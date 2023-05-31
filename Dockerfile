FROM openjdk:8
EXPOSE 9090
ADD target/moviebookingapp.jar moviebookingapp.jar
ENTRYPOINT ["java", "-jar", "/moviebookingapp.jar."]