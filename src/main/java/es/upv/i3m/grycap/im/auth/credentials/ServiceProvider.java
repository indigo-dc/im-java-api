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

public enum ServiceProvider {

  //@formatter:off
  INFRASTRUCTURE_MANAGER("im","InfrastructureManager"),
  VMRC("vmrc","VMRC"),
  DUMMY("dummy","Dummy"),
  OPENNEBULA("one","OpenNebula"),
  EC2("ec2","EC2"),
  FOG_BOW("fog","FogBow"),
  OPENSTACK("ost","OpenStack"),
  OCCI("occi","OCCI"),
  DOCKER("docker","Docker"),
  GCE("gce","GCE"),
  AZURE("azure","Azure"),
  KUBERNETES("kub","Kubernetes");
  //@formatter:on

  private final String id;
  private final String type;

  private ServiceProvider(final String id, final String type) {
    this.id = id;
    this.type = type;
  }

  public final String getId() {
    return id;
  }

  public final String getType() {
    return type;
  }

}
