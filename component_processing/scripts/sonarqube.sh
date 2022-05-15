mvn clean jacoco:prepare-agent install
mvn jacoco:report
mvn sonar:sonar \
  -Dsonar.projectKey=component_processing \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=e67364b1014612de1b13c21882fd1eb92110fec5
