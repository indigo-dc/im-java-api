package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class AmazonEc2Credentials
    extends GenericCredentials<AmazonEc2Credentials> {

  private AmazonEc2Credentials() {
    super(ServiceProvider.EC2);
  }

  public static AmazonEc2Credentials buildCredentials() {
    return new AmazonEc2Credentials();
  }

}
