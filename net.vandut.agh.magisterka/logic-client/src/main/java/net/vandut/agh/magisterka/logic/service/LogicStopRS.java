
package net.vandut.agh.magisterka.logic.service;

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
 *         &lt;element name="logic_stopResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "logicStopResult"
})
@XmlRootElement(name = "logic_stopRS")
public class LogicStopRS {

    @XmlElement(name = "logic_stopResult")
    protected String logicStopResult;

    /**
     * Gets the value of the logicStopResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogicStopResult() {
        return logicStopResult;
    }

    /**
     * Sets the value of the logicStopResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogicStopResult(String value) {
        this.logicStopResult = value;
    }

}
