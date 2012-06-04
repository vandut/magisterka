
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
 *         &lt;element name="get_temperatureResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getTemperatureResult"
})
@XmlRootElement(name = "get_temperatureResponse")
public class GetTemperatureResponse {

    @XmlElement(name = "get_temperatureResult")
    protected String getTemperatureResult;

    /**
     * Gets the value of the getTemperatureResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetTemperatureResult() {
        return getTemperatureResult;
    }

    /**
     * Sets the value of the getTemperatureResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetTemperatureResult(String value) {
        this.getTemperatureResult = value;
    }

}
