package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCreator {

	// http://stackoverflow.com/questions/1091788/how-to-create-a-zip-file-in-java
	public static void omtZip(String path, String outputFile) {
		final int BUFFER = 2048;
		boolean isEntry = false;
		ArrayList<String> directoryList = new ArrayList<String>();
		File f = new File(path);
		if (f.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(outputFile);
				ZipOutputStream zos = new ZipOutputStream(
						new BufferedOutputStream(fos));
				byte data[] = new byte[BUFFER];

				if (f.isDirectory()) {
					// This is Directory
					do {
						String directoryName = "";
						if (directoryList.size() > 0) {
							directoryName = directoryList.get(0);
							System.out.println("Directory Name At 0 :"
									+ directoryName);
						}
						String fullPath = path + directoryName;
						File fileList = null;
						if (directoryList.size() == 0) {
							// Main path (Root Directory)
							fileList = f;
						} else {
							// Child Directory
							fileList = new File(fullPath);
						}
						String[] filesName = fileList.list();

						int totalFiles = filesName.length;
						for (int i = 0; i < totalFiles; i++) {
							String name = filesName[i];
							File filesOrDir = new File(fullPath + name);
							if (filesOrDir.isDirectory()) {
								System.out.println("New Directory Entry :"
										+ directoryName + name + "/");
								ZipEntry entry = new ZipEntry(directoryName
										+ name + "/");
								zos.putNextEntry(entry);
								isEntry = true;
								directoryList.add(directoryName + name + "/");
							} else {
								System.out.println("New File Entry :"
										+ directoryName + name);
								ZipEntry entry = new ZipEntry(directoryName
										+ name);
								zos.putNextEntry(entry);
								isEntry = true;
								FileInputStream fileInputStream = new FileInputStream(
										filesOrDir);
								BufferedInputStream bufferedInputStream = new BufferedInputStream(
										fileInputStream, BUFFER);
								int size = -1;
								while ((size = bufferedInputStream.read(data,
										0, BUFFER)) != -1) {
									zos.write(data, 0, size);
								}
								bufferedInputStream.close();
							}
						}
						if (directoryList.size() > 0
								&& directoryName.trim().length() > 0) {
							System.out.println("Directory removed :"
									+ directoryName);
							directoryList.remove(0);
						}

					} while (directoryList.size() > 0);
				} else {
					// This is File
					// Zip this file
					System.out.println("Zip this file :" + f.getPath());
					FileInputStream fis = new FileInputStream(f);
					BufferedInputStream bis = new BufferedInputStream(fis,
							BUFFER);
					ZipEntry entry = new ZipEntry(f.getName());
					zos.putNextEntry(entry);
					isEntry = true;
					int size = -1;
					while ((size = bis.read(data, 0, BUFFER)) != -1) {
						zos.write(data, 0, size);
					}
				}

				// CHECK IS THERE ANY ENTRY IN ZIP ? ----START
				if (isEntry) {
					zos.close();
				} else {
					zos = null;
					System.out.println("No Entry Found in Zip");
				}
				// CHECK IS THERE ANY ENTRY IN ZIP ? ----START
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File or Directory not found");
		}
	}

}
