package es.upv.i3m.grycap.im.auth.credential.ec2;

import es.upv.i3m.grycap.im.auth.credential.AbstractUsernamePasswordCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceProvider;

public class AmazonEc2UserPwdCredential
    extends AbstractUsernamePasswordCredential<AmazonEc2UserPwdCredential> {

  protected AmazonEc2UserPwdCredential(
      AmazonEc2UserPwdCredentialBuilder builder) {
    super(builder);
  }

  @Override
  public ServiceProvider getServiceProvider() {
    return ServiceProvider.EC2;
  }

  public static AmazonEc2UserPwdCredentialBuilder getBuilder() {
    return new AmazonEc2UserPwdCredentialBuilder();
  }

  //@formatter:off
  public static class AmazonEc2UserPwdCredentialBuilder extends
      AbstractUsernamePasswordCredentialBuilder<AmazonEc2UserPwdCredentialBuilder, 
      AmazonEc2UserPwdCredential> {
    //@formatter:on

    @Override
    public AmazonEc2UserPwdCredential build() {
      return new AmazonEc2UserPwdCredential(this);
    }
  }
}