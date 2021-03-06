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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.upv.i3m.grycap.im.pojo.deserializer.PropertyDeserializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Generic property class that stores all the different properties that can have
 * the infrastructure or the virtual machines.
 */
@JsonDeserialize(using = PropertyDeserializer.class)
public class Property {

  private final String key;
  private final String value;

  public Property(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(key).append(value).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof Property) {
      Property prop = (Property) other;
      return new EqualsBuilder().append(key, prop.key).append(value, prop.value)
          .isEquals();
    }
    return false;
  }

}
