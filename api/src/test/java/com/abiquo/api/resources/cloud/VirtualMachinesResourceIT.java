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

package com.abiquo.api.resources.cloud;

import static com.abiquo.api.common.Assert.assertLinkExist;
import static com.abiquo.api.common.UriTestResolver.resolveEnterpriseURI;
import static com.abiquo.api.common.UriTestResolver.resolveVirtualImageURI;
import static com.abiquo.api.common.UriTestResolver.resolveVirtualMachinesURI;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.core.Response.Status;

import org.apache.wink.client.ClientResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.abiquo.api.resources.AbstractJpaGeneratorIT;
import com.abiquo.api.resources.appslibrary.VirtualImageResource;
import com.abiquo.model.rest.RESTLink;
import com.abiquo.server.core.appslibrary.VirtualImage;
import com.abiquo.server.core.cloud.NodeVirtualImage;
import com.abiquo.server.core.cloud.VirtualAppliance;
import com.abiquo.server.core.cloud.VirtualDatacenter;
import com.abiquo.server.core.cloud.VirtualMachine;
import com.abiquo.server.core.cloud.VirtualMachineDto;
import com.abiquo.server.core.cloud.VirtualMachinesDto;
import com.abiquo.server.core.enterprise.DatacenterLimits;
import com.abiquo.server.core.enterprise.Enterprise;
import com.abiquo.server.core.enterprise.Privilege;
import com.abiquo.server.core.enterprise.Role;
import com.abiquo.server.core.enterprise.User;
import com.abiquo.server.core.infrastructure.Datacenter;
import com.abiquo.server.core.infrastructure.Machine;

public class VirtualMachinesResourceIT extends AbstractJpaGeneratorIT
{
    protected Enterprise ent;

    protected DatacenterLimits dcallowed;

    protected Datacenter datacenter;

    protected VirtualDatacenter vdc;

    protected VirtualAppliance vapp;

    protected VirtualImage vImage;

    @BeforeMethod
    public void setUp()
    {
        ent = enterpriseGenerator.createUniqueInstance();
        datacenter = datacenterGenerator.createUniqueInstance();
        dcallowed = datacenterLimitsGenerator.createInstance(ent, datacenter);

        vdc = vdcGenerator.createInstance(datacenter, ent);
        vapp = vappGenerator.createInstance(vdc);
        vImage = virtualImageGenerator.createInstance(ent, datacenter);
    }

    @BeforeMethod
    public void setupSysadmin()
    {
        final Enterprise sysEnterprise = enterpriseGenerator.createUniqueInstance();
        final Role r = roleGenerator.createInstanceSysAdmin();
        final User u = userGenerator.createInstance(sysEnterprise, r, "sysadmin", "sysadmin");

        final List<Object> entitiesToSetup = new ArrayList<Object>();

        entitiesToSetup.add(sysEnterprise);
        for (final Privilege p : r.getPrivileges())
        {
            entitiesToSetup.add(p);
        }
        entitiesToSetup.add(r);
        entitiesToSetup.add(u);

        setup(entitiesToSetup.toArray());
    }

    /**
     * Create a virtual appliance. Insert tow virtual machines in the virtual appliance and check
     * it. Check also an 'empty' virtual appliance result
     */
    @Test
    public void getVirtualMachinesTest()
    {
        // Create a virtual machine
        final VirtualMachine vm = vmGenerator.createInstance(ent);
        final VirtualMachine vm2 = vmGenerator.createInstance(ent);

        final Machine machine = vm.getHypervisor().getMachine();
        machine.setDatacenter(vdc.getDatacenter());
        machine.setRack(null);

        final Machine machine2 = vm2.getHypervisor().getMachine();
        machine2.setDatacenter(vdc.getDatacenter());
        machine2.setRack(null);

        final VirtualAppliance vapp2 = vappGenerator.createInstance(vdc);

        // Asociate it to the created virtual appliance
        final NodeVirtualImage nvi = nodeVirtualImageGenerator.createInstance(vapp, vm);
        final NodeVirtualImage nvi2 = nodeVirtualImageGenerator.createInstance(vapp, vm2);

        vm.getVirtualImage().getRepository()
            .setDatacenter(vm.getHypervisor().getMachine().getDatacenter());
        vm2.getVirtualImage().getRepository()
            .setDatacenter(vm2.getHypervisor().getMachine().getDatacenter());

        final List<Object> entitiesToSetup = new ArrayList<Object>();

        entitiesToSetup.add(ent);
        entitiesToSetup.add(datacenter);
        entitiesToSetup.add(dcallowed);
        entitiesToSetup.add(vdc);
        entitiesToSetup.add(vapp);
        entitiesToSetup.add(vapp2);

        for (final Privilege p : vm.getUser().getRole().getPrivileges())
        {
            entitiesToSetup.add(p);
        }

        entitiesToSetup.add(vm.getUser().getRole());
        entitiesToSetup.add(vm.getUser());
        entitiesToSetup.add(vm.getVirtualImage().getRepository());
        entitiesToSetup.add(vm.getVirtualImage().getCategory());
        entitiesToSetup.add(vm.getVirtualImage());
        entitiesToSetup.add(machine);
        entitiesToSetup.add(vm.getHypervisor());
        entitiesToSetup.add(vm);
        entitiesToSetup.add(nvi);

        for (final Privilege p : vm2.getUser().getRole().getPrivileges())
        {
            entitiesToSetup.add(p);
        }

        entitiesToSetup.add(vm2.getUser().getRole());
        entitiesToSetup.add(vm2.getUser());
        entitiesToSetup.add(vm2.getVirtualImage().getRepository());
        entitiesToSetup.add(vm2.getVirtualImage().getCategory());
        entitiesToSetup.add(vm2.getVirtualImage());
        entitiesToSetup.add(machine2);
        entitiesToSetup.add(vm2.getHypervisor());
        entitiesToSetup.add(vm2);
        entitiesToSetup.add(nvi2);

        setup(entitiesToSetup.toArray());

        // Check for vapp
        ClientResponse response = get(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()));
        assertEquals(response.getStatusCode(), Status.OK.getStatusCode());
        VirtualMachinesDto vms = response.getEntity(VirtualMachinesDto.class);
        assertNotNull(vms);
        assertNotNull(vms.getCollection());
        assertEquals(vms.getCollection().size(), 2);

        // Check for vapp2
        response = get(resolveVirtualMachinesURI(vdc.getId(), vapp2.getId()));
        assertEquals(response.getStatusCode(), Status.OK.getStatusCode());
        vms = response.getEntity(VirtualMachinesDto.class);
        assertNotNull(vms);
        assertNotNull(vms.getCollection());
        assertEquals(vms.getCollection().size(), 0);

    }

    /**
     * Check the virtual machines of invalid vitual appliance id. Server response should return a
     * 404 NOT FOUND status code
     */
    @Test
    public void getVirtualMachinesRaises404WhenInvalidVirtualApplianceId()
    {
        setup(ent, datacenter, dcallowed, vdc, vapp);

        final ClientResponse response =
            get(resolveVirtualMachinesURI(vdc.getId(), new Random().nextInt()));
        assertEquals(response.getStatusCode(), Status.NOT_FOUND.getStatusCode());
    }

    /**
     * Check the virtual machines list of an invalid virtualdatacenter for a valid virtual appliance
     * id. Server response should return a 404 NOT FOUND status code
     */
    @Test
    public void getVirtualMachinesRaises404WhenInvalidVirtualDatacenterId()
    {
        setup(ent, datacenter, dcallowed, vdc, vapp);

        final ClientResponse response =
            get(resolveVirtualMachinesURI(new Random().nextInt(), vapp.getId()));
        assertEquals(response.getStatusCode(), Status.NOT_FOUND.getStatusCode());
    }

    /**
     * Creates a virtual machine.
     */
    @Test
    public void createVirtualMachine()
    {
        setup(ent, datacenter, dcallowed, vdc, vapp);
        setup(vImage.getRepository(), vImage.getCategory(), vImage);

        final VirtualMachine vm = vmGenerator.createInstance(vImage, ent, "Image");
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);
        final ClientResponse response =
            post(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()), dto, "sysadmin", "sysadmin");
        assertEquals(response.getStatusCode(), Status.CREATED.getStatusCode());

        String vimageUrl =
            resolveVirtualImageURI(vImage.getEnterprise().getId(), vImage.getRepository()
                .getDatacenter().getId(), vImage.getId());
        assertLinkExist(dto, vimageUrl, VirtualImageResource.VIRTUAL_IMAGE);
    }

    /**
     * Attempt to create a virtual machine with a virtual image not in the same repository
     */
    @Test
    public void createVirtualMachineInvalidVirtualImageDifferentDatacenter()
    {
        Datacenter otherDc = datacenterGenerator.createUniqueInstance();
        VirtualImage otherVimage = virtualImageGenerator.createInstance(ent, otherDc);

        setup(ent, datacenter, dcallowed, vdc, vapp);
        setup(otherDc, otherVimage.getRepository(), otherVimage.getCategory(), otherVimage);

        final VirtualMachine vm = vmGenerator.createInstance(otherVimage, ent, "Image");
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);
        final ClientResponse response =
            post(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()), dto, "sysadmin", "sysadmin");
        assertEquals(response.getStatusCode(), Status.CONFLICT.getStatusCode());
    }

    /**
     * Attempt to create a virtual machine with a virtual image not in the same enterprise
     */
    @Test
    public void createVirtualMachineInvalidVirtualImageDifferentEnterprise()
    {
        Enterprise otherEnt = enterpriseGenerator.createUniqueInstance();
        VirtualImage otherVimage = virtualImageGenerator.createInstance(otherEnt, datacenter);

        setup(ent, datacenter, dcallowed, vdc, vapp);
        setup(otherEnt, otherVimage.getRepository(), otherVimage.getCategory(), otherVimage);

        final VirtualMachine vm = vmGenerator.createInstance(otherVimage, ent, "Image");
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);
        final ClientResponse response =
            post(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()), dto, "sysadmin", "sysadmin");
        assertEquals(response.getStatusCode(), Status.CONFLICT.getStatusCode());
    }

    /**
     * Create a virtual machine with a virtual image not in the same enterprise but shared
     */
    @Test
    public void createVirtualMachineSharedVirtualImageDifferentEnterprise()
    {
        Enterprise otherEnt = enterpriseGenerator.createUniqueInstance();
        VirtualImage otherVimage = virtualImageGenerator.createInstance(otherEnt, datacenter);
        otherVimage.setShared(true);

        setup(ent, datacenter, dcallowed, vdc, vapp);
        setup(otherEnt, otherVimage.getRepository(), otherVimage.getCategory(), otherVimage);

        final VirtualMachine vm = vmGenerator.createInstance(otherVimage, ent, "Image");
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);
        final ClientResponse response =
            post(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()), dto, "sysadmin", "sysadmin");
        assertEquals(response.getStatusCode(), Status.CREATED.getStatusCode());
    }

    /**
     * Attempts to create a virtual machine for a non existent virtual image
     */
    @Test
    public void createVirtualMachine404VirtualImageNotFound()
    {
        setup(ent, datacenter, dcallowed, vdc, vapp);
        setup(vImage.getRepository(), vImage.getCategory(), vImage);

        final VirtualMachine vm = vmGenerator.createInstance(vImage, ent, "Image");
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);

        tearDown("virtualimage");

        final ClientResponse response =
            post(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()), dto, "sysadmin", "sysadmin");
        assertEquals(response.getStatusCode(), Status.NOT_FOUND.getStatusCode());
    }

    /**
     * Attempts to create a virtual machine using a malformed virtual image link
     */
    @Test
    public void createVirtualMachine404VirtualImageMissingLink()
    {
        setup(ent, datacenter, dcallowed, vdc, vapp);
        setup(vImage.getRepository(), vImage.getCategory(), vImage);

        final VirtualMachine vm = vmGenerator.createInstance(vImage, ent, "Image");
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);
        for (RESTLink vimagerLink : dto.getLinks())
        {
            if (vimagerLink.getRel().equalsIgnoreCase("virtualimage"))
            {
                vimagerLink.setHref("http://i/m/dummy/user");
            }
        }

        final ClientResponse response =
            post(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()), dto, "sysadmin", "sysadmin");
        assertEquals(response.getStatusCode(), Status.NOT_FOUND.getStatusCode());
    }

    /**
     * Attempts to create a virtual machine for a datacenter not allowed (in the vimage)
     */
    @Test
    public void createVirtualMachine409VirtualImageInDatacenterNotAllowed()
    {
        setup(ent, datacenter, vdc, vapp); // dcallowed
        setup(vImage.getRepository(), vImage.getCategory(), vImage);

        final VirtualMachine vm = vmGenerator.createInstance(vImage, ent, "Image");
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);

        final ClientResponse response =
            post(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()), dto, "sysadmin", "sysadmin");
        assertEquals(response.getStatusCode(), Status.CONFLICT.getStatusCode());
    }

    /**
     * Creates a virtual machine.Disabled until the VirtualImage resource is done
     */
    @Test(enabled = false)
    public void createVirtualMachine404InvalidDatacenterId()
    {
        setup(ent, datacenter, vdc, vapp);

        final VirtualMachine vm = vmGenerator.createInstance(ent);
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);
        final ClientResponse response =
            post(resolveVirtualMachinesURI(new Random().nextInt(), vapp.getId()), dto, "sysadmin",
                "sysadmin");
        assertEquals(response.getStatusCode(), Status.NOT_FOUND.getStatusCode());
    }

    /**
     * Creates a virtual machine.Disabled until the VirtualImage resource is done
     */
    @Test(enabled = false)
    public void createVirtualMachine404InvalidVapp()
    {
        setup(ent, datacenter, vdc, vapp);

        final VirtualMachine vm = vmGenerator.createInstance(ent);
        final VirtualMachineDto dto = fromVirtualMachineToDto(vm);
        final ClientResponse response =
            post(resolveVirtualMachinesURI(vdc.getId(), new Random().nextInt()), dto, "sysadmin",
                "sysadmin");
        assertEquals(response.getStatusCode(), Status.NOT_FOUND.getStatusCode());
    }

    private VirtualMachineDto fromVirtualMachineToDto(final VirtualMachine vm)
    {
        final VirtualMachineDto dto = new VirtualMachineDto();
        dto.setCpu(vm.getCpu());
        dto.setDescription(vm.getDescription());
        dto.setHdInBytes(vm.getHdInBytes());
        dto.setHighDisponibility(vm.getHighDisponibility());
        dto.setName(vm.getName());

        // dto.setIdState(v.getidState)
        if (vm.getIdType() == 0)
        {
            dto.setIdType(com.abiquo.server.core.cloud.VirtualMachine.NOT_MANAGED);
        }
        else
        {
            dto.setIdType(com.abiquo.server.core.cloud.VirtualMachine.MANAGED);
        }
        dto.setPassword(vm.getPassword());
        dto.setRam(vm.getRam());
        dto.setVdrpIP(vm.getVdrpIP());
        dto.setVdrpPort(vm.getVdrpPort());
        final RESTLink enterpriseLink =
            new RESTLink("enterprise", resolveEnterpriseURI(vm.getEnterprise().getId()));
        dto.addLink(enterpriseLink);

        final RESTLink vImageLink =
            new RESTLink("virtualimage", resolveVirtualImageURI(vm.getVirtualImage()
                .getEnterprise().getId(), vm.getVirtualImage().getRepository().getDatacenter()
                .getId(), vm.getVirtualImage().getId()));
        dto.addLink(vImageLink);

        return dto;
    }

}
