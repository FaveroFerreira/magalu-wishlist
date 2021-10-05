FROM adoptopenjdk/openjdk11:jre-11.0.2.9-alpine
WORKDIR app
COPY ./build/libs/magalu-wishlist-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar", "magalu-wishlist-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080