Infrastructure Manager Java API
===============
This project offers an API that allows to connect to the Infrastructure Manager REST services.
For more information about the IM capabilities check: [IM Web](http://www.grycap.upv.es/im/index.php).

1. INSTALLATION
===============

1.1 REQUISITES
--------------
This project has been created with maven. 
To compile it you will need at least **Apache Maven 3.0.5** and **Java 1.7**.
Maven will take care of downloading all the extra dependencies needed for the project.

1.2 INSTALLING
--------------
To compile the project you need to be in the same folder as the **pom.xml** file and type:
```
mvn clean install
```
this command compiles the code and executes the tests. If you want to compile the code without the tests you can use:
```
mvn clean install -DskipTests
```
When the compilation finishes you will have a **target** folder. Inside you can find the compiled java classes and the packaged jars. The jar **im-java-api-0.0.1-jar-with-dependencies.jar** includes all the libraries needed by the im-java-api and can be used independently. The jar **im-java-api-0.0.1.jar** contains only the im-java-api code and needs external libraries to work. The external libraries needed by the project are specified in the pom.xml

1.3 CONFIGURATION
-----------------
This project don't need any configuration files to work.
If you want you can define a **log4j.properties** to log the REST service calls.
To configure the logger you have to create a **log4j.properties** file and set the logger properties.
The log4j.properties file of the tests can help you with the logger configuration.

1.4 USAGE
-----------------
The tests defined in the class **InfrastructureManagerApiClientTest** show how to use the im-java-api.
Also check the following lines to see some examples of use:

### 1.4.1 Create and destroy an infrastructure
```
InfrastructureManagerApiClient imClient = new InfrastructureManagerApiClient("PROVIDER_URL", "AUTH_FILE_PATH");
// Infrastructure creation based on a TOSCA file
ServiceResponse response = imClient.createInfrastructure(FileIO.readUTF8File(TOSCA_FILE_PATH));
// Method to extract the infId of the serrver response
setInfrastructureId(response);
// Infrastructure destruction
imClient.destroyInfrastructure(getInfrastructureId());
```


