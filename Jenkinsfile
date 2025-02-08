pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/tiwarsha/seleniumgrid.git', branch: 'main'
            }
        }

        stage('Run Selenium Tests') {
            steps {
                bat 'run-tests.bat'  // Execute the batch script
            }
        }

        stage('Publish TestNG Reports') {
            steps {
                publishHTML([ 
                    reportDir: 'test-output',  
                    reportFiles: 'index.html',  
                    reportName: 'TestNG Report',  
                    keepAll: true
                ])
            }
        }
    }

    post {
        always {
            echo "Pipeline execution completed."
        }
    }
}
