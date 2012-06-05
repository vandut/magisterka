
package hsoa_1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hsoa_1 package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hsoa_1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTemperatureResponse }
     * 
     */
    public GetTemperatureResponse createGetTemperatureResponse() {
        return new GetTemperatureResponse();
    }

    /**
     * Create an instance of {@link GetHumidityResponse }
     * 
     */
    public GetHumidityResponse createGetHumidityResponse() {
        return new GetHumidityResponse();
    }

    /**
     * Create an instance of {@link GetTemperature }
     * 
     */
    public GetTemperature createGetTemperature() {
        return new GetTemperature();
    }

    /**
     * Create an instance of {@link GetPressureResponse }
     * 
     */
    public GetPressureResponse createGetPressureResponse() {
        return new GetPressureResponse();
    }

    /**
     * Create an instance of {@link GetHumidity }
     * 
     */
    public GetHumidity createGetHumidity() {
        return new GetHumidity();
    }

    /**
     * Create an instance of {@link GetPressure }
     * 
     */
    public GetPressure createGetPressure() {
        return new GetPressure();
    }

}
