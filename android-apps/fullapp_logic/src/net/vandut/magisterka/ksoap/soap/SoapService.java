package net.vandut.magisterka.ksoap.soap;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

public class SoapService {
	
	private static final int SOAP_VERSION = SoapEnvelope.VER10;

	private final String namespace;
	private final String url;

	public SoapService(String namespace, String url) {
		this.namespace = namespace;
		this.url = url;
	}
	
	public SoapMethod getSoapMethod(String method) {
		return new SoapMethod(method);
	}
	
	public class SoapMethod {
		
		private final String method;
		
		private SoapObject request;
		private SoapSerializationEnvelope envelope;

		public SoapMethod(String method) {
			this.method = method;
			prepareEnvelope();
		}
		
		private void prepareEnvelope() {
			request = new SoapObject(namespace, method);
			envelope = new SoapSerializationEnvelope(SOAP_VERSION);
			envelope.setOutputSoapObject(request);
		}
		
		public void addParameter(String name, Object parameter) {
			PropertyInfo pi = new PropertyInfo();
			pi.setName(name);
			pi.setType(parameter.getClass());
			pi.setValue(parameter);
			pi.setNamespace(namespace);
			request.addProperty(pi);
		}

		public String getSoapAction() {
			return namespace + "/" + method;
		}

		public SoapPrimitive call()
				throws IOException, XmlPullParserException {
			AndroidHttpTransport transp = new AndroidHttpTransport(url);
			transp.call(getSoapAction(), envelope);
			return (SoapPrimitive) envelope.getResponse();
		}
		
		public String getStringResponse() throws SoapFault {
			return ((SoapPrimitive) envelope.getResponse()).toString();
		}
		
	}

}
