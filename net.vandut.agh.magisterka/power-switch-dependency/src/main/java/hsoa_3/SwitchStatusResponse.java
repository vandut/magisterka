
package hsoa_3;

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
 *         &lt;element name="switch_statusResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "switchStatusResult"
})
@XmlRootElement(name = "switch_statusResponse")
public class SwitchStatusResponse {

    @XmlElement(name = "switch_statusResult")
    protected String switchStatusResult;

    /**
     * Gets the value of the switchStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchStatusResult() {
        return switchStatusResult;
    }

    /**
     * Sets the value of the switchStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchStatusResult(String value) {
        this.switchStatusResult = value;
    }

}
