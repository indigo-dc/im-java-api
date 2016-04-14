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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FileWithInternalPath implements File {

  private final File file;
  // Find a string like 'file(path_fo_file)' and divide it in three groups
  private static final String FILE_PATH_PATTERN = "(file\\()(.*?)(\\))";
  // The second group of the pattern is the file path
  private static final Integer PATH_TO_URL_PATTERN_GROUP_ID = 2;

  public FileWithInternalPath(final File file) {
    this.file = file;
  }

  @Override
  public String getFilePath() {
    return this.file.getFilePath();
  }

  /**
   * Read the specified file and Check for 'file(' strings inside the file
   * passed. After that puts the content of the referenced file in the string
   * returned.
   *
   * @return : String with the content of the file
   * @throws FileException
   *           : file related exception
   */
  @Override
  public String read() throws FileException {
    String fileContent = this.file.read();
    Matcher matcher = createPatternMatcher(fileContent);
    return replacePatternByFileContent(fileContent, matcher);
  }

  /**
   * Checks for the pattern specified in the file content passed.<br>
   * If the pattern is found, replaces the file path by the content of the file
   * in the path.
   */
  private static Matcher createPatternMatcher(final String fileContent)
      throws FileException {
    Pattern ptrn = Pattern.compile(FILE_PATH_PATTERN);
    return ptrn.matcher(fileContent);
  }

  /**
   * Returns the file content with the pattern replaced by the content inside
   * the file path.
   */
  private static String replacePatternByFileContent(final String fileContent,
      final Matcher matcher) throws FileException {
    String result = fileContent;
    while (matcher.find()) {
      String nestedFileContent =
          getNestedFileContent(matcher.group(PATH_TO_URL_PATTERN_GROUP_ID));
      result = result.replace(matcher.group(), nestedFileContent);
    }
    return result;
  }

  /**
   * Return the content of the file path passed with the new lines escaped
   * twice.<br>
   * Implemented to provide compatibility with the IM.
   */
  private static String getNestedFileContent(final String filePath)
      throws FileException {
    return new DoubleEscapeNewLinesFile(
        new NoNullOrEmptyFile(new Utf8File(filePath))).read();
  }

}
