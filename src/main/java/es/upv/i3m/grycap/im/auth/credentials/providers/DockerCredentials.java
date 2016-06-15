package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class DockerCredentials extends GenericCredentials<DockerCredentials> {

  private DockerCredentials() {
    super(ServiceProvider.DOCKER);
  }

  public static DockerCredentials buildCredentials() {
    return new DockerCredentials();
  }

}
