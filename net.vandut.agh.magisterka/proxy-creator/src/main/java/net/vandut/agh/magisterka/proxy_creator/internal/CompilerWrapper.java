package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;

public class CompilerWrapper {

	private static final Logger logger = Logger
			.getLogger(CompilerWrapper.class);

	private static final String JAVA_FILE_FILTER = ".*\\.java$";

	private String sourcePath;

	private String targetPath;

	public void setSourcePath(String sourcePath) {
		logger.debug("Source dir set: " + sourcePath);
		this.sourcePath = sourcePath;
	}

	public void setTargetPath(String targetPath) {
		logger.debug("Target dir set: " + targetPath);
		this.targetPath = targetPath;
	}

	private String getListFilesDebugString(List<File> files) {
		String debugString = "";
		for (File f : files) {
			debugString += " " + f.getAbsolutePath();
		}
		return debugString;
	}

	private boolean assertTargetDirExists() {
		File targetDirFile = new File(targetPath);
		if (!targetDirFile.exists()) {
			logger.warn("Target directory does not exist, creating");
			if (!targetDirFile.mkdirs()) {
				logger.error("Target directory creation failed");
				return false;
			}
		}
		if (!targetDirFile.isDirectory()) {
			logger.error("Target path is not a directory");
			return false;
		}
		return true;
	}

	public boolean compileSources() throws Exception {
		logger.debug("Preparing compiler tools");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		if (compiler == null) {
			logger.error("Compiler not found! This application needs JDK, not JRE,"
					+ " to compile Java sources. Check 'java.home' property: "
					+ System.getProperty("java.home"));
			return false;
		}

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, Locale.getDefault(), null);
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		logger.debug("Preparing Java files");
		List<File> files = FileLister.listFiles(sourcePath, JAVA_FILE_FILTER);
		logger.debug("Selected files: " + getListFilesDebugString(files));
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromFiles(files);

		assertTargetDirExists();

		logger.debug("Preparing compiler task");
		String[] compileOptions = new String[] { "-d", targetPath };
		CompilationTask task = compiler.getTask(null, fileManager, diagnostics,
				Arrays.asList(compileOptions), null, compilationUnits);

		logger.info("Compiling sources");
		boolean compilationResult = task.call();

		if (compilationResult) {
			logger.info("Sources compiled successfully");
		} else {
			logger.error("Failed to compile sources");
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
					.getDiagnostics()) {
				throw new Exception("Error on line " + diagnostic.getLineNumber()
						+ " in " + diagnostic);
//				logger.error("Error on line " + diagnostic.getLineNumber()
//						+ " in " + diagnostic);
			}
		}

		fileManager.close();

		return compilationResult;
	}

}
