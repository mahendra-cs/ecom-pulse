pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building the inventory-service'
                // Add build steps here
            }
        }
        stage('Test') {
            steps {
                echo 'Testing the inventory-service'
                // Add test steps here
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying the inventory-service'
                // Add deployment steps here
            }
        }
    }
}