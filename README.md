# personal-manager
* Api para gerenciamento de finanças pessoais
* projeto para disciplina de Gerenciamento e configuração de Software
* Version - 1.0-SNAPSHOT

Tecnologias:
- Spring boot 2.2.6 https://spring.io/guides/gs/spring-boot/
- Liquibase https://www.liquibase.org/
- Java 11
- Postgresql

## Workflow CI/CD

![Workflow Deploy](/infra/workflow-backend.png)


## Action Homologação

 
##### Pull da branch homologação build e execução dos testes 
 ```  
    test:
        name: Execute Test
        runs-on: ubuntu-20.04
    
        steps:
          - name: Checkout repository
            uses: actions/checkout@v2.3.4
          - name: Setup Java JDK
            uses: actions/setup-java@v2.1.0
            with:
              java-version: 11.0.4
              distribution: 'adopt'
          - name: Maven Package
            run: mvn -B clean package -DskipTests
          - name: Maven Verify
            run: mvn -B clean verify 
  ```
  #### Construção da imagem docker e push para o DockerHub
  ```
    docker:
      name: Build and Publish docker image
      runs-on: ubuntu-20.04
      needs: [test]
      env:
       REPO: ${{ secrets.DOCKER_REPO }}
      steps:
        - uses: actions/checkout@v2
        - name: Login to Docker Hub
          run: docker login -u ${{ secrets.DOCKER_USER }} -p ${{ secrets.DOCKER_PASS }}
        - name: Build the Docker image
          run: docker build -t $REPO:latest -t $REPO:${GITHUB_SHA::8} .
        - name: Publish Docker image
          run: docker push $REPO
  ```
  #### Deploy da imagem docker no servidor através de um POST em um WEB HOOK que aciona os scripts de montagem do ambiente
  ```
    deploy:
      name: Deploy on server homologacao - post webhook call
      runs-on: ubuntu-18.04
      needs: [docker]
      steps:
        - name: Deploy docker container webhook
          uses: joelwmale/webhook-action@master
          with:
            url: ${{ secrets.DEPLOY_WEBHOOK_URL }}
  ```
  #### Script deploy homologação executado pelo webhook no servidor 
   -  Para e apaga o container e imagem de homologação, e reconstrói uma nova imagem que está no Dockerhub gerada fluxo de deploy
  ```
    #!/bin/sh
    docker image rm -f magaiwer/personal-manager:latest
    docker stop api-homolog
    docker system prune -f
    docker-compose -f docker-compose.homolog.yaml up -d
  ```
  
  ## Action Produção
  #### A action de produção é disparada quando ocorre um merge na branch master, acionando um Web hook no servidor que realiza o clone do ambiente de homolação que já está testado, para produção.
  
  ```
    deploy:
        name: Deploy on server production - post webhook call
        runs-on: ubuntu-latest
        steps:
          - name: Post webhook
            uses: joelwmale/webhook-action@master
            with:
              url: ${{ secrets.DOCKER_WEBHOOK_URL_PROD }}
  
  ```  
  #### Script produção executado pelo webhook no servidor 
  -  Para e apaga o container de produção sob um novo apartir do container de homologação, criando uma network entre o container do banco de dados e o container da API, passa os argumentos de enviroment de produção para subir o container.
  
  ````

  #!/bin/sh
  docker stop api-prod
  docker stop db-postgres-prod
  docker image rm -f api-prod db-postgres-prod
  docker system prune -f

  docker network rm producao
  docker network create producao

  #docker commit api-homolog api-prod:latest
  #docker commit db-postgres-homolog db-postgres-prod:latest

  docker run \
         --name db-postgres-prod \
         --network producao \
         -e POSTGRES_USER=postgres \
         -e POSTGRES_PASSWORD=postgres \
         -v postgres-data:/var/lib/postgresql/data \
         -p 5432:5432 \
         -d postgres:latest

  #docker cp /home/univates/app-docker/ini-database.sh db-postgres-prod:/tmp/
  #docker exec db-postgres-prod bash /tmp/init-database.sh

  docker run -p 9002:9000 -d --env-file .env-file --name api-prod --network producao magaiwer/personal-manager java ${ADDITIONAL_OPTS} -jar /app-api/personal-manager-api.jar --spring.profiles.active=prod
  
  ````


Frontend - https://github.com/Magaiwer/personal-manager-client
