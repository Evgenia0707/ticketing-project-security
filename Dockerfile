#from maven not jar file (better use jar)
FROM amd64/maven:3.8.6-openjdk-11
#base image - run folder - saved here
WORKDIR usr/app
#take all code from the root . . paste it under usr/app directory
COPY  .  .
#execute - run app from maven
ENTRYPOINT ["mvn","spring-boot:run"]
