from flask import Flask, jsonify, request
import subprocess
import re

app = Flask(__name__)

@app.route('/start-jmeter', methods=['POST'])
def start_jmeter():
    try:
        # Execute the JMeter test
        subprocess.run(['docker-compose', 'run', '--rm', 'jmeter'], check=True)
        return jsonify({'message': 'JMeter test started successfully'}), 200
    except subprocess.CalledProcessError as e:
        return jsonify({'message': f'Error starting JMeter test: {e}'}), 500
    except Exception as e:
        return jsonify({'message': f'An unexpected error occurred: {e}'}), 500

@app.route('/set-users', methods=['POST'])
def set_users():
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