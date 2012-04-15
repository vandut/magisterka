package net.vandut.agh.magisterka.proxy_creator;

import net.vandut.agh.magisterka.proxy_creator.internal.CompilerWrapper;
import net.vandut.agh.magisterka.proxy_creator.internal.CxfConfCreator;
import net.vandut.agh.magisterka.proxy_creator.internal.FileUtils;
import net.vandut.agh.magisterka.proxy_creator.internal.JarPacker;
import net.vandut.agh.magisterka.proxy_creator.internal.ManifestMfCreator;
import net.vandut.agh.magisterka.proxy_creator.internal.Wsdl2JavaWrapper;

public class ProxyCreator {

	private static final String MANIFEST_MF_NAME = "MANIFEST.MF";

	private String wsdlLocation;
	private String outputLocation;
	
	private WSDLAnalyzer analyzer;

	public WSDLAnalyzer getAnalyzer() {
		if(analyzer == null) {
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

	public String generateProxyBundle(CreationProgress creationProgress) throws Exception {
		String sourceLocation = outputLocation + "/src";
		String targetLocation = outputLocation + "/target";
		String metaInfDir = targetLocation + "/META-INF";
		String springDir = metaInfDir + "/spring";
		String manifestMfLocation = outputLocation + "/" + MANIFEST_MF_NAME;
		String beansLocation = springDir + "/beans.xml";

		Wsdl2JavaWrapper wrapper = new Wsdl2JavaWrapper();
		wrapper.setwsdlURI(wsdlLocation);
		wrapper.setOutputDir(sourceLocation);
		wrapper.generateSources();
		creationProgress.sourcesGenerated();

		CompilerWrapper compiler = new CompilerWrapper();
		compiler.setSourcePath(sourceLocation);
		compiler.setTargetPath(targetLocation);
		compiler.compileSources();
		creationProgress.sourcesCompiled();

		FileUtils.createDirIfNotExists(metaInfDir);
		FileUtils.createDirIfNotExists(springDir);

		FileUtils.copy(wsdlLocation, targetLocation);
		FileUtils.copyFromClasspath("/META-INF/DEPENDENCIES", metaInfDir);
		FileUtils.copyFromClasspath("/META-INF/LICENSE", metaInfDir);
		FileUtils.copyFromClasspath("/META-INF/NOTICE", metaInfDir);
		
		creationProgress.filesCopied();

		analyzer = new WSDLAnalyzer();
		analyzer.analyze(wsdlLocation);
		creationProgress.wsdlAnalyzed();

		CxfConfCreator.configureFile(analyzer, wsdlLocation, beansLocation);
		creationProgress.cxfConfCreated();
		ManifestMfCreator.configureFile(analyzer, manifestMfLocation);
		creationProgress.manifestCreated();

		return JarPacker.createJar(targetLocation, manifestMfLocation, outputLocation);
	}
	
	public String generateProxyBundle() throws Exception {
		return generateProxyBundle(new EmptyCreationProgress());
	}
	
	public static void run() throws Exception {
		ProxyCreator creator = new ProxyCreator();
		creator.setWsdlLocation("M:/WSDL/person.wsdl");
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
