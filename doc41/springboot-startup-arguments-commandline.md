- Environment variables: Operation system variables
It can be read via: System.getenv("variableName");
It can be set via: export SPRING_PROFILES_ACTIVE=dev
- Java VM variables: pass to your VM after java run
It can be read via: System.getProperty("variableName")
It can be set via: java -Dspring.profiles.active=dev
- Program arguments: Passed to your main method, as method arguments
It can be read via: public static void main(String[] args)... the String[] args
It can be set via: java test.App p1 p2 p3
spring profile: --spring.profiles.active=dev

  