package es.upv.i3m.grycap.im.auth;

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.im.auth.credential.Credential;
import es.upv.i3m.grycap.im.auth.credential.DummyCredential;
import es.upv.i3m.grycap.im.auth.credential.im.ImCredential.ImUsernamePasswordCredential;
import es.upv.i3m.grycap.im.auth.credential.occi.OcciCredential;
import es.upv.i3m.grycap.im.auth.credential.openstack.OpenstackAuthVersion;
import es.upv.i3m.grycap.im.auth.credential.openstack.OpenstackCredential;
import es.upv.i3m.grycap.im.auth.credential.vmrc.VmrcCredential;
import es.upv.i3m.grycap.im.exceptions.ImClientException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class AuthorizationHeaderTest extends ImTestWatcher {

  private static final String DUMMY_CREDS = "id = dummy ; type = Dummy";
  private static final String IM_CREDS =
      "type = InfrastructureManager ; username = imuser01 ; password = pwd";
  private static final String VMRC_CREDS =
      "type = VMRC ; username = demo ; password = pwd ; host = host";
  private static final String OST_CREDS =
      "id = ost ; type = OpenStack ; username = usr ; password = pwd ; host = host ; service_region = region";
  private static final String OCCI_CREDS =
      "id = occi ; type = OCCI ; host = host ; proxy = proxy";

  private static AuthorizationHeader ah;

  @BeforeClass
  public static void createAuthorizationHeader() {
    ah = new AuthorizationHeader();
  }

  @Before
  public void clearAuthorizationHeader() {
    ah.setCredentialsAuthInfos(new ArrayList<Credential<?>>());
  }

  @Test
  public void testDummyCredentials() throws ImClientException {
    Credential<?> cred = DummyCredential.getBuilder().withId("dummy").build();
    ah.addCredential(cred);
    Assert.assertEquals(DUMMY_CREDS, ah.serialize());
  }

  @Test
  public void testImCredentials() throws ImClientException {
    Credential<?> cred = ImUsernamePasswordCredential.getBuilder()
        .withUsername("imuser01").withPassword("pwd").build();
    ah.addCredential(cred);
    Assert.assertEquals(IM_CREDS, ah.serialize());
  }

  @Test
  public void testVmrcCredentials() throws ImClientException {
    Credential<?> cred = VmrcCredential.getBuilder().withUsername("demo")
        .withPassword("pwd").withHost("host").build();
    ah.addCredential(cred);
    Assert.assertEquals(VMRC_CREDS, ah.serialize());
  }

  @Test
  public void testOpenStackCredentials() throws ImClientException {
    Credential<?> cred = OpenstackCredential.getBuilder().withId("ost")
        .withUsername("usr").withPassword("pwd").withTenant("tenant")
        .withServiceRegion("region").withHost("host")
        .withAuthVersion(OpenstackAuthVersion.PASSWORD_2_0).build();
    ah.addCredential(cred);
    Assert.assertEquals(OST_CREDS, ah.serialize());
  }

  @Test
  public void testOcciCredentials() throws ImClientException {
    Credential<?> cred = OcciCredential.getBuilder().withId("occi")
        .withHost("host").withProxy("proxy").build();
    ah.addCredential(cred);
    Assert.assertEquals(OCCI_CREDS, ah.serialize());
  }

}
