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
                bat 'run-tests.bat'  // Run tests
            }
        }

        stage('Publish TestNG Reports') {
            steps {
                script {
                    def reportPath = "C:/ProgramData/Jenkins/.jenkins/workspace/SeleniumGrid-Pipeline/test-output"
                    
                    // Check if test-output exists before publishing
                    if (fileExists(reportPath)) {
                        publishHTML([
                            allowMissing: true,  // Allow Jenkins to continue even if the report is missing
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: 'test-output',
                            reportFiles: 'index.html',
                            reportName: 'TestNG Report'
                        ])
                    } else {
                        echo "⚠️ Skipping TestNG report publishing as test-output directory does not exist."
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline execution completed."
        }
    }
}
