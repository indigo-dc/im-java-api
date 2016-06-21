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

package es.upv.i3m.grycap.im.rest.client.ssl;

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.im.exceptions.ImClientException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

public class SslClientTest extends ImTestWatcher {

  private ClientAndServer mockServer;
  private final String target = "https://127.0.0.1:1080";

  @Before
  public void startServer() {
    mockServer = ClientAndServer.startClientAndServer(1080);
  }

  @After
  public void stopServer() {
    mockServer.stop();
  }

  @Test(expected = ProcessingException.class)
  public void testSslClient() throws ImClientException {
    SslClient sslClient = new SslClient(null, null);
    Client client = sslClient.createClient();
    client.target(target).path("").request(MediaType.TEXT_PLAIN).get();

    // System.out.println(getLogOutput());
  }

  @Test
  public void testSslTrustAllClients() throws ImClientException {
    SslTrustAllClient sslClient = new SslTrustAllClient();
    Client client = sslClient.createClient();
    client.target(target).path("").request(MediaType.TEXT_PLAIN).get();
  }

}
