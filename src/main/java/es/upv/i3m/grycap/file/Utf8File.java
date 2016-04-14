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
import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Utf8File implements File {

  private String filePath;

  public Utf8File(final String filePath) {
    this.filePath = filePath;
  }

  /**
   * Read the file specified by the path parameter using the standard charset
   * UTF8.
   *
   * @return : String with the loaded file
   * @throws FileException
   *           : file related exception
   */
  @Override
  public String read() throws FileException {
    try {
      return new String(Files.readAllBytes(Paths.get(this.filePath)),
          StandardCharsets.UTF_8);
    } catch (IOException ex) {
      ImJavaApiLogger.severe(this.getClass(),
          ImMessages.EXCEPTION_READING_FILE + ": " + this.filePath);
      throw new FileException(ImMessages.EXCEPTION_READING_FILE, ex);
    }
  }

  @Override
  public String getFilePath() {
    return this.filePath;
  }

}