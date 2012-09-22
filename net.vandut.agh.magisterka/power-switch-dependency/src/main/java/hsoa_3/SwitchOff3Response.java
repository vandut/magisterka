
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
 *         &lt;element name="switch_off3Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "switchOff3Result"
})
@XmlRootElement(name = "switch_off3Response")
public class SwitchOff3Response {

    @XmlElement(name = "switch_off3Result")
    protected String switchOff3Result;

    /**
     * Gets the value of the switchOff3Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchOff3Result() {
        return switchOff3Result;
    }

    /**
     * Sets the value of the switchOff3Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchOff3Result(String value) {
        this.switchOff3Result = value;
    }

}
