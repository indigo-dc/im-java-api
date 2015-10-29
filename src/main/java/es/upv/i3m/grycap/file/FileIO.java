package es.upv.i3m.grycap.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Helper class for reading files
 */
public class FileIO {

	/**
	 * Read the file specified by the path parameter using the standard charset
	 * UTF8
	 * 
	 * @param path
	 *            : file to read
	 * @return
	 * @throws IOException
	 */
	public static String readUTF8File(String path) throws IOException {
		return readFile(path, StandardCharsets.UTF_8);
	}

	/**
	 * Read the file specified by the path with the encoding specified by the
	 * encoding parameter
	 * 
	 * @param path
	 *            : file to read
	 * @param encoding
	 *            : charset of the file to read
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	/**
	 * Read the file specified by the path parameter using the standard charset
	 * UTF8 and replace all the '\n' with '\\n'
	 * 
	 * @param path
	 *            : file to read
	 * @return
	 * @throws IOException
	 */
	public static String readUTF8FileAndReplaceNewLines(String path) throws IOException {
		return readFile(path, StandardCharsets.UTF_8).replace("\n", "\\n");
	}

}
