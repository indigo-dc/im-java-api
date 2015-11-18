/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.upv.i3m.grycap.im.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stores the parameters passed to the REST calls in a (name, ...values)
 * structure
 */
public class RestCallParameter {

    String parameterName;
    List<Object> parameterValue;

    public RestCallParameter(String parameterName) {
        this.parameterName = parameterName;
    }

    public RestCallParameter(String parameterName, Object parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = Arrays.asList(parameterValue);
    }

    public String getParameterName() {
        return parameterName;
    }

    public Object[] getParameterValues() {
        return parameterValue.toArray();
    }

    public void addValue(Object value) {
        if (parameterValue == null) {
            parameterValue = new ArrayList<>();
        }
        parameterValue.add(value);
    }
}
