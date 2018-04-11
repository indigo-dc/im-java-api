[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Infrastructure Manager Java API
===============
This project offers an API that allows to connect to the Infrastructure Manager REST API.
For more information about the IM capabilities check: [IM Web](http://www.grycap.upv.es/im).

The javadoc for this project can be found here: [Javadoc](http://indigo-dc.github.io/im-java-api/apidocs/)

1 INSTALLATION
===============

1.1 REQUISITES
--------------
This project has been created with maven.
To compile it you will need at least **Apache Maven 3.0.5** and **Java 1.8**.
Maven will take care of downloading all the extra dependencies needed for the project.

1.2 INSTALLING
--------------
### 1.2.1 From Maven
To compile the project you need to be in the same folder as the **pom.xml** file and type:
```
mvn clean install
```
this command compiles the code and executes the tests. If you want to compile the code without the tests you can use:
```
mvn clean install -DskipTests
```
When the compilation finishes you will have a **target** folder. Inside you can find the compiled java classes and the packaged jars. The jar **im-java-api-x.x.x-jar-with-dependencies.jar** includes all the libraries needed by the im-java-api and can be used independently. The jar **im-java-api-x.x.x.jar** contains only the im-java-api compiled code and needs external libraries to work. The external libraries needed by the project are specified in the pom.xml

### 1.2.1 From a tarball
The code for the library can also be found in the [im-java-api github repository](https://github.com/indigo-dc/im-java-api/releases/tag/v0.4.9).
To download the source code:
```
wget https://github.com/indigo-dc/im-java-api/archive/v0.4.9.tar.gz
```
After the download you have to untar the package and inside the folder compile the project like in the maven section:
```
tar zxf im-java-api-v0.4.9.tar.gz
cd im-java-api-0.4.9
mvn clean install
```
Once the compilation finishes in the folder target you can find the jar.

Also you can download the jar already packaged with all the dependencies from:
```
wget https://github.com/indigo-dc/im-java-api/releases/download/v0.4.9/im-java-api-0.4.9-jar-with-dependencies.tar.gz
```

1.3 CONFIGURATION
-----------------
This project does not need any configuration files to work.
If you want you can define a **log4j.properties** to log the REST service calls.
To configure the logger you have to create a **log4j.properties** file and set the logger properties.
The log4j.properties file of the tests can help you with the logger configuration.

2 USAGE
===============
The tests defined in the class **InfrastructureManagerTest** show how to use the im-java-api.
Also check the following lines to see some examples of use:

2.1 From Java
--------------
### 2.1.1 Create the API client
```
InfrastructureManager im = new InfrastructureManager("IM_ENDPOINT", "AUTH_FILE_PATH");
```
When creating a new client you have to specify a valid IM URL, and an authorization file path with the credentials required for the infrastructure deployment.
More information about the authorization file can be found here: [Auth file](http://imdocs.readthedocs.io/en/devel/client.html#authorization-file).
You can also check the authorization file used for the tests that is available in 'src/test/resources/'.

### 2.1.2 Create and destroy an infrastructure
```
// Infrastructure creation based on a TOSCA file
InfrastructureUri newInfrastructureUri = im.createInfrastructure(readFile(TOSCA_FILE_PATH), BodyContentType.TOSCA);
... USE THE INFRASTRUCTURE ...
// Infrastructure destruction
im.destroyInfrastructure(getInfrastructureId());
```
The 'im' client always returns a POJO with the information of the call. If an error occurs, an **ImClientErrorException** is thrown. This exception contains the error message and the error code returned by the server.

### 2.1.3 Create authorization headers
Since version 0.4.5 the infrastructure manager allows to use the **AuthorizationHeader** class and different builders to create the authorization headers.
The usage is as follows:
```
AuthorizationHeader ah = new AuthorizationHeader();
Credentials imCred = ImCredentials.buildCredentials().withUsername("user").withPassword("pass");
Credentials vmrcCred = VmrcCredentials.buildCredentials().withUsername("user").withPassword("pass").withHost("host");
Credentials dummyCred = DummyCredential.buildCredentials();
ah.addCredential(imCred);
ah.addCredential(vmrcCred);
ah.addCredential(dummyCred);
InfrastructureManager im = new InfrastructureManager("IM_ENDPOINT", ah);
```

2.2 From the Orchestrator
--------------
The im-java-api is used to facilitate the connection between the [Orchestrator](https://github.com/indigo-dc/orchestrator) and the [IM](https://github.com/indigo-dc/im).  
The Orchestrator is mainly developed in Java and the IM in Python, so this library acts as a mediator between this to systems.  
The IM offers a REST API that returns JSON messages and the im-java-api transforms this messages in Java classes that are used by the Orchestrator.

This library library is automatically deployed with the Orchestrator (due to maven dependencies).  
Anyhow, if you want to substitute the library packaged with the war, the location inside the war is WEB-INF/lib/im-java-api-X.X.X.jar.  
To use this library from the Orchestrator you only have to used the Orchestrator normally and create a deployment that uses the IM endpoint.  
More info about the Orchestrator can be found [here](http://indigo-dc.github.io/orchestrator/restdocs/).
