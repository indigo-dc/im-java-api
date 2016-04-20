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

import es.upv.i3m.grycap.im.rest.client.ImResponsesReader;
import es.upv.i3m.grycap.im.rest.client.RestClient;

import org.glassfish.jersey.SslConfigurator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class SslClient implements RestClient {

  private SSLContext ctx;
  private HostnameVerifier verifier;

  public SslClient() {
    this.ctx = SslConfigurator.newInstance(true).createSSLContext();
  }

  public SslClient(final SSLContext ctx) {
    this.ctx = ctx;
  }

  public SslClient(final SSLContext ctx, final HostnameVerifier verifier) {
    this.ctx = ctx;
    this.verifier = verifier;
  }

  /**
   * Build a new Rest client client with SSL security.
   * 
   * @return : new REST client
   */
  @Override
  public Client createClient() {
    if (ctx == null) {
      // Generate generic SSL context
      ctx = SslConfigurator.newInstance(true).createSSLContext();
    }
    return verifier == null
        ? ClientBuilder.newBuilder().sslContext(ctx)
            .register(ImResponsesReader.class).build()
        : ClientBuilder.newBuilder().sslContext(ctx).hostnameVerifier(verifier)
            .register(ImResponsesReader.class).build();
  }
}