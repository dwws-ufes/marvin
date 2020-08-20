pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                slackSend baseUrl: 'https://hooks.slack.com/services/', channel: '#jenkins', color: 'good', message: 'Comecei o processo de Build/Deploy do Marvin! Já dou uma resposta.', teamDomain: 'Marvin', tokenCredentialId: 'slack-token'
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
                    sh 'unzip -o marvin.war -d /opt/wildfly/standalone/deployments/marvin.war'    
                }
                dir("/opt/wildfly/standalone/deployments") {
                    sh 'touch marvin.war.dodeploy'  
                }
            }
        }
    }
    
    post {
        success {
            slackSend baseUrl: 'https://hooks.slack.com/services/', channel: '#jenkins', color: 'good', message: 'Muito bem! O processo foi um sucesso!', teamDomain: 'Marvin', tokenCredentialId: 'slack-token'
        }
        failure {
            slackSend baseUrl: 'https://hooks.slack.com/services/', channel: '#jenkins', color: 'danger', message: 'Ah não! Houve algo de errado e o processo falhou!', teamDomain: 'Marvin', tokenCredentialId: 'slack-token'   
        }
    }
    
}
