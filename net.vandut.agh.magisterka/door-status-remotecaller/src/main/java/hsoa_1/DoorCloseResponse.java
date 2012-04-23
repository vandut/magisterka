
package hsoa_1;

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
 *         &lt;element name="door_closeResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "doorCloseResult"
})
@XmlRootElement(name = "door_closeResponse")
public class DoorCloseResponse {

    @XmlElement(name = "door_closeResult")
    protected String doorCloseResult;

    /**
     * Gets the value of the doorCloseResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoorCloseResult() {
        return doorCloseResult;
    }

    /**
     * Sets the value of the doorCloseResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoorCloseResult(String value) {
        this.doorCloseResult = value;
    }

}
