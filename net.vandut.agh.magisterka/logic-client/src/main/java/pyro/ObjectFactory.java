
package pyro;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pyro package. 
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

    private final static QName _GetEndpointURIResponseReturn_QNAME = new QName("", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pyro
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStatus }
     * 
     */
    public GetStatus createGetStatus() {
        return new GetStatus();
    }

    /**
     * Create an instance of {@link GetStatusResponse }
     * 
     */
    public GetStatusResponse createGetStatusResponse() {
        return new GetStatusResponse();
    }

    /**
     * Create an instance of {@link GetDescription }
     * 
     */
    public GetDescription createGetDescription() {
        return new GetDescription();
    }

    /**
     * Create an instance of {@link GetUuid }
     * 
     */
    public GetUuid createGetUuid() {
        return new GetUuid();
    }

    /**
     * Create an instance of {@link GetTemp }
     * 
     */
    public GetTemp createGetTemp() {
        return new GetTemp();
    }

    /**
     * Create an instance of {@link GetCommonNameResponse }
     * 
     */
    public GetCommonNameResponse createGetCommonNameResponse() {
        return new GetCommonNameResponse();
    }

    /**
     * Create an instance of {@link GetEndpointURI }
     * 
     */
    public GetEndpointURI createGetEndpointURI() {
        return new GetEndpointURI();
    }

    /**
     * Create an instance of {@link GetUuidResponse }
     * 
     */
    public GetUuidResponse createGetUuidResponse() {
        return new GetUuidResponse();
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link GetSpecificationURIResponse }
     * 
     */
    public GetSpecificationURIResponse createGetSpecificationURIResponse() {
        return new GetSpecificationURIResponse();
    }

    /**
     * Create an instance of {@link IsActive }
     * 
     */
    public IsActive createIsActive() {
        return new IsActive();
    }

    /**
     * Create an instance of {@link GetSpecificationURI }
     * 
     */
    public GetSpecificationURI createGetSpecificationURI() {
        return new GetSpecificationURI();
    }

    /**
     * Create an instance of {@link GetVersion }
     * 
     */
    public GetVersion createGetVersion() {
        return new GetVersion();
    }

    /**
     * Create an instance of {@link GetConfigurationAssistURI }
     * 
     */
    public GetConfigurationAssistURI createGetConfigurationAssistURI() {
        return new GetConfigurationAssistURI();
    }

    /**
     * Create an instance of {@link IsActiveResponse }
     * 
     */
    public IsActiveResponse createIsActiveResponse() {
        return new IsActiveResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationAssistURIResponse }
     * 
     */
    public GetConfigurationAssistURIResponse createGetConfigurationAssistURIResponse() {
        return new GetConfigurationAssistURIResponse();
    }

    /**
     * Create an instance of {@link GetCommonName }
     * 
     */
    public GetCommonName createGetCommonName() {
        return new GetCommonName();
    }

    /**
     * Create an instance of {@link GetDescriptionResponse }
     * 
     */
    public GetDescriptionResponse createGetDescriptionResponse() {
        return new GetDescriptionResponse();
    }

    /**
     * Create an instance of {@link GetEndpointURIResponse }
     * 
     */
    public GetEndpointURIResponse createGetEndpointURIResponse() {
        return new GetEndpointURIResponse();
    }

    /**
     * Create an instance of {@link GetTempResponse }
     * 
     */
    public GetTempResponse createGetTempResponse() {
        return new GetTempResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetEndpointURIResponse.class)
    public JAXBElement<String> createGetEndpointURIResponseReturn(String value) {
        return new JAXBElement<String>(_GetEndpointURIResponseReturn_QNAME, String.class, GetEndpointURIResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetStatusResponse.class)
    public JAXBElement<String> createGetStatusResponseReturn(String value) {
        return new JAXBElement<String>(_GetEndpointURIResponseReturn_QNAME, String.class, GetStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetDescriptionResponse.class)
    public JAXBElement<String> createGetDescriptionResponseReturn(String value) {
        return new JAXBElement<String>(_GetEndpointURIResponseReturn_QNAME, String.class, GetDescriptionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetSpecificationURIResponse.class)
    public JAXBElement<String> createGetSpecificationURIResponseReturn(String value) {
        return new JAXBElement<String>(_GetEndpointURIResponseReturn_QNAME, String.class, GetSpecificationURIResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetConfigurationAssistURIResponse.class)
    public JAXBElement<String> createGetConfigurationAssistURIResponseReturn(String value) {
        return new JAXBElement<String>(_GetEndpointURIResponseReturn_QNAME, String.class, GetConfigurationAssistURIResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetVersionResponse.class)
    public JAXBElement<String> createGetVersionResponseReturn(String value) {
        return new JAXBElement<String>(_GetEndpointURIResponseReturn_QNAME, String.class, GetVersionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetCommonNameResponse.class)
    public JAXBElement<String> createGetCommonNameResponseReturn(String value) {
        return new JAXBElement<String>(_GetEndpointURIResponseReturn_QNAME, String.class, GetCommonNameResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetUuidResponse.class)
    public JAXBElement<String> createGetUuidResponseReturn(String value) {
        return new JAXBElement<String>(_GetEndpointURIResponseReturn_QNAME, String.class, GetUuidResponse.class, value);
    }

}
