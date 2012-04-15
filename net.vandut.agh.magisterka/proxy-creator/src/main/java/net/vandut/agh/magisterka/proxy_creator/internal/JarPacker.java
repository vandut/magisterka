package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;

public class JarPacker {

	private static final Logger logger = Logger.getLogger(JarPacker.class);

	public static String createJar(String targetPath, String manifestPath,
			String outputPath) throws IOException {
		Manifest manifest = new Manifest(new FileInputStream(manifestPath));
		Attributes attributes = manifest.getMainAttributes();
		String jarFilePath = outputPath + "/"
				+ attributes.getValue("Bundle-SymbolicName");
		jarFilePath += "-" + attributes.getValue("Bundle-Version");
		jarFilePath += ".jar";

		JarOutputStream target = new JarOutputStream(new FileOutputStream(
				jarFilePath), manifest);

		List<File> targetFiles = FileLister.listFiles(targetPath, null);
		File baseDir = new File(targetPath);
		for (File f : targetFiles) {
			add(baseDir, f, target);
		}
		
		target.close();
		
		logger.info("Saving generated bundle to " + jarFilePath);
		return jarFilePath;
	}

	private static void add(File baseDir, File source, JarOutputStream target)
			throws IOException {
		BufferedInputStream in = null;
		int stripCharLength = baseDir.getAbsolutePath().length()+1;
		try {
			if (source.isDirectory()) {
				String name = source.getPath().replace("\\", "/");
				if (!name.isEmpty()) {
					if (!name.endsWith("/"))
						name += "/";
					JarEntry entry = new JarEntry(name.substring(stripCharLength));
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (File nestedFile : source.listFiles())
					add(baseDir, nestedFile, target);
				return;
			}

			JarEntry entry = new JarEntry(source.getPath().replace("\\", "/").substring(stripCharLength));
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			byte[] buffer = new byte[1024];
			while (true) {
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		} finally {
			if (in != null)
				in.close();
		}
	}

}
