
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
 *         &lt;element name="switch_on1Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "switchOn1Result"
})
@XmlRootElement(name = "switch_on1Response")
public class SwitchOn1Response {

    @XmlElement(name = "switch_on1Result")
    protected String switchOn1Result;

    /**
     * Gets the value of the switchOn1Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchOn1Result() {
        return switchOn1Result;
    }

    /**
     * Sets the value of the switchOn1Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchOn1Result(String value) {
        this.switchOn1Result = value;
    }

}
