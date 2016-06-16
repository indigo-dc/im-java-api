package es.upv.i3m.grycap.im.auth.credentials;

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.file.NoNullOrEmptyFile;
import es.upv.i3m.grycap.file.Utf8File;
import es.upv.i3m.grycap.im.InfrastructureManager;
import es.upv.i3m.grycap.im.InfrastructureManagerTest;
import es.upv.i3m.grycap.im.auth.credentials.providers.AmazonEc2Credentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.DockerCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.DummyCredential;
import es.upv.i3m.grycap.im.auth.credentials.providers.FogBowCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.GceCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.ImCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.KubernetesCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.OcciCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.OpenNebulaCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.OpenStackCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.OpenstackAuthVersion;
import es.upv.i3m.grycap.im.auth.credentials.providers.VmrcCredentials;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.pojo.InfrastructureUri;
import es.upv.i3m.grycap.im.rest.client.BodyContentType;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

public class AuthorizationHeaderTest extends ImTestWatcher {

  // Test user/pass
  private static final String USER = "user";
  private static final String PASS = "pass";
  private static final String USER_PASS =
      "username = " + USER + " ; password = " + PASS;
  // Credentials
  private static final String DUMMY_CREDS = "id = dummy ; type = Dummy";
  private static final String IM_UP_CREDS =
      "id = im ; type = InfrastructureManager ; " + USER_PASS;
  private static final String IM_TK_CREDS =
      "id = im ; type = InfrastructureManager ; token = token";
  private static final String VMRC_CREDS =
      "id = vmrc ; type = VMRC ; " + USER_PASS + " ; host = host";
  private static final String OST_CREDS =
      "id = ost ; type = OpenStack ; " + USER_PASS
          + " ; tenant = tenant ; service_region = region ; host = host";
  private static final String OST_CREDS_PASS_3 =
      "id = ost ; type = OpenStack ; " + USER_PASS
          + " ; tenant = tenant ; service_region = region ; host = host ; auth_version = 3.X_password";
  private static final String OST_CREDS_PASS_3_TOKEN =
      "id = ost ; type = OpenStack ; " + USER_PASS
          + " ; tenant = tenant ; service_region = region ; host = host ; auth_version = 3.x_oidc_access_token";
  private static final String OCCI_CREDS =
      "id = occi ; type = OCCI ; host = host ; proxy = proxy";
  private static final String ONE_UP_CREDS =
      "id = one ; type = OpenNebula ; " + USER_PASS + " ; host = host";
  private static final String ONE_TK_CREDS =
      "id = one ; type = OpenNebula ; token = token ; host = host";
  private static final String EC2_CREDS =
      "id = ec2 ; type = EC2 ; " + USER_PASS;
  private static final String DOCKER_CREDS =
      "id = docker ; type = Docker ; host = host_url";
  private static final String GCE_CREDS =
      "id = gce ; type = GCE ; " + USER_PASS + " ; project = testPrj";
  private static final String KUB_CREDS =
      "id = kub ; type = Kubernetes ; username = user ; password = pass ; host = host";
  private static final String FOG_BOW_CREDS =
      "id = fog ; type = FogBow ; host = host ; proxy = proxy";
  // IM information
  private static final String IM_DUMMY_PROVIDER_URL =
      "http://servproject.i3m.upv.es:8811";
  private static final String TOSCA_FILE_PATH =
      "./src/test/resources/tosca/galaxy_tosca.yaml";

  private static AuthorizationHeader ah;

  @BeforeClass
  public static void createAuthorizationHeader() {
    ah = new AuthorizationHeader();
  }

  @Before
  public void clearAuthorizationHeader() {
    ah.setCredentialsAuthInfos(new ArrayList<Credentials>());
  }

  @Test
  public void testAuthorizationHeader() throws ImClientException {
    // Create authorization headers
    Credentials cred = ImCredentials.buildCredentials().withUsername("imuser01")
        .withPassword("invitado");
    ah.addCredential(cred);
    cred = VmrcCredentials.buildCredentials().withUsername("demo")
        .withPassword("demo")
        .withHost("http://servproject.i3m.upv.es:8080/vmrc/vmrc");
    ah.addCredential(cred);
    cred = DummyCredential.buildCredentials();
    ah.addCredential(cred);

    // Check the headers work with the dummy provider
    try {
      InfrastructureManager im =
          new InfrastructureManager(IM_DUMMY_PROVIDER_URL, ah);
      String toscaFile =
          new NoNullOrEmptyFile(new Utf8File(Paths.get(TOSCA_FILE_PATH)))
              .read();
      InfrastructureUri newInfrastructureUri =
          im.createInfrastructure(toscaFile, BodyContentType.TOSCA);
      String uri = newInfrastructureUri.getUri();
      Assert.assertEquals(false, uri.isEmpty());
      String infId = newInfrastructureUri.getInfrastructureId();
      im.destroyInfrastructure(infId);

    } catch (ImClientException exception) {
      ImJavaApiLogger.severe(InfrastructureManagerTest.class,
          exception.getMessage());
      Assert.fail();
    }
  }

  @Test
  public void testAmazonEc2UserPassCredentials() {
    Credentials cred = AmazonEc2Credentials.buildCredentials()
        .withUsername(USER).withPassword(PASS);
    ah.addCredential(cred);
    Assert.assertEquals(EC2_CREDS, ah.serialize());
  }

  @Test
  public void testDockerCredentials() {
    Credentials cred =
        DockerCredentials.buildCredentials().withHost("host_url");
    ah.addCredential(cred);
    Assert.assertEquals(DOCKER_CREDS, ah.serialize());
  }

  @Test
  public void testDummyCredentials() {
    Credentials cred = DummyCredential.buildCredentials();
    ah.addCredential(cred);
    Assert.assertEquals(DUMMY_CREDS, ah.serialize());
  }

  @Test
  public void testFogBowCredentials() {
    Credentials cred = FogBowCredentials.buildCredentials().withHost("host")
        .withProxy("proxy");
    ah.addCredential(cred);
    Assert.assertEquals(FOG_BOW_CREDS, ah.serialize());
  }

  @Test
  public void testGceUserPwdCredentials() {
    Credentials cred = GceCredentials.buildCredentials().withUsername(USER)
        .withPassword(PASS).withProject("testPrj");
    ah.addCredential(cred);
    Assert.assertEquals(GCE_CREDS, ah.serialize());
  }

  @Test
  public void testImUserPassCredentials() {
    Credentials cred =
        ImCredentials.buildCredentials().withUsername(USER).withPassword(PASS);
    ah.addCredential(cred);
    Assert.assertEquals(IM_UP_CREDS, ah.serialize());
  }

  @Test
  public void testImTokenCredentials() {
    Credentials cred = ImCredentials.buildCredentials().withToken("token");
    ah.addCredential(cred);
    Assert.assertEquals(IM_TK_CREDS, ah.serialize());
  }

  @Test
  public void testKubernetesCredentials() {
    Credentials cred = KubernetesCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withHost("host");
    ah.addCredential(cred);
    Assert.assertEquals(KUB_CREDS, ah.serialize());
  }

  @Test
  public void testOcciCredentials() {
    Credentials cred =
        OcciCredentials.buildCredentials().withHost("host").withProxy("proxy");
    ah.addCredential(cred);
    Assert.assertEquals(OCCI_CREDS, ah.serialize());
  }

  @Test
  public void testOpenNebulaUserPassCredentials() {
    Credentials cred = OpenNebulaCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withHost("host");
    ah.addCredential(cred);
    Assert.assertEquals(ONE_UP_CREDS, ah.serialize());
  }

  @Test
  public void testOpenNebulaTokenCredentials() {
    Credentials cred = OpenNebulaCredentials.buildCredentials()
        .withToken("token").withHost("host");
    ah.addCredential(cred);
    Assert.assertEquals(ONE_TK_CREDS, ah.serialize());
  }

  @Test
  public void testOpenStackCredentials() {
    Credentials cred = OpenStackCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withTenant("tenant")
        .withServiceRegion("region").withHost("host")
        .withAuthVersion(OpenstackAuthVersion.PASSWORD_2_0);
    ah.addCredential(cred);
    Assert.assertEquals(OST_CREDS, ah.serialize());
  }

  @Test
  public void testOpenStackCredentialsPassword3() {
    Credentials cred = OpenStackCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withTenant("tenant")
        .withServiceRegion("region").withHost("host")
        .withAuthVersion(OpenstackAuthVersion.PASSWORD_3_X);
    ah.addCredential(cred);
    Assert.assertEquals(OST_CREDS_PASS_3, ah.serialize());
  }

  @Test
  public void testOpenStackCredentialsPassword3Token() {
    Credentials cred = OpenStackCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withTenant("tenant")
        .withServiceRegion("region").withHost("host")
        .withAuthVersion(OpenstackAuthVersion.PASSWORD_3_X_TOKEN);
    ah.addCredential(cred);
    Assert.assertEquals(OST_CREDS_PASS_3_TOKEN, ah.serialize());
  }

  @Test
  public void testVmrcCredentials() {
    Credentials cred = VmrcCredentials.buildCredentials().withUsername(USER)
        .withPassword(PASS).withHost("host");
    ah.addCredential(cred);
    Assert.assertEquals(VMRC_CREDS, ah.serialize());
  }

}
