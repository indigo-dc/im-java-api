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

package es.upv.i3m.grycap.file;

import es.upv.i3m.grycap.im.exceptions.FileException;

public final class EscapedNewLinesFile implements File {

  private final File file;

  public EscapedNewLinesFile(final File file) {
    this.file = file;
  }

  /**
   * Read the file specified and escapes all the new lines (i.e. replace all the
   * '\n' with '\\n').
   *
   * @return : String with the newlines replaced
   * @throws FileException
   *           : file related exception
   */
  @Override
  public String read() throws FileException {
    return this.file.read().replace("\n", "\\n");
  }

}
