pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                dir('ecom-pulse') {
                    sh 'mvn clean install'
                }
            }
        }
        stage('Test') {
            parallel {
                stage('Test Order Service') {
                    steps {
                        dir('ecom-pulse/order-service') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Test Payment Service') {
                    steps {
                        dir('ecom-pulse/payment-service') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Test Inventory Service') {
                    steps {
                        dir('ecom-pulse/inventory-service') {
                            sh 'mvn test'
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                dir('ecom-pulse') {
                    sh 'docker-compose up -d --build'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
