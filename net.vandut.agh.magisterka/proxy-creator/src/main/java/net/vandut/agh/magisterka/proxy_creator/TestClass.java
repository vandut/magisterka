package net.vandut.agh.magisterka.proxy_creator;

import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.wsdlto.core.DataBindingProfile;
import org.apache.cxf.tools.wsdlto.core.PluginLoader;
import org.apache.cxf.tools.wsdlto.databinding.jaxb.JAXBBindErrorListener;
import org.apache.cxf.tools.wsdlto.databinding.jaxb.JAXBDataBinding;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLFilterImpl;

import com.sun.tools.xjc.reader.internalizer.DOMForest;
import com.sun.tools.xjc.reader.internalizer.InternalizationLogic;
import com.sun.tools.xjc.util.NullStream;

public class TestClass {

	private static final Logger logger = Logger.getLogger(TestClass.class);

	public void performTest(String name) {
		
		testClassess();
		
		logger.info("Loading DataBinding " + name + " ...");
		PluginLoader pluginLoader = PluginLoader.newInstance();
		DataBindingProfile x = pluginLoader.getDataBindingProfile(name);
		logger.info("DataBinding " + name + " loaded: x=" + x);
	}
	
	@SuppressWarnings("unused")
	private void testClassess() {
		logger.info("Testing class (namespaces) existance");

		logger.info("Testing class: com.sun.xml.bind.api.ErrorListener");
		com.sun.xml.bind.api.ErrorListener el1 = new com.sun.xml.bind.api.ErrorListener() {
			public void warning(SAXParseException exception) {
			}
			public void info(SAXParseException exception) {
			}
			public void fatalError(SAXParseException exception) {
			}
			public void error(SAXParseException exception) {
			}
		};

		// FAIL!! :<
		logger.info("Testing class: com.sun.tools.xjc.api.ErrorListener");
		com.sun.tools.xjc.api.ErrorListener el2 = new com.sun.tools.xjc.api.ErrorListener() {
			public void warning(SAXParseException exception) {
			}
			public void info(SAXParseException exception) {
			}
			public void fatalError(SAXParseException exception) {
			}
			public void error(SAXParseException exception) {
			}
		};
		

		logger.info("Testing class: InternalizationLogic");
		InternalizationLogic internalizationLogic = new InternalizationLogic() {
			@Override
			public Element refineTarget(Element target) {
				return null;
			}
			@Override
			public XMLFilterImpl createExternalReferenceFinder(DOMForest parent) {
				return null;
			}
			@Override
			public boolean checkIfValidTargetNode(DOMForest parent, Element bindings, Element target) {
				return false;
			}
		};

		logger.info("Testing class: org.apache.cxf.tools.common.ToolException");
		ToolException te = new ToolException("dsd");
		
		logger.info("Testing class: JAXBBindErrorListener");
		JAXBBindErrorListener listener = new JAXBBindErrorListener (true);
		logger.info("Testing class: JAXBDataBinding");
		JAXBDataBinding s = new JAXBDataBinding();

		logger.info("Testing class: NullStream");
		NullStream ns = new NullStream();
		
		logger.info("Testing class result: success");
	}
	
	public static void main(String[] args) {
		TestClass tc = new TestClass();
		tc.testClassess();
	}

}
