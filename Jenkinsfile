pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                sh 'rm -rf petclinic_integration_test' // Remove existing directory
                sh 'git clone https://github.com/jgomezz/petclinic_integration_test.git'
            }
        }
        stage('Compile') {
            steps {
                dir('petclinic_integration_test') {
                    sh 'mvn clean compile'  // Example Maven command
                }
            }
        }
        stage('Test') {
            steps {
                dir('petclinic_integration_test') {
                    withCredentials([usernamePassword(credentialsId: 'DB_CREDENTIALS', usernameVariable: 'DB_USERNAME', passwordVariable: 'DB_PASSWORD')]) {
                        sh """
                        export DB_USERNAME=${DB_USERNAME}
                        export DB_PASSWORD=${DB_PASSWORD}
                        mvn test -Dspring.profiles.active=test
                        """
                    }
                }
            }
        }
    }
}
