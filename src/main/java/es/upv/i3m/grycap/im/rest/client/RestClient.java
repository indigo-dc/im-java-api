package es.upv.i3m.grycap.im.rest.client;

import es.upv.i3m.grycap.im.exceptions.ImClientException;

import javax.ws.rs.client.Client;

public interface RestClient {

  public Client createClient() throws ImClientException;

}
