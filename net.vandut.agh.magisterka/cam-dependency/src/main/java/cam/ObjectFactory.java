
package cam;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cam package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _StartClassifierResponseStartClassifierReturn_QNAME = new QName("", "StartClassifierReturn");
    private final static QName _GetLastResponseGetLastReturn_QNAME = new QName("", "GetLastReturn");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cam
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLastResponse }
     * 
     */
    public GetLastResponse createGetLastResponse() {
        return new GetLastResponse();
    }

    /**
     * Create an instance of {@link StartClassifierResponse }
     * 
     */
    public StartClassifierResponse createStartClassifierResponse() {
        return new StartClassifierResponse();
    }

    /**
     * Create an instance of {@link StartClassifier }
     * 
     */
    public StartClassifier createStartClassifier() {
        return new StartClassifier();
    }

    /**
     * Create an instance of {@link GetLast }
     * 
     */
    public GetLast createGetLast() {
        return new GetLast();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StartClassifierReturn", scope = StartClassifierResponse.class)
    public JAXBElement<String> createStartClassifierResponseStartClassifierReturn(String value) {
        return new JAXBElement<String>(_StartClassifierResponseStartClassifierReturn_QNAME, String.class, StartClassifierResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GetLastReturn", scope = GetLastResponse.class)
    public JAXBElement<String> createGetLastResponseGetLastReturn(String value) {
        return new JAXBElement<String>(_GetLastResponseGetLastReturn_QNAME, String.class, GetLastResponse.class, value);
    }

}
