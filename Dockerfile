FROM openjdk:11.0.9.1-jre
ADD /target/api-produto-*.jar api-produto.jar
ENTRYPOINT ["java", "-jar", "api-produto.jar"]