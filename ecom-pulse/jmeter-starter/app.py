from flask import Flask, jsonify, request
import subprocess
import re
import os
import signal
from flasgger import Swagger

app = Flask(__name__)
swagger = Swagger(app) # Initialize Swagger

jmeter_process = None # Global variable to store the JMeter process

@app.route('/set-throughput', methods=['POST'])
def set_throughput():
    """
    Set the target throughput for the JMeter test.
    This will update the ConstantThroughputTimer in create_order.jmx.
    ---
    parameters:
      - name: body
        in: body
        required: true
        schema:
          type: object
          required:
            - users_per_second
          properties:
            users_per_second:
              type: number
              description: Target throughput in users per second.
              example: 5
    responses:
      200:
        description: Throughput set successfully.
      400:
        description: Invalid input.
      500:
        description: An unexpected error occurred.
    """
    try:
        data = request.get_json()
        users_per_second = data.get('users_per_second')

        if not users_per_second or not isinstance(users_per_second, (int, float)):
            return jsonify({'message': 'Invalid input. Please provide a number for users_per_second.'}), 400

        throughput_per_minute = users_per_second * 60
        jmx_file_path = '/jmeter/create_order.jmx'

        with open(jmx_file_path, 'r') as f:
            jmx_content = f.read()

        # Use regex to replace the throughput value
        jmx_content = re.sub(r'(<doubleProp><name>throughput</name><value>)[^<]+(</value>)',
                             r'\g<1>{}\g<2>'.format(throughput_per_minute),
                             jmx_content)

        with open(jmx_file_path, 'w') as f:
            f.write(jmx_content)

        return jsonify({'message': f'Throughput set to {users_per_second} users per second ({throughput_per_minute} samples per minute)'}), 200

    except Exception as e:
        return jsonify({'message': f'An unexpected error occurred: {e}'}), 500



@app.route('/start-jmeter', methods=['POST'])
def start_jmeter():
    """
    Start the JMeter test in the background.
    The test will run continuously until explicitly stopped.
    ---
    responses:
      200:
        description: JMeter test started successfully in background.
      409:
        description: JMeter test is already running.
      500:
        description: An unexpected error occurred.
    """
    global jmeter_process
    if jmeter_process and jmeter_process.poll() is None:
        return jsonify({'message': 'JMeter test is already running'}), 409

    try:
        # Execute the JMeter test in detached mode
        # We need to run docker-compose from the parent directory
        jmeter_process = subprocess.Popen(['docker-compose', 'run', '--rm', 'jmeter'],
                                          cwd='/app/..', # Change current working directory to parent
                                          stdout=subprocess.PIPE,
                                          stderr=subprocess.PIPE,
                                          preexec_fn=os.setsid) # Start a new session for the child process

        return jsonify({'message': 'JMeter test started successfully in background'}), 200
    except Exception as e:
        return jsonify({'message': f'An unexpected error occurred: {e}'}), 500

@app.route('/stop-jmeter', methods=['POST'])
def stop_jmeter():
    """
    Stop any currently running JMeter test.
    ---
    responses:
      200:
        description: JMeter test stopped successfully.
      404:
        description: No JMeter test is currently running.
      500:
        description: An unexpected error occurred.
    """
    global jmeter_process
    if jmeter_process and jmeter_process.poll() is None:
        try:
            os.killpg(os.getpgid(jmeter_process.pid), signal.SIGTERM) # Terminate the process group
            jmeter_process.wait(timeout=5) # Wait for the process to terminate
            jmeter_process = None
            return jsonify({'message': 'JMeter test stopped successfully'}), 200
        except Exception as e:
            return jsonify({'message': f'Error stopping JMeter test: {e}'}), 500
    else:
        return jsonify({'message': 'No JMeter test is currently running'}), 404

@app.route('/set-users', methods=['POST'])
def set_users():
    """
    Set the number of concurrent users (threads) for the JMeter test.
    This will update the ThreadGroup.num_threads in create_order.jmx.
    ---
    parameters:
      - name: body
        in: body
        required: true
        schema:
          type: object
          required:
            - num_users
          properties:
            num_users:
              type: integer
              description: Number of concurrent users.
              example: 10
    responses:
      200:
        description: Number of users set successfully.
      400:
        description: Invalid input.
      500:
        description: An unexpected error occurred.
    """
    try:
        data = request.get_json()
        num_users = data.get('num_users')

        if not num_users or not isinstance(num_users, int):
            return jsonify({'message': 'Invalid input. Please provide an integer for num_users.'}), 400

        jmx_file_path = '/jmeter/create_order.jmx'

        with open(jmx_file_path, 'r') as f:
            jmx_content = f.read()

        # Use regex to replace the number of threads
        jmx_content = re.sub(r'(<stringProp name="ThreadGroup.num_threads">)\d+(</stringProp>)',
                             r'\g<1>{}\g<2>'.format(num_users),
                             jmx_content)

        with open(jmx_file_path, 'w') as f:
            f.write(jmx_content)

        return jsonify({'message': f'Number of users set to {num_users}'}), 200

    except Exception as e:
        return jsonify({'message': f'An unexpected error occurred: {e}'}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)