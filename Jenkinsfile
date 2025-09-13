pipeline {
    agent any

    stages {
        stage('Build') {
            parallel {
                stage('Build Order Service') {
                    steps {
                        dir('ecom-pulse/order-service') {
                            sh 'mvn clean install'
                        }
                    }
                }
                stage('Build Payment Service') {
                    steps {
                        dir('ecom-pulse/payment-service') {
                            sh 'mvn clean install'
                        }
                    }
                }
                stage('Build Inventory Service') {
                    steps {
                        dir('ecom-pulse/inventory-service') {
                            sh 'mvn clean install'
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
}
