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

package es.upv.i3m.grycap.im.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

/**
 * This class stores all the information related to the virtual machines.
 * Depending on the deployment definition of the machines this information can
 * vary. A generic structure is used to cope with all the possible cases.
 */
public class VirtualMachineInfo {

  private final List<Map<String, Object>> vmProperties;

  public VirtualMachineInfo(@JsonProperty("radl") List<Map<String, Object>> vmProperties) {
    this.vmProperties = vmProperties;
  }

  public List<Map<String, Object>> getVmProperties() {
    return vmProperties;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(vmProperties).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof VirtualMachineInfo) {
      VirtualMachineInfo vmInfo = (VirtualMachineInfo) other;
      return new EqualsBuilder().append(vmProperties, vmInfo.vmProperties)
          .isEquals();
    }
    return false;
  }

}
