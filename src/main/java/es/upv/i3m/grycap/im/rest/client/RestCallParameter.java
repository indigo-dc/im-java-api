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

import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the parameters passed to the REST calls in a (name, ...values)
 * structure
 */
public class RestCallParameter {

  private String parameterName;
  private List<Object> parameterValues;

  public RestCallParameter(String parameterName) {
    setParameterName(parameterName);
  }

  public RestCallParameter(String parameterName, Object parameterValue) {
    setParameterName(parameterName);
    addValue(parameterValue);
  }

  public RestCallParameter(String parameterName, List<?> parameterValue) {
    setParameterName(parameterName);
    setParameterValues(parameterValue);
  }

  /**
   * Adds a generic object to the parameters.
   * 
   * @param value
   *          : generic value to add
   */
  public void addValue(Object value) {
    if (parameterValues == null) {
      parameterValues = new ArrayList<>();
    }
    parameterValues.add(value);
  }

  private void checkNullValue(Object value) {
    // Runtime exception if returns a null value
    if (value == null) {
      ImJavaApiLogger.severe(this.getClass(), ImMessages.EXCEPTION_NULL_VALUE);
      throw new NullPointerException();
    }
  }

  public String getParameterName() {
    checkNullValue(parameterName);
    return parameterName;
  }

  public Object[] getParameterValues() {
    checkNullValue(parameterValues);
    return parameterValues.toArray();
  }

  private void setParameterName(String parameterName) {
    if (parameterName == null) {
      ImJavaApiLogger.warning(this.getClass(),
          ImMessages.WARNING_NULL_PARAMETER_NAME);
    }
    this.parameterName = parameterName;
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
