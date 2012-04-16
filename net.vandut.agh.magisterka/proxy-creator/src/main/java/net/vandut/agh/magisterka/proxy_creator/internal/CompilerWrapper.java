package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.File;
import java.util.ArrayList;
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
	private static final String JAR_FILE_FILTER = ".*\\.jar$";

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

	private String joinFilePaths(List<File> files, char glue) {
		StringBuffer debugString = new StringBuffer();
		for (File f : files) {
			debugString.append(glue);
			debugString.append(f.getAbsolutePath());
		}
		return debugString.toString();
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
		
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnostics, Locale.getDefault(), null);
		
		
		/*StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(diagnostics,
				Locale.getDefault(), null);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		JavaFileManager fileManager = new CustomClassloaderJavaFileManager(loader, standardJavaFileManager);
		
		logger.info("ClassLoader: "+loader);*/
		
		logger.debug("Preparing Java files");
		List<File> files = FileLister.listFiles(sourcePath, JAVA_FILE_FILTER);
		logger.debug("Selected files: " + joinFilePaths(files, ' '));
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromFiles(files);

		assertTargetDirExists();
		
		String classpath = System.getProperty("java.class.path");
		String compileClasspathRoot = System.getProperty("compile.classpath.root");
		if (compileClasspathRoot != null) {
			List<File> jarFiles = FileLister.listFiles(compileClasspathRoot, JAR_FILE_FILTER);
			logger.debug("Jar classpath files: " + joinFilePaths(jarFiles, ' '));
			classpath += joinFilePaths(jarFiles, ';');
		}

		logger.debug("Preparing compiler task");
		List<String> optionList = new ArrayList<String>();
		optionList.addAll(Arrays.asList("-classpath", classpath));
		optionList.addAll(Arrays.asList("-d", targetPath));
		logger.debug("Compiler optons: " + optionList);
		CompilationTask task = compiler.getTask(null, fileManager, diagnostics, optionList, null, compilationUnits);

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
