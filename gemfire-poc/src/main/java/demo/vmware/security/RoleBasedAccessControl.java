/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.security;

import java.security.Principal;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.operations.OperationContext;
import com.gemstone.gemfire.cache.operations.OperationContext.OperationCode;
import com.gemstone.gemfire.distributed.DistributedMember;
import com.gemstone.gemfire.security.AccessControl;
import com.gemstone.gemfire.security.NotAuthorizedException;

/**
 * TODO: Describe RoleBasedAccessControl
 * 
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class RoleBasedAccessControl
	implements AccessControl, InitializingBean
{

	private static RoleBasedAccessControl instance;

	private Authentication principal;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemstone.gemfire.cache.CacheCallback#close()
	 */
	@Override
	public void close()
	{
		// NOP
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gemstone.gemfire.security.AccessControl#authorizeOperation(java.lang
	 * .String, com.gemstone.gemfire.cache.operations.OperationContext)
	 */
	@Override
	public boolean authorizeOperation(String region, OperationContext context)
	{
		if (context.getOperationCode() == OperationCode.PUT)
		{
			if (!principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
			{
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gemstone.gemfire.security.AccessControl#init(java.security.Principal,
	 * com.gemstone.gemfire.distributed.DistributedMember,
	 * com.gemstone.gemfire.cache.Cache)
	 */
	@Override
	public void init(Principal principal, DistributedMember arg1, Cache arg2)
		throws NotAuthorizedException
	{
		this.principal = (Authentication) principal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
		throws Exception
	{
		RoleBasedAccessControl.instance = this;
	}

	public static RoleBasedAccessControl getInstance()
	{
		return instance;
	}
}
