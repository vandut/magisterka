
package hsoa_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="door_statusResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "doorStatusResult"
})
@XmlRootElement(name = "door_statusResponse")
public class DoorStatusResponse {

    @XmlElement(name = "door_statusResult")
    protected String doorStatusResult;

    /**
     * Gets the value of the doorStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoorStatusResult() {
        return doorStatusResult;
    }

    /**
     * Sets the value of the doorStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoorStatusResult(String value) {
        this.doorStatusResult = value;
    }

}
