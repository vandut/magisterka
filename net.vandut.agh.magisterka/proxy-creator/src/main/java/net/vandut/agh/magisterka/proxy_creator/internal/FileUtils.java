package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileUtils {

	public static void createDirIfNotExists(String path) throws IOException {
		File dir = new File(path);
		if (dir.exists()) {
			if (dir.isFile()) {
				throw new IOException("Could not create directory, path exists and is a file");
			}
		} else {
			if (!dir.mkdirs()) {
				throw new IOException("Failed to create directory: " + path);
			}
		}
	}
	
	public static String stringFromClasspathFile(String path) throws IOException {
		InputStream stream = FileUtils.getInputStreamFromClasspath(path);
		return FileUtils.readFile(stream);
	}

	// public static File getFileFromClasspath(String path) throws IOException {
	// URL url = FileUtils.class.getResource(path);
	// Thread.currentThread().getContextClassLoader().getResources(path);
	// if (url == null) {
	// throw new IOException("File " + path + " not found in classpath");
	// }
	// return new File(url.getFile());
	// }

	public static InputStream getInputStreamFromClasspath(String path) throws IOException {
		InputStream is = FileUtils.class.getResourceAsStream(path);
		if (is == null) {
			throw new IOException("File " + path + " not found in classpath");
		}
		return is;
	}

	public static void copy(String fromFileName, String toFileName) throws IOException {
		File fromFile = new File(fromFileName);

		if (!fromFile.exists())
			throw new IOException("FileCopy: no such source file: " + fromFileName);
		if (!fromFile.isFile())
			throw new IOException("FileCopy: can't copy directory: " + fromFileName);
		if (!fromFile.canRead())
			throw new IOException("FileCopy: source file is unreadable: " + fromFileName);

		copy(new FileInputStream(fromFile), toFileName, fromFile.getName());
	}

	public static void copyFromClasspath(String path, String toFileName) throws IOException {
		InputStream inputStream = getInputStreamFromClasspath(path);
		File file = new File(FileUtils.class.getResource(path).getFile());
		copy(inputStream, toFileName, file.getName());
	}

	public static void copy(InputStream inputStream, String toFileName, String fromFileName) throws IOException {
		File toFile = new File(toFileName);

		if (toFile.isDirectory())
			toFile = new File(toFile, fromFileName);

		if (toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("FileCopy: " + "destination file is unwriteable: " + toFileName);
		} else {
			String parent = toFile.getParent();
			if (parent == null)
				parent = System.getProperty("user.dir");
			File dir = new File(parent);
			if (!dir.exists())
				throw new IOException("FileCopy: " + "destination directory doesn't exist: " + parent);
			if (dir.isFile())
				throw new IOException("FileCopy: " + "destination is not a directory: " + parent);
			if (!dir.canWrite())
				throw new IOException("FileCopy: " + "destination directory is unwriteable: " + parent);
		}

		InputStream from = inputStream;
		OutputStream to = null;
		try {
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead);
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					;
				}
		}
	}

	public static String readFile(File file) throws IOException {
		return readFile(new FileInputStream(file));
	}

	public static String readFile(InputStream stream) throws IOException {
		try {
			return getContents(stream);
		} finally {
			stream.close();
		}
	}

	// http://www.javapractices.com/topic/TopicAction.do?Id=42
	static private String getContents(InputStream stream) {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new InputStreamReader(stream));
			String NL = System.getProperty("line.separator");
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(NL);
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}

	public static void writeFile(String filePath, String text) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(filePath);
		out.println(text);
		out.close();
	}

}
