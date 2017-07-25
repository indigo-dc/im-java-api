/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.upv.i3m.grycap.im.auth.credentials;

import es.upv.i3m.grycap.im.auth.credentials.providers.AmazonEc2Credentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.AzureCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.DockerCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.DummyCredential;
import es.upv.i3m.grycap.im.auth.credentials.providers.FogBowCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.GceCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.ImCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.KubernetesCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.OcciCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.OpenNebulaCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.OpenStackAuthVersion;
import es.upv.i3m.grycap.im.auth.credentials.providers.OpenStackCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.VmrcCredentials;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CredentialProvidersTest extends GenericCredentials {

  // Test user/pass
  private static final String USER = "user";
  private static final String PASS = "pass";
  private static final String USER_PASS = "username = " + USER + " ; password = " + PASS;

  // Test ID
  private static final String CUSTOM_ID_PROPERTY = "customId";
  private static final String CUSTOM_ID = "id = " + CUSTOM_ID_PROPERTY + " ; ";

  // Credentials
  private static final String DUMMY_CREDS = "id = dummy ; type = Dummy";
  private static final String DUMMY_WITH_ID = CUSTOM_ID + "type = Dummy";
  private static final String IM_UP_CREDS = "id = im ; type = InfrastructureManager ; " + USER_PASS;
  private static final String IM_TK_CREDS =
      "id = im ; type = InfrastructureManager ; token = token";
  private static final String IM_WITH_ID = CUSTOM_ID + "type = InfrastructureManager";
  private static final String VMRC_CREDS =
      "id = vmrc ; type = VMRC ; " + USER_PASS + " ; host = host";
  private static final String VMRC_WITH_ID = CUSTOM_ID + "type = VMRC";
  private static final String OST_CREDS = "id = ost ; type = OpenStack ; " + USER_PASS
      + " ; tenant = tenant ; service_region = region ; host = host ; base_url = base ;"
      + " service_name = name ; auth_token = token ; domain = domain";
  private static final String OST_CREDS_PASS_3 = "id = ost ; type = OpenStack ; " + USER_PASS
      + " ; tenant = tenant ; service_region = region ; host = host ; auth_version = 3.X_password";
  private static final String OST_CREDS_PASS_3_TOKEN = "id = ost ; type = OpenStack ; " + USER_PASS
      + " ; tenant = tenant ; service_region = region ; host = host ; auth_version = 3.x_oidc_access_token";
  private static final String OST_WITH_ID = CUSTOM_ID + "type = OpenStack";
  private static final String OCCI_CREDS = "id = occi ; type = OCCI ; host = host ; proxy = proxy";
  private static final String OCCI_WITH_ID = CUSTOM_ID + "type = OCCI";
  private static final String ONE_UP_CREDS =
      "id = one ; type = OpenNebula ; " + USER_PASS + " ; host = host";
  private static final String ONE_TK_CREDS =
      "id = one ; type = OpenNebula ; token = token ; host = host";
  private static final String ONE_WITH_ID = CUSTOM_ID + "type = OpenNebula";
  private static final String EC2_CREDS = "id = ec2 ; type = EC2 ; " + USER_PASS;
  private static final String EC2_WITH_ID = CUSTOM_ID + "type = EC2";
  private static final String AZURE_CREDS = "id = azure ; type = Azure ; " + "username = " + USER
      + " ; private_key = public ; public_key = private";
  private static final String AZURE_WITH_ID = CUSTOM_ID + "type = Azure";
  private static final String DOCKER_CREDS = "id = docker ; type = Docker ; host = host_url";
  private static final String DOCKER_WITH_ID = CUSTOM_ID + "type = Docker";
  private static final String GCE_CREDS =
      "id = gce ; type = GCE ; " + USER_PASS + " ; project = testPrj";
  private static final String GCE_WITH_ID = CUSTOM_ID + "type = GCE";
  private static final String KUB_CREDS =
      "id = kub ; type = Kubernetes ; username = user ; password = pass ; host = host";
  private static final String KUB_WITH_ID = CUSTOM_ID + "type = Kubernetes";
  private static final String FOG_BOW_CREDS =
      "id = fog ; type = FogBow ; host = host ; proxy = proxy";
  private static final String FOG_BOW_WITH_ID = CUSTOM_ID + "type = FogBow";

  @Test
  public void testServiceProviderEnumMethods() {
    Assert.assertEquals(ServiceProvider.DOCKER,
        ServiceProvider.valueOf("DOCKER"));
    Assert.assertEquals(12, ServiceProvider.values().length);
  }

  @Test
  public void testAmazonEc2UserPassCredentials() {
    Credentials cred = AmazonEc2Credentials.buildCredentials()
        .withUsername(USER).withPassword(PASS);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(EC2_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testAmazonEc2WithId() {
    Credentials cred = AmazonEc2Credentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(EC2_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testAzureCredentials() {
    Credentials cred = AzureCredentials.buildCredentials().withUsername(USER)
        .withPublicKey("public").withPrivateKey("private");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(AZURE_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testAzureWithId() {
    Credentials cred = AzureCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(AZURE_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testDockerCredentials() {
    Credentials cred =
        DockerCredentials.buildCredentials().withHost("host_url");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(DOCKER_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testDockerWithId() {
    Credentials cred = DockerCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(DOCKER_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testDummyCredentials() {
    Credentials cred = DummyCredential.buildCredentials();
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(DUMMY_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testDummyWithId() {
    Credentials cred = DummyCredential.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(DUMMY_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testFogBowCredentials() {
    Credentials cred = FogBowCredentials.buildCredentials().withHost("host")
        .withProxy("proxy");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(FOG_BOW_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testFogBowWithId() {
    Credentials cred = FogBowCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(FOG_BOW_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testGceUserPwdCredentials() {
    Credentials cred = GceCredentials.buildCredentials().withUsername(USER)
        .withPassword(PASS).withProject("testPrj");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(GCE_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testGceWithId() {
    Credentials cred = GceCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(GCE_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testImUserPassCredentials() {
    Credentials cred =
        ImCredentials.buildCredentials().withUsername(USER).withPassword(PASS);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(IM_UP_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testImTokenCredentials() {
    Credentials cred = ImCredentials.buildCredentials().withToken("token");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(IM_TK_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testImWithId() {
    Credentials cred = ImCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(IM_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testKubernetesCredentials() {
    Credentials cred = KubernetesCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withHost("host");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(KUB_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testKubernetesWithId() {
    Credentials cred = KubernetesCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(KUB_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testOcciCredentials() {
    Credentials cred =
        OcciCredentials.buildCredentials().withHost("host").withProxy("proxy");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(OCCI_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testOcciWithId() {
    Credentials cred = OcciCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(OCCI_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testOpenNebulaUserPassCredentials() {
    Credentials cred = OpenNebulaCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withHost("host");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(ONE_UP_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testOpenNebulaTokenCredentials() {
    Credentials cred = OpenNebulaCredentials.buildCredentials()
        .withToken("token").withHost("host");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(ONE_TK_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testOpenNebulaWithId() {
    Credentials cred = OpenNebulaCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(ONE_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testOpenStackCredentials() {
    Credentials cred = OpenStackCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withTenant("tenant")
        .withServiceRegion("region").withHost("host")
        .withAuthVersion(OpenStackAuthVersion.PASSWORD_2_0).withBaseUrl("base")
        .withServiceName("name").withAuthToken("token").withDomain("domain");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(OST_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testOpenStackCredentialsPassword3() {
    Credentials cred = OpenStackCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withTenant("tenant")
        .withServiceRegion("region").withHost("host")
        .withAuthVersion(OpenStackAuthVersion.PASSWORD_3_X);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(OST_CREDS_PASS_3, getAuthorizationHeader().serialize());
  }

  @Test
  public void testOpenStackCredentialsPassword3Token() {
    Credentials cred = OpenStackCredentials.buildCredentials()
        .withUsername(USER).withPassword(PASS).withTenant("tenant")
        .withServiceRegion("region").withHost("host")
        .withAuthVersion(OpenStackAuthVersion.PASSWORD_3_X_TOKEN);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(OST_CREDS_PASS_3_TOKEN,
        getAuthorizationHeader().serialize());
  }

  @Test
  public void testOpenStackWithId() {
    Credentials cred = OpenStackCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(OST_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test
  public void testVmrcCredentials() {
    Credentials cred = VmrcCredentials.buildCredentials().withUsername(USER)
        .withPassword(PASS).withHost("host");
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(VMRC_CREDS, getAuthorizationHeader().serialize());
  }

  @Test
  public void testVmrcWithId() {
    Credentials cred = VmrcCredentials.buildCredentials(CUSTOM_ID_PROPERTY);
    getAuthorizationHeader().addCredential(cred);
    Assert.assertEquals(VMRC_WITH_ID, getAuthorizationHeader().serialize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCredentials() {
    Credentials cred = VmrcCredentials.buildCredentials().withPassword(PASS)
        .withUsername(null).withHost("host");
    getAuthorizationHeader().addCredential(cred);
    getAuthorizationHeader().serialize();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyCredentials() {
    Credentials cred = VmrcCredentials.buildCredentials().withUsername("");
    getAuthorizationHeader().addCredential(cred);
    getAuthorizationHeader().serialize();
  }

  @Test
  public void testAuthorizationHeaderCredentials() {
    // Create authorization headers
    Credentials cred =
        ImCredentials.buildCredentials().withUsername(USER).withPassword(PASS);
    getAuthorizationHeader().addCredential(cred);
    cred = VmrcCredentials.buildCredentials().withUsername(USER)
        .withPassword(PASS).withHost("host");
    getAuthorizationHeader().addCredential(cred);
    cred = DummyCredential.buildCredentials();
    getAuthorizationHeader().addCredential(cred);

    String expected = IM_UP_CREDS + "\\n" + VMRC_CREDS + "\\n" + DUMMY_CREDS;
    Assert.assertEquals(expected, getAuthorizationHeader().serialize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetEmptyAuthorizationHeaderCredentials() {
    getAuthorizationHeader().setCredentialsAuthInfos(null);
  }

  @Test
  public void testAuthorizationHeaderGetCredentials() {
    List<Credentials> credentials = new ArrayList<Credentials>();
    Credentials cred =
        ImCredentials.buildCredentials().withUsername(USER).withPassword(PASS);
    getAuthorizationHeader().addCredential(cred);
    credentials.add(cred);
    cred = VmrcCredentials.buildCredentials().withUsername(USER)
        .withPassword(PASS).withHost("host");
    getAuthorizationHeader().addCredential(cred);
    credentials.add(cred);
    cred = DummyCredential.buildCredentials();
    getAuthorizationHeader().addCredential(cred);
    credentials.add(cred);

    Assert.assertEquals(credentials, getAuthorizationHeader().getCredentials());
  }
}
