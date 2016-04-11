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

import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;

public class SslTrustAllRestClient implements RestClient {

  private TrustManager[] getCerts() {
    return new TrustManager[] { new X509TrustManager() {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType)
          throws CertificateException {
        // Trust all servers
      }

      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType)
          throws CertificateException {
        // Trust all clients
      }
    } };
  }

  /**
   * Build a new Rest client with SSL security that trusts all certificates in
   * SSL/TLS.
   * 
   * @return : new REST client
   * @throws ImClientException
   *           : generic exception in the rest client
   */
  @Override
  public Client createClient() throws ImClientException {
    try {
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(null, getCerts(), new SecureRandom());

      HostnameVerifier verifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostName, SSLSession sslSession) {
          return true;
        }
      };

      return new SslRestClient(ctx, verifier).createClient();

    } catch (GeneralSecurityException exception) {
      ImJavaApiLogger.severe(this.getClass(), exception);
      throw new ImClientException(exception);
    }

  }
}