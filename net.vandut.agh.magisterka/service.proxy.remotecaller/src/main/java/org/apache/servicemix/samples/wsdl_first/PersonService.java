package org.apache.servicemix.samples.wsdl_first;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by FuseSource Services Framework 2.4.3-fuse-00-13
 * 2012-02-27T21:23:29.075+01:00
 * Generated source version: 2.4.3-fuse-00-13
 * 
 */
@WebServiceClient(name = "PersonService", 
                  wsdlLocation = "file:/M:/project/net.vandut.agh.magisterka/service-proxy/src/main/resources/person.wsdl",
                  targetNamespace = "http://servicemix.apache.org/samples/wsdl-first") 
public class PersonService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://servicemix.apache.org/samples/wsdl-first", "PersonService");
    public final static QName Soap = new QName("http://servicemix.apache.org/samples/wsdl-first", "soap");
    static {
        URL url = null;
        try {
            url = new URL("file:/M:/project/net.vandut.agh.magisterka/service-proxy/src/main/resources/person.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(PersonService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/M:/project/net.vandut.agh.magisterka/service-proxy/src/main/resources/person.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public PersonService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PersonService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PersonService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns Person
     */
    @WebEndpoint(name = "soap")
    public Person getSoap() {
        return super.getPort(Soap, Person.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Person
     */
    @WebEndpoint(name = "soap")
    public Person getSoap(WebServiceFeature... features) {
        return super.getPort(Soap, Person.class, features);
    }

}
