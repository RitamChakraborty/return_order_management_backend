#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Running jacoco:prepare-agent...'
mvn clean jacoco:prepare-agent install
echo 'Running jacoco:report...'
mvn jacoco:report
echo 'Running sonar:sonar...'
mvn sonar:sonar \
  -Dsonar.projectKey=component_processing \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=e67364b1014612de1b13c21882fd1eb92110fec5
