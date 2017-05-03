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

import es.upv.i3m.grycap.file.NoNullOrEmptyFile;
import es.upv.i3m.grycap.file.Utf8File;
import es.upv.i3m.grycap.im.InfrastructureManager;
import es.upv.i3m.grycap.im.InfrastructureManagerTest;
import es.upv.i3m.grycap.im.auth.credentials.providers.DummyCredential;
import es.upv.i3m.grycap.im.auth.credentials.providers.ImCredentials;
import es.upv.i3m.grycap.im.auth.credentials.providers.VmrcCredentials;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.pojo.InfrastructureUri;
import es.upv.i3m.grycap.im.rest.client.BodyContentType;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class AuthorizationHeaderTest extends GenericCredentials {

  // IM information
  private static final String IM_DUMMY_PROVIDER_URL =
      "https://servproject.i3m.upv.es:8811";
  private static final String TOSCA_FILE_PATH =
      "./src/test/resources/tosca/galaxy_tosca.yaml";

  @Test
  public void testAuthorizationHeader() throws ImClientException {
    // Create authorization headers
    Credentials cred = ImCredentials.buildCredentials().withUsername("imuser01")
        .withPassword("invitado");
    getAuthorizationHeader().addCredential(cred);
    cred = VmrcCredentials.buildCredentials().withUsername("demo")
        .withPassword("demo")
        .withHost("http://servproject.i3m.upv.es:8080/vmrc/vmrc");
    getAuthorizationHeader().addCredential(cred);
    cred = DummyCredential.buildCredentials();
    getAuthorizationHeader().addCredential(cred);

    // Check the headers work with the dummy provider
    try {
      InfrastructureManager im = new InfrastructureManager(
          IM_DUMMY_PROVIDER_URL, getAuthorizationHeader());
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
}
