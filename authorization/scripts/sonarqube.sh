#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Running jacoco:prepare-agent...'
mvn clean jacoco:prepare-agent install
echo 'Running jacoco:report...'
mvn jacoco:report
echo 'Running sonar:sonar...'
mvn sonar:sonar \
  -Dsonar.projectKey=authorization \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=dc7fa119cf886f7d7c604216663bbf1cfc067f8a
