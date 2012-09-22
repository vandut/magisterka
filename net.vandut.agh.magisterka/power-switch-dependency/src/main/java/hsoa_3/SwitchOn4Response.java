
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
 *         &lt;element name="switch_on4Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "switchOn4Result"
})
@XmlRootElement(name = "switch_on4Response")
public class SwitchOn4Response {

    @XmlElement(name = "switch_on4Result")
    protected String switchOn4Result;

    /**
     * Gets the value of the switchOn4Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchOn4Result() {
        return switchOn4Result;
    }

    /**
     * Sets the value of the switchOn4Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchOn4Result(String value) {
        this.switchOn4Result = value;
    }

}
