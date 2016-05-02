package es.upv.i3m.grycap.im.rest.client.ssl;

import es.upv.i3m.grycap.im.exceptions.ImClientException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

public class SslClientTest {

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
  }

  @Test
  public void testSslTrustAllClients() throws ImClientException {
    SslTrustAllClient sslClient = new SslTrustAllClient();
    Client client = sslClient.createClient();
    client.target(target).path("").request(MediaType.TEXT_PLAIN).get();
  }

}
