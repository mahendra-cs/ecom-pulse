pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building the order-service'
                // Add build steps here
            }
        }
        stage('Test') {
            steps {
                echo 'Testing the order-service'
                // Add test steps here
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying the order-service'
                // Add deployment steps here
            }
        }
    }
}