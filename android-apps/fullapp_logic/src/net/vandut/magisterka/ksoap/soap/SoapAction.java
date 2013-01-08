package net.vandut.magisterka.ksoap.soap;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

public class SoapAction {
	
	private static final int SOAP_VERSION = SoapEnvelope.VER10;

	private final String namespace;
	private final String method;
	private final String url;

	public SoapAction(String namespace, String method, String url) {
		this.namespace = namespace;
		this.method = method;
		this.url = url;
	}

	public String getSoapAction() {
		return namespace + "/" + method;
	}

	public SoapPrimitive callMethod(Map<String, Object> parameters) throws IOException, XmlPullParserException {
		SoapObject request = new SoapObject(namespace, method);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SOAP_VERSION);
		envelope.setOutputSoapObject(request);

		for(Entry<String, Object> entry : parameters.entrySet()) {
			addParameterToSoapRequest(request, entry.getKey(), entry.getValue());
		}
		
		AndroidHttpTransport transp = new AndroidHttpTransport(url);
		transp.call(getSoapAction(), envelope);
		return (SoapPrimitive) envelope.getResponse();
	}
	
	private void addParameterToSoapRequest(SoapObject request, String name, Object parameter) {
		PropertyInfo pi = new PropertyInfo();
		pi.setName(name);
		pi.setType(parameter.getClass());
		pi.setValue(parameter);
		pi.setNamespace(namespace);
		request.addProperty(pi);
	}

}
