pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout Repo') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-pat',
                    url: 'https://github.com/pjwarriors/chr-automation-framework.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Run TestNG Tests') {
            steps {
                bat 'mvn test -DsuiteXmlFile=src\\test\\resources\\testng.xml'
            }
            post {
                always {
                    junit '**/test-output/testng-results.xml'
                }
            }
        }

        stage('Run Cucumber Tests') {
            steps {
                bat 'mvn test -Dcucumber.plugin="json:target/cucumber-reports/Cucumber.json"'
            }
        }

        stage('Publish TestNG HTML Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'test-output',
                    reportFiles: 'index.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    reportName: 'TestNG Report'
                ])
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'target\\extent-reports',
                    reportFiles: 'index.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    reportName: 'Extent Report'
                ])
            }
        }

        stage('Publish Cucumber HTML Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'target\\cucumber-reports',
                    reportFiles: 'cucumber-html-reports.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    reportName: 'Cucumber HTML Report'
                ])
            }
        }
    }

    post {
        success {
            echo "Pipeline PASSED 🎉"
        }
        failure {
            echo "Pipeline FAILED ❌"
        }
    }
}
