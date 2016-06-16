package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;
import es.upv.i3m.grycap.im.auth.credentials.properties.ProjectProperty;

public class GceCredentials extends GenericCredentials<GceCredentials> {

  private GceCredentials() {
    super(ServiceProvider.GCE);
  }

  public static GceCredentials buildCredentials() {
    return new GceCredentials();
  }

  public GceCredentials withProject(String project) {
    setCredentials(new ProjectProperty(getCredentials(), project));
    return this;
  }

}
