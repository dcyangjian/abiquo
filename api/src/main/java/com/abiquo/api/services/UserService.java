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

package com.abiquo.api.services;

import static com.abiquo.api.util.URIResolver.buildPath;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abiquo.api.exceptions.APIError;
import com.abiquo.api.exceptions.NotFoundException;
import com.abiquo.api.resources.EnterpriseResource;
import com.abiquo.api.resources.EnterprisesResource;
import com.abiquo.api.resources.RoleResource;
import com.abiquo.api.resources.RolesResource;
import com.abiquo.api.util.URIResolver;
import com.abiquo.model.rest.RESTLink;
import com.abiquo.server.core.enterprise.Enterprise;
import com.abiquo.server.core.enterprise.EnterpriseRep;
import com.abiquo.server.core.enterprise.Role;
import com.abiquo.server.core.enterprise.User;
import com.abiquo.server.core.enterprise.UserDto;

@Service
@Transactional(readOnly = true)
public class UserService extends DefaultApiService
{

    @Autowired
    EnterpriseRep repo;

    public UserService()
    {

    }

    // use this to initialize it for tests
    public UserService(EntityManager em)
    {
        repo = new EnterpriseRep(em);
    }

    /**
     * Based on the spring authentication context.
     * 
     * @see SecurityContextHolder
     */
    public User getCurrentUser()
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        return repo.getUserByUserName(userName);
    }

    public Collection<User> getUsers()
    {
        return repo.findAllUsers();
    }

    public Collection<User> getUsersByEnterprise(final Integer enterpriseId)
    {
        return repo.findUsersByEnterprise(findEnterprise(enterpriseId));
    }

    public Collection<User> getUsersByEnterprise(String enterpriseId, String filter, String order,
        boolean desc)
    {
        return getUsersByEnterprise(enterpriseId, filter, order, desc, false, 0, 25);
    }

    public Collection<User> getUsersByEnterprise(String enterpriseId, String filter, String order,
        boolean desc, boolean connected, Integer page, Integer numResults)
    {
        Enterprise enterprise = null;
        if (!enterpriseId.equals("_"))
        {
            enterprise = findEnterprise(Integer.valueOf(enterpriseId));
        }
        if (StringUtils.isEmpty(order))
        {
            order = User.NAME_PROPERTY;
        }

        return repo.findUsersByEnterprise(enterprise, filter, order, desc, connected, page,
            numResults);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User addUser(UserDto dto, Integer enterpriseId)
    {
        Role role = findRole(dto);
        return addUser(dto, enterpriseId, role);
    }

    public User addUser(UserDto dto, Integer enterpriseId, Role role)
    {
        Enterprise enterprise = findEnterprise(enterpriseId);

        checkUserCredentials(enterprise);

        User user =
            enterprise.createUser(role, dto.getName(), dto.getSurname(), dto.getEmail(), dto
                .getNick(), dto.getPassword(), dto.getLocale());
        user.setActive(dto.isActive() ? 1 : 0);
        user.setDescription(dto.getDescription());
        user.setAvailableVirtualDatacenters(dto.getAvailableVirtualDatacenters());

        if (!user.isValid())
        {
            addValidationErrors(user.getValidationErrors());
            flushErrors();
        }
        if (repo.existAnyUserWithNick(user.getNick()))
        {
            errors.add(APIError.USER_DUPLICATED_NICK);
            flushErrors();
        }
        if(!emailIsValid(user.getEmail()))
        {
        	errors.add(APIError.EMAIL_IS_INVALID);
        	flushErrors();
        }
        

        repo.insertUser(user);

        return user;
    }

    public User getUser(Integer id)
    {
        return repo.findUserById(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User modifyUser(Integer userId, UserDto user)
    {
        User old = repo.findUserById(userId);
        if (old == null)
        {
            throw new NotFoundException(APIError.USER_NON_EXISTENT);
        }

        checkUserCredentials(old.getEnterprise());

        old.setActive(user.isActive() ? 1 : 0);
        old.setEmail(user.getEmail());
        old.setLocale(user.getLocale());
        old.setName(user.getName());
        old.setPassword(user.getPassword());
        old.setSurname(user.getSurname());
        old.setNick(user.getNick());
        old.setDescription(user.getDescription());
        old.setAvailableVirtualDatacenters(user.getAvailableVirtualDatacenters());
        
        if(!emailIsValid(user.getEmail()))
        {
        	errors.add(APIError.EMAIL_IS_INVALID);
        	flushErrors();
        }
        if (user.searchLink(RoleResource.ROLE) != null)
        {
            old.setRole(findRole(user));
        }
        if (user.searchLink(EnterpriseResource.ENTERPRISE) != null)
        {
            old.setEnterprise(findEnterprise(getEnterpriseID(user)));
        }

        if (!old.isValid())
        {
            addValidationErrors(old.getValidationErrors());
            flushErrors();
        }
        if (repo.existAnyOtherUserWithNick(old, old.getNick()))
        {
            errors.add(APIError.USER_DUPLICATED_NICK);
            flushErrors();
        }
        

        return updateUser(old);
    }

    public User updateUser(User user) {
        repo.updateUser(user);

        return user;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeUser(Integer id)
    {
        User user = getUser(id);
        if (user == null)
        {
            throw new NotFoundException(APIError.USER_NON_EXISTENT);
        }

        checkUserCredentials(user.getEnterprise());

        repo.removeUser(user);
    }

    public boolean isAssignedTo(final Integer enterpriseId, final Integer userId)
    {
        User user = getUser(userId);

        return user != null && user.getEnterprise().getId().equals(enterpriseId);
    }

    private Enterprise findEnterprise(Integer enterpriseId)
    {
        Enterprise enterprise = repo.findById(enterpriseId);
        if (enterprise == null)
        {
            throw new NotFoundException(APIError.NON_EXISTENT_ENTERPRISE);
        }
        return enterprise;
    }

    public User findUserByEnterprise(Integer userId, Enterprise enterprise)
    {
        User user = repo.findUserByEnterprise(userId, enterprise);
        if (user == null)
        {
            throw new NotFoundException(APIError.USER_NON_EXISTENT);
        }
        return user;
    }

    private Role findRole(UserDto dto)
    {
        return repo.findRoleById(getRoleId(dto));
    }

    private Integer getRoleId(UserDto user)
    {
        RESTLink role = user.searchLink(RoleResource.ROLE);

        if (role == null)
        {
            throw new NotFoundException(APIError.MISSING_ROLE_LINK);
        }

        String buildPath = buildPath(RolesResource.ROLES_PATH, RoleResource.ROLE_PARAM);
        MultivaluedMap<String, String> roleValues =
            URIResolver.resolveFromURI(buildPath, role.getHref());

        if (roleValues == null || !roleValues.containsKey(RoleResource.ROLE))
        {
            throw new NotFoundException(APIError.ROLE_PARAM_NOT_FOUND);
        }

        Integer roleId = Integer.valueOf(roleValues.getFirst(RoleResource.ROLE));
        return roleId;
    }

    private Integer getEnterpriseID(UserDto user)
    {
        RESTLink ent = user.searchLink(EnterpriseResource.ENTERPRISE);

        String buildPath =
            buildPath(EnterprisesResource.ENTERPRISES_PATH, EnterpriseResource.ENTERPRISE_PARAM);
        MultivaluedMap<String, String> values =
            URIResolver.resolveFromURI(buildPath, ent.getHref());

        Integer entId = Integer.valueOf(values.getFirst(EnterpriseResource.ENTERPRISE));
        return entId;
    }

    public void checkUserCredentials(Enterprise enterprise)
    {
        User user = getCurrentUser();
        checkUserCredentials(user, enterprise);
    }

    private void checkUserCredentials(User user, Enterprise enterprise)
    {
        if (user.getRole().getType() == Role.Type.ENTERPRISE_ADMIN
            && !enterprise.equals(user.getEnterprise()))
        {
            throw new AccessDeniedException("");
        }
    }
    private Boolean emailIsValid(String email)
    {
    	final Pattern pattern;
    	final Matcher matchers;
    	final String EMAIL_PATTERN = 
            "[a-z0-9A-Z!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9A-Z!#$%&'*+/=?^_`{|}~-]+)*@" +
    		"(?:[a-z0-9A-Z](?:[a-z0-9A-Z-]*[a-z0-9A-Z])?\\.)+[a-z0-9A-Z](?:[a-z0-9A-Z-]*[a-z0-9A-Z])?";
    	pattern = Pattern.compile(EMAIL_PATTERN);
    	matchers = pattern.matcher(email);
    	return matchers.matches();
    }
}
