FROM openjdk:11.0
ADD target/otp-service-1.0.0.jar otp-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "otp-service.jar"]