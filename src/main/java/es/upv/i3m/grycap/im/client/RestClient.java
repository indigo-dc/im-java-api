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

package es.upv.i3m.grycap.im.client;

import org.glassfish.jersey.SslConfigurator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestClient {

  // Sets the support to SSL or not
  private boolean ssl;

  public RestClient(boolean ssl) {
    this.ssl = ssl;
  }

  /**
   * Build a new Rest client.
   * 
   * @return : new REST client
   */
  public Client createClient() {
    return this.ssl ? ClientBuilder.newBuilder()
        .sslContext(SslConfigurator.newInstance(true).createSSLContext())
        .build() : ClientBuilder.newBuilder().build();
  }
}