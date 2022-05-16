mvn clean jacoco:prepare-agent install
mvn jacoco:report
mvn sonar:sonar \
  -Dsonar.projectKey=authorization \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=78789b0cc53378b2dc83a1aec19718c89d0c7bd6
