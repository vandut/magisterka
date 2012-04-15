package net.vandut.agh.magisterka.proxy_creator.internal;

import java.util.Arrays;

import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.wsdlto.WSDLToJava;
import org.apache.log4j.Logger;

public class Wsdl2JavaWrapper {

	private static final Logger logger = Logger
			.getLogger(Wsdl2JavaWrapper.class);

	protected String wsdlURI;

	protected String outputDir;

	public void setwsdlURI(String wsdlURI) {
		logger.debug("WSDL url set: " + wsdlURI);
		this.wsdlURI = wsdlURI;
	}

	public void setOutputDir(String outputDir) {
		logger.debug("Output dir set: " + outputDir);
		this.outputDir = outputDir;
	}

	protected String[] buildParserArguments() {
		return new String[] { "-d", outputDir, "-frontend", "jaxws21", "-databinding", "jaxb", "-quiet", wsdlURI };
	}

	public void generateSources() throws ToolException, Exception {
		String[] pargs = buildParserArguments();
		logger.debug("WSDLToJava arguments: " + Arrays.toString(pargs));

		WSDLToJava w2j = new WSDLToJava(pargs);

		try {
			logger.info("Running WSDLToJava");
			w2j.run(new ToolContext());
			logger.info("Sources generated using WSDLToJava");
		} catch (ToolException e) {
			logger.error("WSDLToJava Error", e);
			throw e;
		} catch (Exception e) {
			logger.error("WSDLToJava Error", e);
			throw e;
		}
	}

}
