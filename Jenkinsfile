pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                dir("/var/lib/jenkins/workspace/marvin/target") {
                    sh 'unzip -o marvin.war -d /home/luan/wildfly-19.0.0.Final/standalone/deployments/marvin.war'    
                }
                dir("/home/luan/wildfly-19.0.0.Final/standalone/deployments") {
                    sh 'touch marvin.war.dodeploy'  
                }
            }
        }
    }
    post {
        success {
            slackSend baseUrl: 'https://hooks.slack.com/services/', channel: '#jenkins-pipeline-demo', color: 'good', message: 'Build succesful!!', teamDomain: 'Workspace do luan', tokenCredentialId: 'slack-demo'
            slackUploadFile channel: '#jenkins-pipeline-demo', filePath: ""${JENKINS_URL}/job/${JOB_NAME}/lastBuild/consoleText"
        }
    }
}
