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

package es.upv.i3m.grycap.im.rest.client;

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.im.exceptions.ImClientServerErrorException;
import es.upv.i3m.grycap.im.exceptions.ImClientErrorException;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.pojo.ResponseError;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImClientTest extends ImTestWatcher {

  private static ImClient imClient;
  private static final String IM_DUMMY_PROVIDER_URL =
      "https://appsgrycap.i3m.upv.es:31443/im-dev/";
  private static final String AUTH_FILE_PATH = "./src/test/resources/auth.dat";
  private static final Integer EXPECTED_ERROR_CODE = 404;
  private static final String EXPECTED_ERROR_MESSAGE = "Not found: '/'";
  private static final String IM_FAKE_PROVIDER_URL = "http://localhost:1234";

  private ImClient getImClient() {
    return imClient;
  }

  /**
   * Creates a new rest client.
   */
  @BeforeClass
  public static void setRestClient() {
    try {
      imClient = new ImClient(IM_DUMMY_PROVIDER_URL, AUTH_FILE_PATH);
    } catch (ImClientException exception) {
      ImJavaApiLogger.severe(ImClientTest.class, exception.getMessage());
      Assert.fail();
    }
  }

  @Test
  public void testDeleteError() throws ImClientException {
    try {
      // Force an error
      getImClient().delete("", String.class);
    } catch (ImClientErrorException exception) {
      checkError(exception);
    }
  }

  @Test
  public void testGetError() throws ImClientException {
    try {
      // Force an error
      getImClient().get("", String.class);
    } catch (ImClientErrorException exception) {
      checkError(exception);
    }
  }

  @Test
  public void testPostError() throws ImClientException {
    try {
      // Force an error
      getImClient().post("", String.class);
    } catch (ImClientErrorException exception) {
      checkError(exception);
    }
  }

  @Test
  public void testPutError() throws ImClientException {
    try {
      // Force an error
      getImClient().put("", String.class);
    } catch (ImClientErrorException exception) {
      checkError(exception);
    }
  }

  @Test
  public void testServerError() throws ImClientException {
    try {
      imClient = new ImClient(IM_FAKE_PROVIDER_URL, AUTH_FILE_PATH);
    } catch (ImClientException exception) {
      ImJavaApiLogger.severe(ImClientTest.class, exception.getMessage());
      Assert.fail();
    }

    try {
      // Force an error
      getImClient().delete("", String.class);
    } catch (ImClientServerErrorException exception) {
    }
  }

  private void checkError(ImClientErrorException exception) {
    ResponseError error = exception.getResponseError();
    Assert.assertEquals(EXPECTED_ERROR_CODE, error.getCode());
    Assert.assertEquals(EXPECTED_ERROR_MESSAGE, error.getMessage());
  }
}
