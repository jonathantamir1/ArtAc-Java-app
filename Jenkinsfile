pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        checkout scm
        sh 'git rev-parse --short HEAD > .git-commit'
        script {
          env.GIT_COMMIT_SHORT = readFile('.git-commit').trim()
        }

      }
    }

    stage('Build & Test') {
      steps {
        sh 'docker run --rm                     -v "$PWD":/workspace                     -v "$HOME/.m2":/root/.m2                     -w /workspace                     maven:3.9.6-eclipse-temurin-17                     mvn -B -e clean verify'
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} -t ${DOCKER_IMAGE}:commit-${GIT_COMMIT_SHORT} .'
      }
    }

    stage('Push Docker Image') {
      steps {
        withCredentials(bindings: [usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
          sh 'docker push ${DOCKER_IMAGE}:${IMAGE_TAG}'
          sh 'docker push ${DOCKER_IMAGE}:commit-${GIT_COMMIT_SHORT}'
          sh 'docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} ${DOCKER_IMAGE}:latest'
          sh 'docker push ${DOCKER_IMAGE}:latest'
        }

      }
    }

    stage('Deploy to EC2') {
      steps {
        withCredentials(bindings: [
                                        sshUserPrivateKey(credentialsId: 'ec2-ssh-key', keyFileVariable: 'SSH_KEY', usernameVariable: 'SSH_USER'),
                                        string(credentialsId: 'CHATBOT_API_KEY', variable: 'CHATBOT_API_KEY')
                                    ]) {
            sh 'scp -i "$SSH_KEY" -o StrictHostKeyChecking=no scripts/deploy.sh "$SSH_USER"@"${EC2_HOST}":/tmp/deploy.sh'
            sh 'ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no "$SSH_USER"@"${EC2_HOST}" "chmod +x /tmp/deploy.sh && CHATBOT_API_KEY="$CHATBOT_API_KEY" /tmp/deploy.sh ${DOCKER_IMAGE} ${IMAGE_TAG} chatbot-app 8080"'
          }

        }
      }

    }
    environment {
              DOCKER_IMAGE = "${env.DOCKER_IMAGE ?: 'jamir7/chatbot-app'}"
      IMAGE_TAG = "${env.IMAGE_TAG ?: env.BUILD_NUMBER}"
      EC2_HOST = "${env.EC2_HOST ?: 'ec2-your-host.compute.amazonaws.com'}"
    }
    post {
      always {
        cleanWs()
      }

    }
    options {
      timestamps()
    }
  }