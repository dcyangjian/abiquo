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

package com.abiquo.api.resources;

import static com.abiquo.api.resources.EnterpriseResource.createTransferObject;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.wink.common.annotations.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.abiquo.api.services.EnterpriseService;
import com.abiquo.api.spring.security.SecurityService;
import com.abiquo.api.util.IRESTBuilder;
import com.abiquo.server.core.enterprise.Enterprise;
import com.abiquo.server.core.enterprise.EnterpriseDto;
import com.abiquo.server.core.enterprise.EnterprisesDto;
import com.abiquo.server.core.util.PagedList;

@Path(EnterprisesResource.ENTERPRISES_PATH)
@Controller
@Workspace(workspaceTitle = "Abiquo administration workspace", collectionTitle = "Enterprises")
public class EnterprisesResource extends AbstractResource
{
    public static final String ENTERPRISES_PATH = "admin/enterprises";

    @Autowired
    private EnterpriseService service;

    @Autowired
    private SecurityService securityService;

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(EnterprisesDto.MEDIA_TYPE)
    public EnterprisesDto getEnterprises(
        @QueryParam(START_WITH) @DefaultValue("0") @Min(0) final Integer startwith,
        @QueryParam(FILTER) @DefaultValue("") final String filterName,
        @QueryParam(LIMIT) @Min(1) @DefaultValue(DEFAULT_PAGE_LENGTH_STRING) final Integer numResults,
        @QueryParam("idPricingTemplate") @DefaultValue("-1") final int idPricingTempl,
        @QueryParam("included") final boolean included, @Context final IRESTBuilder restBuilder)
        throws Exception
    {

        Collection<Enterprise> all =
            service.getEnterprises(startwith, idPricingTempl, included, filterName, numResults);
        EnterprisesDto enterprises = new EnterprisesDto();

        if (all != null && !all.isEmpty())
        {
            for (Enterprise e : all)
            {
                enterprises.add(createTransferObject(e, restBuilder));
            }
        }

        if (all instanceof PagedList< ? >)
        {
            PagedList<Enterprise> list = (PagedList<Enterprise>) all;
            enterprises.setLinks(restBuilder.buildPaggingLinks(
                uriInfo.getAbsolutePath().toString(), list));
            enterprises.setTotalSize(list.getTotalResults());
        }

        return enterprises;
    }

    @POST
    @Consumes(EnterpriseDto.MEDIA_TYPE)
    @Produces(EnterpriseDto.MEDIA_TYPE)
    public EnterpriseDto postEnterprise(final EnterpriseDto enterprise,
        @Context final IRESTBuilder restBuilder) throws Exception
    {
        Enterprise e = service.addEnterprise(enterprise);

        return createTransferObject(e, restBuilder);
    }
}
