
package hsoa_2;

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
     * Create an instance of {@link DoorOpen }
     * 
     */
    public DoorOpen createDoorOpen() {
        return new DoorOpen();
    }

    /**
     * Create an instance of {@link DoorOpenResponse }
     * 
     */
    public DoorOpenResponse createDoorOpenResponse() {
        return new DoorOpenResponse();
    }

    /**
     * Create an instance of {@link DoorClose }
     * 
     */
    public DoorClose createDoorClose() {
        return new DoorClose();
    }

    /**
     * Create an instance of {@link DoorStatus }
     * 
     */
    public DoorStatus createDoorStatus() {
        return new DoorStatus();
    }

    /**
     * Create an instance of {@link DoorCloseResponse }
     * 
     */
    public DoorCloseResponse createDoorCloseResponse() {
        return new DoorCloseResponse();
    }

    /**
     * Create an instance of {@link DoorStatusResponse }
     * 
     */
    public DoorStatusResponse createDoorStatusResponse() {
        return new DoorStatusResponse();
    }

}
