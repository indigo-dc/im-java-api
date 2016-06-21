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

package es.upv.i3m.grycap.im.rest.client.parameters;

import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the parameters passed to the REST calls in a (name, ...values)
 * structure
 */
public class Parameter implements RestParameter {

  private final String name;
  private final List<Object> values;

  public Parameter(String name) {
    this.name = name;
    this.values = new ArrayList<>();
  }

  /**
   * Create a REST parameter.
   * 
   * @param name
   *          : name of the parameter
   * @param value
   *          : value of the parameter
   */
  public Parameter(String name, Object value) {
    this.name = name;
    this.values = new ArrayList<>();
    addValue(value);
  }

  /**
   * Create a REST parameter.
   * 
   * @param name
   *          : name of the parameter
   * @param values
   *          : list of values of the parameter
   */
  public Parameter(String name, List<?> values) {
    this.name = name;
    this.values = new ArrayList<>();
    setParameterValues(values);
  }

  /**
   * Adds a generic object to the parameters.
   * 
   * @param value
   *          : generic value to add
   */
  @Override
  public void addValue(Object value) {
    values.add(value);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Object[] getValues() {
    return values.toArray();
  }

  private void setParameterValues(List<?> parameterValues) {
    if (parameterValues != null && !parameterValues.isEmpty()) {
      for (Object parameterValue : parameterValues) {
        addValue(parameterValue);
      }
    } else {
      ImJavaApiLogger.warning(this.getClass(),
          ImMessages.WARNING_NULL_OR_EMPTY_PARAMETER_VALUES);
    }
  }
}
