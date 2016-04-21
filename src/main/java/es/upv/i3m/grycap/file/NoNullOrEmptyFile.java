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

import java.nio.file.Path;

public final class NoNullOrEmptyFile implements File {

  private final File file;

  public NoNullOrEmptyFile(final File file) {
    this.file = file;
  }

  /**
   * Read the file specified and checks that the file is not null or empty.
   *
   * @return : String with the content of the file
   * @throws FileException
   *           : file related exception
   */
  @Override
  public String read() throws FileException {
    String fileContent = this.file.read();
    if (fileContent == null || fileContent.isEmpty()) {
      ImJavaApiLogger.severe(this.getClass(),
          ImMessages.EXCEPTION_FILE_NULL_OR_EMPTY + ": "
              + this.file.getFilePath());
      throw new FileException(ImMessages.EXCEPTION_FILE_NULL_OR_EMPTY);
    }
    return fileContent;
  }

  @Override
  public Path getFilePath() {
    return this.file.getFilePath();
  }

}
