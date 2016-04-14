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

package es.upv.i3m.grycap.im.exceptions;

/**
 * Exception launched when a enumerator is not found between the predefined
 * ones.
 */
public class NoEnumFoundException extends ImClientException {

  private static final long serialVersionUID = -1418837501975744151L;

  public NoEnumFoundException(String message) {
    super(message);
  }

  public NoEnumFoundException(String message, Exception ex) {
    super(message, ex);
  }

  public NoEnumFoundException(Exception ex) {
    super(ex);
  }
}
