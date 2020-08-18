pipeline {
    agent any
    	
	environment {
        TIMESTAMP = "26-May-2020:10:00"
        REPONAME = "petclinicrepo"
		REGISTRY_LINK = "https://932833910912.dkr.ecr.us-east-1.amazonaws.com/petclinicrepo:latest"
    }

    stages {
		stage('projectInstall') {			
			steps {	
					sh 'pwd'					
					git credentialsId: 'jaga-amp-github-jenkins', url:'https://github.com/jaga-amp/spring-petclinic-master.git'
					git branch: 'Cloud',
						credentialsId: 'jaga-amp-github-jenkins',
						url: 'https://github.com/jaga-amp/spring-petclinic-master.git'
					sh 'ls -lat'					
					echo 'projectInstall over'								  
			}				  
		}
		stage('docker installation') {            
            steps {                
				sh 'mvn --version'
				sh 'sudo yum -y install docker'
				
				sh 'sudo chmod 666 /var/run/docker.sock'
				sh 'sudo usermod -a -G docker $USER'
				sh 'sudo service docker start'
				sh 'docker info'
				sh 'docker -v'
				echo 'docker installation over'	
            }
        }
        stage('Push image') {
			steps {
                // prepare docker build context
                sh 'pwd'
                sh 'ls -lrt'
                sh 'aws ecr get-login --region us-east-1 | sed -e "s/-e none//g" > ecr-login.sh'
                sh 'chmod +x ecr-login.sh'
                sh 'cat ecr-login.sh'
                sh './ecr-login.sh'
                sh 'docker build -t 932833910912.dkr.ecr.us-east-1.amazonaws.com/petclinicrepo .'
                sh 'docker push 932833910912.dkr.ecr.us-east-1.amazonaws.com/petclinicrepo'
               
            }
		}
        
		
	}
}