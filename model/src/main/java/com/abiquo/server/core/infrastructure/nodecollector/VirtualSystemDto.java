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
// Generated on: 2010.12.24 at 11:14:06 AM CET 
//

package com.abiquo.server.core.infrastructure.nodecollector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for VirtualSystemDto complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualSystemDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://abiquo.com/server/core/infrastructure/nodecollector}ComputerSystemType">
 *       &lt;sequence>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vport" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="status" type="{http://abiquo.com/server/core/infrastructure/nodecollector}VirtualSystemStatusEnumType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualSystemDto", propOrder = {"uuid", "vport", "status"})
public class VirtualSystemDto extends ComputerSystemType
{

    @XmlElement(required = true)
    protected String uuid;

    protected long vport;

    @XmlElement(required = true)
    protected VirtualSystemStatusEnumType status;

    /**
     * Gets the value of the uuid property.
     * 
     * @return possible object is {@link String }
     */
    public String getUuid()
    {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setUuid(String value)
    {
        this.uuid = value;
    }

    /**
     * Gets the value of the vport property.
     */
    public long getVport()
    {
        return vport;
    }

    /**
     * Sets the value of the vport property.
     */
    public void setVport(long value)
    {
        this.vport = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return possible object is {@link VirtualSystemStatusEnumType }
     */
    public VirtualSystemStatusEnumType getStatus()
    {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value allowed object is {@link VirtualSystemStatusEnumType }
     */
    public void setStatus(VirtualSystemStatusEnumType value)
    {
        this.status = value;
    }

}
