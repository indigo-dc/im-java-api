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

import java.util.Map;

public class InfOutputValues {

  @JsonProperty("outputs")
  private Map<String, String> outputs;

  public InfOutputValues(@JsonProperty("outputs") Map<String, String> outputs) {
    this.outputs = outputs;
  }

  public Map<String, String> getOutputs() {
    return outputs;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(outputs).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof InfOutputValues) {
      InfOutputValues outValues = (InfOutputValues) other;
      return new EqualsBuilder().append(outputs, outValues.outputs).isEquals();
    }
    return false;
  }

}
