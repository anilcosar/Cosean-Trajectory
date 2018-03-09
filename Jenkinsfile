node {
    stage('Initialize'){
        def mavenHome = tool 'coseanMaven'
        env.PATH = "${mavenHome}/bin:${env.PATH}"
    }
    stage('Checkout') {
        checkout scm
    }
    stage('Build'){
        sh "mvn clean install"
    }
}
