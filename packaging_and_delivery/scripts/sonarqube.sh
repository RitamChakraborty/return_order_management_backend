#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Running jacoco:prepare-agent...'
mvn clean jacoco:prepare-agent install
echo 'Running jacoco:report...'
mvn jacoco:report
echo 'Running sonar:sonar...'
mvn sonar:sonar \
  -Dsonar.projectKey=packaging_and_delivery \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=c75a3b54cf93036a4ecde58d92114cf2f0f7a2b3