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

/**
 * 
 */
package com.abiquo.api.resources;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.apache.wink.common.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.abiquo.api.services.InfrastructureService;
import com.abiquo.api.util.IRESTBuilder;
import com.abiquo.model.rest.RESTLink;
import com.abiquo.model.util.ModelTransformer;
import com.abiquo.server.core.cloud.VirtualMachine;
import com.abiquo.server.core.cloud.VirtualMachineDto;

/**
 * <pre>
 * Resource that contains all the methods related to a Virtual Machine Infrastructure. Exposes all
 * the methods inside the URI
 * http://{host}/api/admin/datacenters/{dcid}/racks/{rackids}/machines/{machineid}/virtualmachines/{vmid}
 * related to perform actions to a virtual machines from the infrastructure path.
 * </pre>
 * 
 * @author jdevesa@abiquo.com
 */
@Parent(VirtualMachinesInfrastructureResource.class)
@Path(VirtualMachineInfrastructureResource.VIRTUAL_MACHINE_INFRASTRUCTURE_PARAM)
@Controller
public class VirtualMachineInfrastructureResource extends AbstractResource
{
    /** Name of the relational object. */
    public static final String VIRTUAL_MACHINE_INFRASTRUCTURE = "virtualmachine";

    /** Param to map the input values related to the virtual machine. */
    public static final String VIRTUAL_MACHINE_INFRASTRUCTURE_PARAM = "{"
        + VIRTUAL_MACHINE_INFRASTRUCTURE + "}";

    @Autowired
    private InfrastructureService service;

    @GET
    public VirtualMachineDto getInfrastructureVirtualMachine(
        @PathParam(DatacenterResource.DATACENTER) @NotNull @Min(1) final Integer datacenterId,
        @PathParam(RackResource.RACK) @NotNull @Min(1) final Integer rackId,
        @PathParam(MachineResource.MACHINE) @Min(1) @NotNull final Integer machineId,
        @PathParam(VirtualMachineInfrastructureResource.VIRTUAL_MACHINE_INFRASTRUCTURE) @Min(1) @NotNull final Integer vmId,
        @Context final IRESTBuilder restBuilder) throws Exception
    {
        VirtualMachine vm =
            service.getVirtualMachineFromInfrastructure(datacenterId, rackId, machineId, vmId);

        return createTransferObject(datacenterId, rackId, machineId, vm, restBuilder);
    }

    /**
     * Static method that converts the persistence {@link VirtualMachine} object to transfer
     * {@link VirtualMachineDto} object. It also adds REST self-discover {@link RESTLink}s
     * 
     * @param vm Input persistence object.
     * @param restBuilder Context-injected {@link RESTLink} builder to create the links.
     * @return the corresponding transfer object.
     * @throws Exception Serialization unhandled exception.
     */
    public static VirtualMachineDto createTransferObject(final Integer datacenterId,
        final Integer rackId, final Integer machineId, final VirtualMachine vm,
        final IRESTBuilder restBuilder) throws Exception
    {
        // TODO: Try with enterprise and user != null
        VirtualMachineDto vmDto =
            ModelTransformer.transportFromPersistence(VirtualMachineDto.class, vm);
        if (vm.getIdType() == 0)
        {
            vmDto.setIdType(com.abiquo.server.core.cloud.VirtualMachine.NOT_MANAGED);
        }
        else
        {
            vmDto.setIdType(com.abiquo.server.core.cloud.VirtualMachine.MANAGED);
        }
        Integer enterpriseId = null;
        Integer userId = null;

        if (vm.getEnterprise() != null)
        {
            enterpriseId = vm.getEnterprise().getId();
        }
        if (vm.getUser() != null)
        {
            userId = vm.getUser().getId();
        }
        vmDto.setLinks(restBuilder.buildVirtualMachineAdminLinks(datacenterId, rackId, machineId,
            enterpriseId, userId));
        return vmDto;
    }
}