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


package com.abiquo.appliancemanager.repositoryspace;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.abiquo.appliancemanager.repositoryspace package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OVFDescription_QNAME = new QName("http://www.abiquo.com/appliancemanager/repositoryspace", "OVFDescription");
    private final static QName _RepositorySpace_QNAME = new QName("http://www.abiquo.com/appliancemanager/repositoryspace", "RepositorySpace");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.abiquo.appliancemanager.repositoryspace
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RepositorySpace }
     * 
     */
    public RepositorySpace createRepositorySpace() {
        return new RepositorySpace();
    }

    /**
     * Create an instance of {@link OVFDescription }
     * 
     */
    public OVFDescription createOVFDescription() {
        return new OVFDescription();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OVFDescription }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.abiquo.com/appliancemanager/repositoryspace", name = "OVFDescription")
    public JAXBElement<OVFDescription> createOVFDescription(OVFDescription value) {
        return new JAXBElement<OVFDescription>(_OVFDescription_QNAME, OVFDescription.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RepositorySpace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.abiquo.com/appliancemanager/repositoryspace", name = "RepositorySpace")
    public JAXBElement<RepositorySpace> createRepositorySpace(RepositorySpace value) {
        return new JAXBElement<RepositorySpace>(_RepositorySpace_QNAME, RepositorySpace.class, null, value);
    }

}
