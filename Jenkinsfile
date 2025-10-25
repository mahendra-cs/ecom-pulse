pipeline {
    agent any

    environment {
        SONAR_QUBE_URL = "YOUR_SONARQUBE_URL"
        SONAR_QUBE_TOKEN = "YOUR_SONARQUBE_TOKEN"
    }

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
        stage('Code Quality') {
            steps {
                dir('ecom-pulse') {
                    sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_QUBE_URL} -Dsonar.login=${SONAR_QUBE_TOKEN}"
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
