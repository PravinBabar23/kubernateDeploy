pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "your-dockerhub-username/springboot-app"
        DOCKER_CREDENTIALS = "docker-hub-credentials"
        KUBECONFIG_CREDENTIALS = "kube-config"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'git@github.com:your-username/springboot-app.git'
            }
        }

        stage('Build Jar') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${BUILD_NUMBER}")
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS}") {
                        docker.image("${DOCKER_IMAGE}:${BUILD_NUMBER}").push()
                        docker.image("${DOCKER_IMAGE}:${BUILD_NUMBER}").push("latest")
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: "${KUBECONFIG_CREDENTIALS}", variable: 'KUBECONFIG')]) {
                    sh 'kubectl apply -f k8s/deployment.yaml'
                    sh 'kubectl apply -f k8s/service.yaml'
                }
            }
        }
    }

    post {
        success {
            echo "✅ Spring Boot app deployed successfully on Kubernetes!"
        }
        failure {
            echo "❌ Build/Deploy failed!"
        }
    }
}
