### Solução
A solução foi realizada com Spring Boot e Cassandra.

### Pré-requisitos
   1. JDK (11+) 
   2. Docker
   3. Postman
 
### Instruções:
    1. Para criar a imagem docker, executar o comando:
        ```
        * docker build -t custom-cassandra .
        ```
    2. Para startar o container docker de cassandra, executar o comando:
        ```
        * docker run --rm -p 9042:9042 --name cassandra-container -d custom-cassandra
        ```
    3. Acessar o diretorio com o "pom.xml":
        ```
        * cd ..
        ```
    4. mvn install
    5. java -jar springboot-gopay-api-0.0.1.jar
    6. Importar a collection no diretório "postman" para realizar os testes