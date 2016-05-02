[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Infrastructure Manager Java API
===============
This project offers an API that allows to connect to the Infrastructure Manager REST API.
For more information about the IM capabilities check: [IM Web](http://www.grycap.upv.es/im).

The javadoc for this project can be found here: [Javadoc](http://indigo-dc.github.io/im-java-api/apidocs/)

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
When the compilation finishes you will have a **target** folder. Inside you can find the compiled java classes and the packaged jars. The jar **im-java-api-x.x.x-jar-with-dependencies.jar** includes all the libraries needed by the im-java-api and can be used independently. The jar **im-java-api-x.x.x.jar** contains only the im-java-api compiled code and needs external libraries to work. The external libraries needed by the project are specified in the pom.xml

1.3 CONFIGURATION
-----------------
This project does not need any configuration files to work.
If you want you can define a **log4j.properties** to log the REST service calls.
To configure the logger you have to create a **log4j.properties** file and set the logger properties.
The log4j.properties file of the tests can help you with the logger configuration.

1.4 USAGE
-----------------
The tests defined in the class **InfrastructureManagerTest** show how to use the im-java-api.
Also check the following lines to see some examples of use:

### 1.4.1 Create the API client
```
InfrastructureManager im = new InfrastructureManager("IM_ENDPOINT", "AUTH_FILE_PATH");
```
When creating a new client you have to specify a valid IM URL, and an authorization file path with the credentials required for the infrastructure deployment.
More information about the authorization file can be found here: [Auth file](http://www.grycap.upv.es/im/doc/client.html#auth-file).
You can also check the authorization file used for the tests that is available in 'src/test/resources/'.

### 1.4.2 Create and destroy an infrastructure
```
// Infrastructure creation based on a TOSCA file
InfrastructureUri newInfrastructureUri = im.createInfrastructure(readFile(TOSCA_FILE_PATH), BodyContentType.TOSCA);
... USE THE INFRASTRUCTURE ...
// Infrastructure destruction
im.destroyInfrastructure(getInfrastructureId());
```
The 'im' client always returns a POJO with the information of the call. If an error occurs, an **ImClientErrorException** is thrown. This exception contains the error message and the error code returned by the server.
