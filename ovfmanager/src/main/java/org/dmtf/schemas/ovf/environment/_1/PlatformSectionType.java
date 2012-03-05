/**
 * Abiquo community edition
 * cloud management application for hybrid clouds
 * Copyright (C) 2008-2010 - Abiquo Holdings S.L.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.02.23 at 01:07:44 PM CET 
//


package org.dmtf.schemas.ovf.environment._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.dmtf.schemas.wbem.wscim._1.common.CimString;
import org.w3c.dom.Element;


/**
 * Information about deployment platform
 * 
 * <p>Java class for PlatformSection_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlatformSection_Type">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.dmtf.org/ovf/environment/1}Section_Type">
 *       &lt;sequence>
 *         &lt;element name="Kind" type="{http://schemas.dmtf.org/wbem/wscim/1/common}cimString" minOccurs="0"/>
 *         &lt;element name="Version" type="{http://schemas.dmtf.org/wbem/wscim/1/common}cimString" minOccurs="0"/>
 *         &lt;element name="Vendor" type="{http://schemas.dmtf.org/wbem/wscim/1/common}cimString" minOccurs="0"/>
 *         &lt;element name="Locale" type="{http://schemas.dmtf.org/wbem/wscim/1/common}cimString" minOccurs="0"/>
 *         &lt;element name="Timezone" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlatformSection_Type", propOrder = {
    "kind",
    "version",
    "vendor",
    "locale",
    "timezone",
    "any"
})
public class PlatformSectionType
    extends SectionType
{

    @XmlElement(name = "Kind")
    protected CimString kind;
    @XmlElement(name = "Version")
    protected CimString version;
    @XmlElement(name = "Vendor")
    protected CimString vendor;
    @XmlElement(name = "Locale")
    protected CimString locale;
    @XmlElement(name = "Timezone")
    protected Integer timezone;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the kind property.
     * 
     * @return
     *     possible object is
     *     {@link CimString }
     *     
     */
    public CimString getKind() {
        return kind;
    }

    /**
     * Sets the value of the kind property.
     * 
     * @param value
     *     allowed object is
     *     {@link CimString }
     *     
     */
    public void setKind(CimString value) {
        this.kind = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link CimString }
     *     
     */
    public CimString getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link CimString }
     *     
     */
    public void setVersion(CimString value) {
        this.version = value;
    }

    /**
     * Gets the value of the vendor property.
     * 
     * @return
     *     possible object is
     *     {@link CimString }
     *     
     */
    public CimString getVendor() {
        return vendor;
    }

    /**
     * Sets the value of the vendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link CimString }
     *     
     */
    public void setVendor(CimString value) {
        this.vendor = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link CimString }
     *     
     */
    public CimString getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link CimString }
     *     
     */
    public void setLocale(CimString value) {
        this.locale = value;
    }

    /**
     * Gets the value of the timezone property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimezone() {
        return timezone;
    }

    /**
     * Sets the value of the timezone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimezone(Integer value) {
        this.timezone = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

}
