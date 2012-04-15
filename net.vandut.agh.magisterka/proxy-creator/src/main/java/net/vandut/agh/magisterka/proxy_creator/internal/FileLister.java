package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileLister {

	private static final Logger logger = Logger.getLogger(FileLister.class);

	private final String filter;

	private List<File> files = new ArrayList<File>();

	public FileLister() {
		this.filter = null;
	}
	
	public FileLister(String filter) {
		this.filter = filter;
	}

	public void scan(String directory) {
		scan(new File(directory));
	}

	public void scan(File file) {
		if (file.isFile()) {
			addFileToList(file);
		} else if (file.isDirectory()) {
			File[] listOfFiles = file.listFiles();
			if (listOfFiles != null) {
				for (File f : listOfFiles) {
					scan(f);
				}
			}
		} else {
			logger.warn(file.getAbsolutePath() + " - Access Denied");
		}
	}

	public void addFileToList(File file) {
		if (filter != null) {
			if (file.getName().matches(filter)) {
				files.add(file);
			} else {
				logger.warn("Filter [" + filter + "] not matched: "
						+ file.getAbsolutePath());
			}
		} else {
			files.add(file);
		}
	}

	public List<File> getFiles() {
		return files;
	}

	public static List<File> listFiles(String directory, String filter) {
		FileLister fileLister = new FileLister(filter);
		fileLister.scan(directory);
		return fileLister.getFiles();
	}

}
