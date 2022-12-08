pipeline {
  agent {
     label 'Jenkins-Master'
     }
   triggers{
          gitPush()
      }

   environment {
    DOCKER_TAG = "${BUILD_NUMBER}"
   }
          options {
              buildDiscarder logRotator(
                          daysToKeepStr: '1',
                          numToKeepStr: '1'
                  )
          }

   stages {
//      stage("Git Clone Project"){
//       steps{
//          git branch:'feature/TMT-27-impl�mentation-de-lenvoie-des-not', credentialsId: 'Bitbucket-password', url: 'https://aftech-solution@bitbucket.org/timet-aftech/notification-service.git'
//         }
 //   }

    stage('Maven clean project') {
        steps{
         sh 'maven clean'
        }
    }

     stage('Project Build') {
       steps {
         sh 'maven package -x test'
       }
    }

    stage("Docker build Project"){
       steps{
        sh 'docker version'
        sh 'docker build -t hc-cloud-gateway .'
        sh 'docker image list'
        sh 'docker tag hc-cloud-gateway intershop-hc/hc-cloud-gateway:${DOCKER_TAG}'
        }
    }
    stage("Docker login"){
       steps{
        withCredentials([usernamePassword(credentialsId: 'Dockerhub-key', passwordVariable: 'password', usernameVariable: 'username')]) {
        sh 'docker login -u $username -p $password'
    }
    }
}
    stage("Push Image to Docker Hub"){
//         when{
//            // branch 'prod'
//            // branch 'development'
//         }
        steps{
        sh 'docker push  intershop-hc/hc-cloud-gateway:${DOCKER_TAG}'
        }
    }

      stage("remove unused docker image"){
            steps{
            sh 'docker rmi hc-cloud-gateway -f'
            sh 'docker rmi intershop-hc/hc-cloud-gateway:${DOCKER_TAG} -f'
         }
        }

    stage("Ansible Docker deploy and Start the container"){
         when{
             branch 'prod'
            // branch 'development'
        }
        steps{
        ansiColor('xterm'){
            ansiblePlaybook become: true, colorized: true, credentialsId: 'Serveur-Prod', disableHostKeyChecking: true,
            extras: '-e DOCKER_TAG=${DOCKER_TAG}', installation: 'ansible', inventory: '/opt/ansible/internshop/docker/host2',
            playbook: '/opt/ansible/internshop/docker/hc-cloud-gateway.yml'
        }
        }
    }

    stage("kubernetes deployment"){
        when{
            branch 'prod'
            //branch 'development'
        }
//          steps{
//         withAWS(credentials: 'aws-cred', region: 'eu-central-1') {
//           sh '/usr/local/bin/kubectl apply -f k8s-spring-boot-deployment.yml'
//             sendSlackNotification()
//
//             }
//         }
    }



    stage('Post Slack Notification'){
            steps{
             sendSlackNotification()
            }

        }

    }


 }

def sendSlackNotification()
{
	if ( currentBuild.currentResult == "SUCCESS" ) {
		buildSummary = "Job:  ${env.JOB_NAME}\n Status: *SUCCESS*\n Build Report: ${env.BUILD_URL}CI-Build-HTML-Report"

		slackSend color : "good", message: "${buildSummary}", channel: '#deployment_notifications', tokenCredentialId: 'Slack-notify-jenkins-hc'
		}
	else {
		buildSummary = "Job:  ${env.JOB_NAME}\n Status: *FAILURE*\n Error description: *${CI_ERROR}* \nBuild Report :${env.BUILD_URL}CI-Build-HTML-Report"
		slackSend color : "danger", message: "${buildSummary}", channel: '#deployment_notifications', tokenCredentialId: 'Slack-notify-jenkins-hc'
		}
}

