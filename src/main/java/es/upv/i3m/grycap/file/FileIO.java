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
