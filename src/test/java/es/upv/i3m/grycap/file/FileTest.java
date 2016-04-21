package es.upv.i3m.grycap.file;

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.im.exceptions.FileException;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class FileTest extends ImTestWatcher {

  private static final String AUTH_FILE_PATH_NO_INTERNAL_PATH =
      "./src/test/resources/files/auth_no_internal_path.dat";
  private static final String AUTH_FILE_PATH_ONE_INTERNAL_PATH =
      "./src/test/resources/files/auth_one_internal_path.dat";
  private static final String AUTH_FILE_PATH_TWO_INTERNAL_PATH =
      "./src/test/resources/files/auth_two_internal_path.dat";
  private static final String AUTH_FILE_PATH_SEVERAL_DIFFERENT_INTERNAL_PATH =
      "./src/test/resources/files/auth_several_different_internal_path.dat";
  private static final String AUTH_FILE_PATH_NO_INTERNAL_PATH_RESULT_FILE =
      "./src/test/resources/files/aut_no_internal_path_expected_result";
  private static final String AUTH_FILE_PATH_ONE_INTERNAL_PATH_RESULT_FILE =
      "./src/test/resources/files/aut_one_internal_path_expected_result";
  private static final String AUTH_FILE_PATH_SEVERAL_DIFFERENT_INTERNAL_PATH_RESULT_FILE =
      "./src/test/resources/files/aut_several_different_internal_path_expected_result";

  @Test
  public void testNoInternalPaths() throws FileException {
    String fileContent = new FileWithInternalPath(
        new EscapeNewLinesFile(getFile(AUTH_FILE_PATH_NO_INTERNAL_PATH)))
            .read();
    String expectedContent =
        readFile(AUTH_FILE_PATH_NO_INTERNAL_PATH_RESULT_FILE);
    Assert.assertArrayEquals(expectedContent.toCharArray(),
        fileContent.toCharArray());
  }

  @Test
  public void testInternalPath() throws FileException {
    String fileContent = new FileWithInternalPath(
        new EscapeNewLinesFile(getFile(AUTH_FILE_PATH_ONE_INTERNAL_PATH)))
            .read();
    String expectedContent =
        readFile(AUTH_FILE_PATH_ONE_INTERNAL_PATH_RESULT_FILE);
    Assert.assertArrayEquals(expectedContent.toCharArray(),
        fileContent.toCharArray());
  }

  /**
   * Expected exception because one of the internal path redirects to an empty
   * file.
   */
  @Test(expected = FileException.class)
  public void testEmptyFileInInternalPath() throws FileException {
    new FileWithInternalPath(
        new EscapeNewLinesFile(getFile(AUTH_FILE_PATH_TWO_INTERNAL_PATH)))
            .read();
  }

  @Test
  public void testSeveralDifferentInternalPaths() throws FileException {
    String fileContent = new FileWithInternalPath(new EscapeNewLinesFile(
        getFile(AUTH_FILE_PATH_SEVERAL_DIFFERENT_INTERNAL_PATH))).read();
    String expectedContent =
        readFile(AUTH_FILE_PATH_SEVERAL_DIFFERENT_INTERNAL_PATH_RESULT_FILE);
    Assert.assertArrayEquals(expectedContent.toCharArray(),
        fileContent.toCharArray());
  }

  @Test
  public void testDifferentDecoratorOrder() throws FileException {
    String fileContent = new EscapeNewLinesFile(new FileWithInternalPath(
        getFile(AUTH_FILE_PATH_SEVERAL_DIFFERENT_INTERNAL_PATH))).read();
    String expectedContent =
        readFile(AUTH_FILE_PATH_SEVERAL_DIFFERENT_INTERNAL_PATH_RESULT_FILE);
    Assert.assertArrayEquals(expectedContent.toCharArray(),
        fileContent.toCharArray());
  }

  private File getFile(String filePath) throws FileException {
    return new NoNullOrEmptyFile(new Utf8File(Paths.get(filePath)));
  }

  private String readFile(String filePath) throws FileException {
    return new NoNullOrEmptyFile(new Utf8File(Paths.get(filePath))).read();
  }
}
