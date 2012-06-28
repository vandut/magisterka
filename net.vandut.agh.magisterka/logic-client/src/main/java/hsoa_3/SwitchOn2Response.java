
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
 *         &lt;element name="switch_on2Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "switchOn2Result"
})
@XmlRootElement(name = "switch_on2Response")
public class SwitchOn2Response {

    @XmlElement(name = "switch_on2Result")
    protected String switchOn2Result;

    /**
     * Gets the value of the switchOn2Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchOn2Result() {
        return switchOn2Result;
    }

    /**
     * Sets the value of the switchOn2Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchOn2Result(String value) {
        this.switchOn2Result = value;
    }

}
