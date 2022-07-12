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
  -Dsonar.login=51df199b1d72bb0f6befb770d1da030b44b44c48
