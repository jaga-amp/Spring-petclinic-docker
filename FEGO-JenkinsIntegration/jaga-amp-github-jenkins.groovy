def getTimeStamp(){
    return sh (script: "date +'%Y%m%d%H%M%S%N' | sed 's/[0-9][0-9][0-9][0-9][0-9][0-9]\$//g'", returnStdout: true);
}
node('master'){
    
    stage('Init'){
        script{
        env.TIMESTAMP = getTimeStamp();
        env.REGISTRY_LINK = 'https://932833910912.dkr.ecr.us-east-1.amazonaws.com/petclinicrepo:latest'
        
        }
    }

stage('projectInstall') {

git credentialsId: 'jaga-amp-github-jenkins', url:'https://github.com/jaga-amp/Test.git'
dir('/Test') {
  sh '''mvn test'''
  sh '''mvn install'''
}
}
stage('dockerBuild') {
dir('/Test') {

    sh '''
            docker build -t ${TIMESTAMP} .
            docker tag ${TIMESTAMP} ${REGISTRY_LINK}
            docker push ${REGISTRY_LINK}
    '''
}}

}