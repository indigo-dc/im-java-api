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

package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class AmazonEc2Credentials
    extends GenericCredentials<AmazonEc2Credentials> {

  private AmazonEc2Credentials() {
    super(ServiceProvider.EC2);
  }

  private AmazonEc2Credentials(String id) {
    super(ServiceProvider.EC2, id);
  }
  
  public static AmazonEc2Credentials buildCredentials() {
    return new AmazonEc2Credentials();
  }
  
  public static AmazonEc2Credentials buildCredentials(String id) {
    return new AmazonEc2Credentials(id);
  }

}
