pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building the payment-service'
                // Add build steps here
            }
        }
        stage('Test') {
            steps {
                echo 'Testing the payment-service'
                // Add test steps here
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying the payment-service'
                // Add deployment steps here
            }
        }
    }
}