package hsoa_1;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by FuseSource Services Framework 2.4.3-fuse-01-02
 * 2012-04-23T20:49:56.612+02:00
 * Generated source version: 2.4.3-fuse-01-02
 * 
 */
@WebService(targetNamespace = "HSOA_1", name = "ServiceSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface ServiceSoap {

    @WebResult(name = "door_statusResult", targetNamespace = "HSOA_1")
    @RequestWrapper(localName = "door_status", targetNamespace = "HSOA_1", className = "hsoa_1.DoorStatus")
    @WebMethod(operationName = "door_status", action = "tns:door_status")
    @ResponseWrapper(localName = "door_statusResponse", targetNamespace = "HSOA_1", className = "hsoa_1.DoorStatusResponse")
    public java.lang.String doorStatus();

    @WebResult(name = "door_openResult", targetNamespace = "HSOA_1")
    @RequestWrapper(localName = "door_open", targetNamespace = "HSOA_1", className = "hsoa_1.DoorOpen")
    @WebMethod(operationName = "door_open", action = "tns:door_open")
    @ResponseWrapper(localName = "door_openResponse", targetNamespace = "HSOA_1", className = "hsoa_1.DoorOpenResponse")
    public java.lang.String doorOpen();

    @WebResult(name = "door_closeResult", targetNamespace = "HSOA_1")
    @RequestWrapper(localName = "door_close", targetNamespace = "HSOA_1", className = "hsoa_1.DoorClose")
    @WebMethod(operationName = "door_close", action = "tns:door_close")
    @ResponseWrapper(localName = "door_closeResponse", targetNamespace = "HSOA_1", className = "hsoa_1.DoorCloseResponse")
    public java.lang.String doorClose();
}
