package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;
import es.upv.i3m.grycap.im.auth.credentials.properties.PrivateKeyProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.PublicKeyProperty;

public class AzureCredentials extends GenericCredentials<AzureCredentials> {

  private AzureCredentials() {
    super(ServiceProvider.AZURE);
  }

  public static AzureCredentials buildCredentials() {
    return new AzureCredentials();
  }

  public AzureCredentials withPublicKey(String publicKey) {
    setCredentials(new PublicKeyProperty(getCredentials(), publicKey));
    return this;
  }

  public AzureCredentials withPrivateKey(String privateKey) {
    setCredentials(new PrivateKeyProperty(getCredentials(), privateKey));
    return this;
  }

}
