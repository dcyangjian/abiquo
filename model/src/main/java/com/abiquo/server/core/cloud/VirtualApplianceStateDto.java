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

package com.abiquo.server.core.cloud;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.abiquo.model.transport.SingleResourceTransportDto;
import com.abiquo.server.core.infrastructure.DatacenterDto;

/**
 * DTO to modify state parameters of the appliance.
 * 
 * @author ssedano
 */
@XmlRootElement(name = "virtualApplianceState")
public class VirtualApplianceStateDto extends SingleResourceTransportDto implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 7891496375111061310L;
    public static final String BASE_MEDIA_TYPE = "application/vnd.abiquo.virtualappliancestate+xml";
    public static final String MEDIA_TYPE = BASE_MEDIA_TYPE + "; version=" + API_VERSION;
    
    /**
     * Machine power state. <br>
     * Values
     * <ul>
     * <li><b>DEPLOYED</b></li>
     * <li><b>NOT_DEPLOYED</b></li>
     * <li><b>NEEDS_SYNCHRONIZE</b></li>
     * </ul>
     */
    private VirtualApplianceState power;

    /**
     * Machine power state.
     * 
     * @return <ul>
     *         <li><b>DEPLOYED</b></li>
     *         <li><b>NOT_DEPLOYED</b></li>
     *         <li><b>NEEDS_SYNCHRONIZE</b></li>
     *         </ul>
     */
    public VirtualApplianceState getPower()
    {
        return power;
    }

    /**
     * Machine power state. <br>
     * Values
     * <ul>
     * <li><b>DEPLOYED</b></li>
     * <li><b>NOT_DEPLOYED</b></li>
     * <li><b>NEEDS_SYNCHRONIZE</b></li>
     * </ul>
     * 
     * @param power <ul>
     *            <li><b>DEPLOYED</b></li>
     *            <li><b>NOT_DEPLOYED</b></li>
     *            <li><b>NEEDS_SYNCHRONIZE</b></li>
     *            </ul>
     *            void
     */
    public void setPower(final VirtualApplianceState power)
    {
        this.power = power;
    }
    
    @Override
    public String getMediaType()
    {
        return VirtualApplianceStateDto.MEDIA_TYPE;
    }
}
