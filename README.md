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

 
#####- Pull da branch homologação build e execução dos testes 
- ```  
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
  - ##### Construção da imagem docker e push para o DockerHub
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
  ## Action Produção
  ##### A action de produção é disparada quando ocorre um merge na branch master,  acionando  
  ##### um Web hook no servidor que realiza o clone do ambiente de homolação que já está testado, para produção.
  
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

Frontend - https://github.com/Magaiwer/personal-manager-client
