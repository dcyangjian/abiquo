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

package com.abiquo.server.core.appslibrary;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.abiquo.model.transport.WrapperDto;
import com.abiquo.server.core.infrastructure.DatacenterDto;

/**
 * Represent a collection of {@link TemplateDefinitionDto}
 */
@XmlRootElement(name = "templateDefinitions")
public class TemplateDefinitionsDto extends WrapperDto<TemplateDefinitionDto>
{

    private static final long serialVersionUID = -6421402033472232181L;
    public static final String BASE_MEDIA_TYPE = "application/vnd.abiquo.templatedefinitions+xml";
    public static final String MEDIA_TYPE = BASE_MEDIA_TYPE + "; version=" + API_VERSION;

    @Override
    @XmlElement(name = "templateDefinition")
    public List<TemplateDefinitionDto> getCollection()
    {
        return collection;
    }
    
    @Override
    public String getMediaType()
    {
        return TemplateDefinitionsDto.MEDIA_TYPE;
    }
    
    @Override
    public String getBaseMediaType()
    {
        return BASE_MEDIA_TYPE;
    }
}
