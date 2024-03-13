#!/bin/bash

jarName="fragnostic-i18n-server-project-assembly-0.2.4-SNAPSHOT.jar"

runJar() {
  java -jar target/scala-2.13/$jarName
}

if [ $# -lt 1 ];
then
  echo ">>>"
  echo ">>> You forget to specify the environment 'd', 'hml' or 'p' ('dev', 'hml' or 'prd') ?"
  echo ">>>"
else
  case $1 in
  
    d|dev)
      echo -e "\n******************* NOTICE *******************"
      echo "   I will run in development"
      echo -e "**********************************************\n"
      export OKD_GATEWAY_ENV=dev
      source ./environment.sh
      runJar
      ;;

    h|hml)
      echo -e "\n******************* NOTICE *******************"
      echo "   I will run in homologation"
      echo -e "**********************************************\n"
      export OKD_GATEWAY_ENV=hml
      source ./environment.sh
      runJar
      ;;

    p|prd)
      echo -e "\n******************* NOTICE *******************"
      echo "   I will run in production"
      echo -e "**********************************************\n"
      export OKD_GATEWAY_ENV=prd
      source ./environment.sh
      runJar
      ;;

    *)
      echo -e "\n******************************** NOTICE ********************************"
      echo "   I will run in development, because I dont know the environment '$1'"
      echo -e "************************************************************************\n"
      export OKD_GATEWAY_ENV=dev
      source ./environment.sh
      runJar
      ;;

  esac
fi
