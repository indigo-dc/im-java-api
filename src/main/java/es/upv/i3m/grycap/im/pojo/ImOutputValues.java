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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Pojo class used to store the JSON values returned by the IM.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImOutputValues {

  @JsonIgnore
  private Map<String, Object> properties = new HashMap<String, Object>();

  /**
   * @return Map with the fields populated by the JSON used to create the class.
   */
  @JsonAnyGetter
  public Map<String, Object> getProperties() {
    return properties;
  }

  /**
   * Sets a new key/value in the properties map.
   * 
   * @param name
   *          : property key
   * @param value
   *          : property value
   */
  @JsonAnySetter
  public void setProperty(String name, Object value) {
    this.properties.put(name, value);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(properties).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof ImOutputValues) {
      ImOutputValues rhs = (ImOutputValues) other;
      return new EqualsBuilder().append(properties, rhs.properties).isEquals();
    }
    return false;
  }
}
