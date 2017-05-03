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

import es.upv.i3m.grycap.im.auth.credentials.properties.AuthTokenProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.BaseProperties;
import es.upv.i3m.grycap.im.auth.credentials.properties.BaseUrlProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.DomainProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.GenericProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.HostProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.OpenStackAuthVersionProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.PasswordProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.PrivateKeyProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ProjectProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.PublicKeyProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ServiceNameProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ServiceRegionProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.TenantProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.TokenProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.UserNameProperty;
import es.upv.i3m.grycap.im.auth.credentials.providers.OpenStackAuthVersion;

import org.junit.Assert;
import org.junit.Test;

public class PropertiesTest extends GenericCredentials {

  private static final String ID = "id";
  private static final String TYPE = "type";
  private static final String BP_EXPECTED_RESULT = "id = id ; type = type";

  private static final BaseProperties BP = new BaseProperties(ID, TYPE);
  private static final String PROPERTY_NAME = "name";
  private static final String PROPERTY_VALUE = "value";
  private static final String ERROR_MESSAGE = "message";
  private static final String ERROR_NULL_CREDENTIALS =
      "Empty credentials error.";

  @Test
  public void testBaseProperties() {
    BaseProperties bp = new BaseProperties(ID, TYPE);
    Assert.assertEquals(BP_EXPECTED_RESULT, bp.serialize());
  }

  @Test
  public void testNullBaseProperties() {
    BaseProperties bp = new BaseProperties(null, null);
    Assert.assertEquals("", bp.serialize());
  }

  @Test
  public void testGenericProperties() {
    GenericProperty gp =
        new GenericProperty(BP, PROPERTY_NAME, PROPERTY_VALUE, ERROR_MESSAGE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; name = value", gp.serialize());
  }

  @Test
  public void testGenericPropertyNullPropertyValue() {
    try {
      GenericProperty gp =
          new GenericProperty(BP, PROPERTY_NAME, null, ERROR_MESSAGE);
      gp.serialize();
    } catch (IllegalArgumentException exception) {
      Assert.assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
  }

  @Test
  public void testGenericPropertyEmptyPropertyValue() {
    try {
      GenericProperty gp =
          new GenericProperty(BP, PROPERTY_NAME, "", ERROR_MESSAGE);
      gp.serialize();
    } catch (IllegalArgumentException exception) {
      Assert.assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
  }

  @Test
  public void testGenericPropertyNullCredentials() {
    try {
      GenericProperty gp = new GenericProperty(null, PROPERTY_NAME,
          PROPERTY_VALUE, ERROR_MESSAGE);
      gp.serialize();
    } catch (NullPointerException exception) {
      Assert.assertEquals(ERROR_NULL_CREDENTIALS, exception.getMessage());
    }
  }

  @Test
  public void testAuthTokenProperty() {
    AuthTokenProperty p = new AuthTokenProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(
        BP_EXPECTED_RESULT + " ; auth_token = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testBaseUrlProperty() {
    BaseUrlProperty p = new BaseUrlProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; base_url = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testHostProperty() {
    HostProperty p = new HostProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; host = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testOpenStackAuthVersionPropertyPassword2() {
    OpenStackAuthVersionProperty p =
        new OpenStackAuthVersionProperty(BP, OpenStackAuthVersion.PASSWORD_2_0);
    Assert.assertEquals(BP_EXPECTED_RESULT, p.serialize());
  }

  @Test
  public void testOpenStackAuthVersionPropertyPassword3() {
    OpenStackAuthVersionProperty p =
        new OpenStackAuthVersionProperty(BP, OpenStackAuthVersion.PASSWORD_3_X);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; auth_version = "
        + OpenStackAuthVersion.PASSWORD_3_X.getValue(), p.serialize());
  }

  @Test
  public void testOpenStackAuthVersionPropertyPassword3Token() {
    OpenStackAuthVersionProperty p = new OpenStackAuthVersionProperty(BP,
        OpenStackAuthVersion.PASSWORD_3_X_TOKEN);
    Assert.assertEquals(
        BP_EXPECTED_RESULT + " ; auth_version = "
            + OpenStackAuthVersion.PASSWORD_3_X_TOKEN.getValue(),
        p.serialize());
  }

  @Test
  public void testPasswordProperty() {
    PasswordProperty p = new PasswordProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; password = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testPrivateKeyProperty() {
    PrivateKeyProperty p = new PrivateKeyProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(
        BP_EXPECTED_RESULT + " ; private_key = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testProjectProperty() {
    ProjectProperty p = new ProjectProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; project = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testPublicKeyProperty() {
    PublicKeyProperty p = new PublicKeyProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(
        BP_EXPECTED_RESULT + " ; public_key = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testServiceNameProperty() {
    ServiceNameProperty p = new ServiceNameProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(
        BP_EXPECTED_RESULT + " ; service_name = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testServiceRegionProperty() {
    ServiceRegionProperty p = new ServiceRegionProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(
        BP_EXPECTED_RESULT + " ; service_region = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testTenantProperty() {
    TenantProperty p = new TenantProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; tenant = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testTokenProperty() {
    TokenProperty p = new TokenProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; token = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testUsernameProperty() {
    UserNameProperty p = new UserNameProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; username = " + PROPERTY_VALUE,
        p.serialize());
  }

  @Test
  public void testDomainProperty() {
    DomainProperty p = new DomainProperty(BP, PROPERTY_VALUE);
    Assert.assertEquals(BP_EXPECTED_RESULT + " ; domain = " + PROPERTY_VALUE,
        p.serialize());
  }

}
