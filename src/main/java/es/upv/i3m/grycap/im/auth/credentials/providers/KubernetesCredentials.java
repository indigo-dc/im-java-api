package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class KubernetesCredentials
    extends GenericCredentials<KubernetesCredentials> {

  private KubernetesCredentials() {
    super(ServiceProvider.KUBERNETES);
  }

  public static KubernetesCredentials buildCredentials() {
    return new KubernetesCredentials();
  }

}
