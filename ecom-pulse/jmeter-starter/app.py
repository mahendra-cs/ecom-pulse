
from flask import Flask, jsonify
import subprocess

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

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)
