
package net.vandut.agh.magisterka.logic.service;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.vandut.agh.magisterka.logic.service package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.vandut.agh.magisterka.logic.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LogicStartRS }
     * 
     */
    public LogicStartRS createLogicStartRS() {
        return new LogicStartRS();
    }

    /**
     * Create an instance of {@link LogicStart }
     * 
     */
    public LogicStart createLogicStart() {
        return new LogicStart();
    }

    /**
     * Create an instance of {@link LogicStatusRS }
     * 
     */
    public LogicStatusRS createLogicStatusRS() {
        return new LogicStatusRS();
    }

    /**
     * Create an instance of {@link LogicStopRS }
     * 
     */
    public LogicStopRS createLogicStopRS() {
        return new LogicStopRS();
    }

    /**
     * Create an instance of {@link LogicStop }
     * 
     */
    public LogicStop createLogicStop() {
        return new LogicStop();
    }

    /**
     * Create an instance of {@link LogicStatus }
     * 
     */
    public LogicStatus createLogicStatus() {
        return new LogicStatus();
    }

}
