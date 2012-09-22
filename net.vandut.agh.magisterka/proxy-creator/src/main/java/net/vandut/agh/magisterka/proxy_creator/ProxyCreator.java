package net.vandut.agh.magisterka.proxy_creator;

import java.io.File;

import net.vandut.agh.magisterka.proxy_creator.internal.ActivatorCreator;
import net.vandut.agh.magisterka.proxy_creator.internal.ClientCodeCreator;
import net.vandut.agh.magisterka.proxy_creator.internal.CompilerWrapper;
import net.vandut.agh.magisterka.proxy_creator.internal.CxfConfCreator;
import net.vandut.agh.magisterka.proxy_creator.internal.FileUtils;
import net.vandut.agh.magisterka.proxy_creator.internal.JarPacker;
import net.vandut.agh.magisterka.proxy_creator.internal.ManifestMfCreator;
import net.vandut.agh.magisterka.proxy_creator.internal.Wsdl2JavaWrapper;
import net.vandut.agh.magisterka.proxy_creator.internal.ZipCreator;

import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;

public class ProxyCreator implements IDistributionConstants {

	private static final String MANIFEST_MF_NAME = "MANIFEST.MF";

	private static final String SRC_DIR = "/src";
	private static final String TARGET_DIR = "/target";
	private static final String METAINF_DIR = "/META-INF";
	private static final String CLIENT_FILE = "/client.zip";

	private String wsdlLocation;
	private String outputLocation;
	private String portToUse;

	private WSDLAnalyzer analyzer;

	public WSDLAnalyzer getAnalyzer() {
		if (analyzer == null) {
			throw new IllegalStateException("Analyzer not ready");
		}
		return analyzer;
	}

	public void setWsdlLocation(String wsdlLocation) {
		this.wsdlLocation = wsdlLocation;
	}

	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}
	
	public String getSourceLocation() {
		return outputLocation + SRC_DIR;
	}
	
	public String getTargetLocation() {
		return outputLocation + TARGET_DIR;
	}
	
	public String getClientZipFile() {
		return outputLocation + CLIENT_FILE;
	}
	
	public void setPortToUse(String portToUse) {
		this.portToUse = portToUse;
	}

	public String generateProxyBundle(CreationProgress creationProgress) throws Exception {
		String sourceLocation = getSourceLocation();
		String targetLocation = getTargetLocation();
		String metaInfDir = targetLocation + METAINF_DIR;
		String springDir = metaInfDir + "/spring";
		String manifestMfLocation = outputLocation + "/" + MANIFEST_MF_NAME;
		String beansLocation = springDir + "/beans.xml";

		analyzer = new WSDLAnalyzer();
		analyzer.analyze(wsdlLocation);
		creationProgress.wsdlAnalyzed();

		Wsdl2JavaWrapper wrapper = new Wsdl2JavaWrapper();
		wrapper.setwsdlURI(wsdlLocation);
		wrapper.setOutputDir(sourceLocation);
		wrapper.generateSources();
		creationProgress.sourcesGenerated();

		FileUtils.createDirIfNotExists(sourceLocation + "/javax/xml/ws");
		FileUtils.copyFromClasspath("/java_files/Holder.java", sourceLocation + "/javax/xml/ws");
		FileUtils.copyFromClasspath("/java_files/WebServiceException.java", sourceLocation + "/javax/xml/ws");
		ActivatorCreator.createActivator(analyzer, "/java_files/Activator.java", sourceLocation);

		CompilerWrapper compiler = new CompilerWrapper();
		compiler.setSourcePath(sourceLocation);
		compiler.setTargetPath(targetLocation);
		if(!compiler.compileSources()) {
			throw new Exception("Compilation error");
		}
		creationProgress.sourcesCompiled();

		FileUtils.createDirIfNotExists(metaInfDir);
		FileUtils.createDirIfNotExists(springDir);

		FileUtils.copy(wsdlLocation, targetLocation);
		FileUtils.copyFromClasspath("/META-INF/DEPENDENCIES", metaInfDir);
		FileUtils.copyFromClasspath("/META-INF/LICENSE", metaInfDir);
		FileUtils.copyFromClasspath("/META-INF/NOTICE", metaInfDir);

		creationProgress.filesCopied();

		CxfConfCreator.configureFile(analyzer, wsdlLocation, beansLocation, portToUse);
		creationProgress.cxfConfCreated();
		ManifestMfCreator.configureFile(analyzer, manifestMfLocation);
		creationProgress.manifestCreated();
		
		String clientLocation = outputLocation + "/client";
		String clientSrcLocation = outputLocation + "/client/src/main/java";
		File clientFile = new File(clientLocation);
		File clientSrcFile = new File(clientSrcLocation);
		clientFile.mkdirs();
		clientSrcFile.mkdirs();

		FileUtils.copy(wsdlLocation, clientLocation);
		org.apache.commons.io.FileUtils.copyDirectory(new File(sourceLocation), clientSrcFile);
		String internalSrcLocation = ActivatorCreator.getPackageInternalPath(analyzer, clientSrcLocation);
		File internalSrcFile = new File(internalSrcLocation);
		org.apache.commons.io.FileUtils.deleteDirectory(internalSrcFile);
		internalSrcFile.mkdirs();
		
		ClientCodeCreator.createClientCode(analyzer, clientLocation, internalSrcLocation);
		
		String jarBundleLocation = JarPacker.createJar(targetLocation, manifestMfLocation, outputLocation);
		FileUtils.copy(jarBundleLocation, clientLocation);
		
		ZipCreator.omtZip(clientLocation + "/", getClientZipFile());

		return jarBundleLocation;
	}

	public String generateProxyBundle() throws Exception {
		return generateProxyBundle(new EmptyCreationProgress());
	}

	public static void run() throws Exception {
		ProxyCreator creator = new ProxyCreator();
		creator.setWsdlLocation("M:/WSDL/DoorService_final.wsdl");
		creator.setOutputLocation("M:/test");
		creator.generateProxyBundle();
	}

	public static void main(String[] args) throws Exception {
		run();
	}

	private static class EmptyCreationProgress implements CreationProgress {

		@Override
		public void sourcesGenerated() {
		}

		@Override
		public void sourcesCompiled() {
		}

		@Override
		public void filesCopied() {
		}

		@Override
		public void wsdlAnalyzed() {
		}

		@Override
		public void cxfConfCreated() {
		}

		@Override
		public void manifestCreated() {
		}

	}

}
