package es.upv.i3m.grycap.im.auth;

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.file.NoNullOrEmptyFile;
import es.upv.i3m.grycap.file.Utf8File;
import es.upv.i3m.grycap.im.InfrastructureManager;
import es.upv.i3m.grycap.im.InfrastructureManagerTest;
import es.upv.i3m.grycap.im.auth.credential.Credential;
import es.upv.i3m.grycap.im.auth.credential.dummy.DummyCredential;
import es.upv.i3m.grycap.im.auth.credential.im.ImCredential.ImTokenCredential;
import es.upv.i3m.grycap.im.auth.credential.im.ImCredential.ImUsernamePasswordCredential;
import es.upv.i3m.grycap.im.auth.credential.occi.OcciCredential;
import es.upv.i3m.grycap.im.auth.credential.opennebula.OpenNebulaTokenCredential;
import es.upv.i3m.grycap.im.auth.credential.opennebula.OpenNebulaUserPwdCredential;
import es.upv.i3m.grycap.im.auth.credential.openstack.OpenstackAuthVersion;
import es.upv.i3m.grycap.im.auth.credential.openstack.OpenstackCredential;
import es.upv.i3m.grycap.im.auth.credential.vmrc.VmrcCredential;
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

  private static final String DUMMY_CREDS = "id = dummy ; type = Dummy";
  private static final String IM_UP_CREDS =
      "type = InfrastructureManager ; username = imuser01 ; password = pwd";
  private static final String IM_TK_CREDS =
      "type = InfrastructureManager ; token = token";
  private static final String VMRC_CREDS =
      "type = VMRC ; username = demo ; password = pwd ; host = host";
  private static final String OST_CREDS =
      "id = ost ; type = OpenStack ; username = usr ; password = pwd ; host = host ; service_region = region";
  private static final String OCCI_CREDS =
      "id = occi ; type = OCCI ; host = host ; proxy = proxy";
  private static final String ONE_UP_CREDS =
      "id = one ; type = OpenNebula ; username = usr ; password = pwd ; host = host";
  private static final String ONE_TK_CREDS =
      "id = one ; type = OpenNebula ; token = token ; host = host";
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
    ah.setCredentialsAuthInfos(new ArrayList<Credential>());
  }

  @Test
  public void testAuthorizationHeader() throws ImClientException {
    // Create authorization headers
    Credential<?> cred = ImUsernamePasswordCredential.getBuilder()
        .withUsername("imuser01").withPassword("invitado").build();
    ah.addCredential(cred);
    cred = VmrcCredential.getBuilder().withUsername("demo").withPassword("demo")
        .withHost("http://servproject.i3m.upv.es:8080/vmrc/vmrc").build();
    ah.addCredential(cred);
    cred = DummyCredential.getBuilder().withId("dummy").build();
    ah.addCredential(cred);

    // Check the headers work with the dummy provider
    try {
      InfrastructureManager im =
          new InfrastructureManager(IM_DUMMY_PROVIDER_URL, ah.serialize());
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
  public void testDummyCredentials() throws ImClientException {
    Credential<?> cred = DummyCredential.getBuilder().withId("dummy").build();
    ah.addCredential(cred);
    Assert.assertEquals(DUMMY_CREDS, ah.serialize());
  }

  @Test
  public void testImUserPassCredentials() throws ImClientException {
    Credential<?> cred = ImUsernamePasswordCredential.getBuilder()
        .withUsername("imuser01").withPassword("pwd").build();
    ah.addCredential(cred);
    Assert.assertEquals(IM_UP_CREDS, ah.serialize());
  }

  @Test
  public void testImTokenCredentials() throws ImClientException {
    Credential<?> cred =
        ImTokenCredential.getBuilder().withToken("token").build();
    ah.addCredential(cred);
    Assert.assertEquals(IM_TK_CREDS, ah.serialize());
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
  public void testOpenNebulaUserPassCredentials() throws ImClientException {
    Credential<?> cred = OpenNebulaUserPwdCredential.getBuilder().withId("one")
        .withUsername("usr").withPassword("pwd").withHost("host").build();
    ah.addCredential(cred);
    Assert.assertEquals(ONE_UP_CREDS, ah.serialize());
  }

  @Test
  public void testOpenNebulaTokenCredentials() throws ImClientException {
    Credential<?> cred = OpenNebulaTokenCredential.getBuilder().withId("one")
        .withToken("token").withHost("host").build();
    ah.addCredential(cred);
    Assert.assertEquals(ONE_TK_CREDS, ah.serialize());
  }

  @Test
  public void testOcciCredentials() throws ImClientException {
    Credential<?> cred = OcciCredential.getBuilder().withId("occi")
        .withHost("host").withProxy("proxy").build();
    ah.addCredential(cred);
    Assert.assertEquals(OCCI_CREDS, ah.serialize());
  }

}
