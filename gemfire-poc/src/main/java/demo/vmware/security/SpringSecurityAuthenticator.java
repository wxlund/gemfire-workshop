/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.security;

import java.security.Principal;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.distributed.DistributedMember;
import com.gemstone.gemfire.security.AuthenticationFailedException;
import com.gemstone.gemfire.security.Authenticator;

/**
 * TODO: Describe SpringSecurityAuthenticator
 *
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class SpringSecurityAuthenticator
	implements Authenticator, InitializingBean
{
	private static SpringSecurityAuthenticator instance;
	
	private Logger log = LoggerFactory.getLogger(SpringSecurityAuthenticator.class);
	
	private AuthenticationManager manager;

	/* (non-Javadoc)
	 * @see com.gemstone.gemfire.cache.CacheCallback#close()
	 */
	@Override
	public void close()
	{
		//NOP
	}

	/* (non-Javadoc)
	 * @see com.gemstone.gemfire.security.Authenticator#authenticate(java.util.Properties, com.gemstone.gemfire.distributed.DistributedMember)
	 */
	@Override
	public Principal authenticate(Properties props, DistributedMember arg1)
		throws AuthenticationFailedException
	{
		String userName = props.getProperty("userName");
		String password = props.getProperty("password");
		
		Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		log.info("Authenticated user: " + auth.getName());
		return auth;
	}

	/* (non-Javadoc)
	 * @see com.gemstone.gemfire.security.Authenticator#init(java.util.Properties, com.gemstone.gemfire.LogWriter, com.gemstone.gemfire.LogWriter)
	 */
	@Override
	public void init(Properties arg0, LogWriter arg1, LogWriter arg2)
		throws AuthenticationFailedException
	{
		//Don't need these
	}

	public AuthenticationManager getManager()
	{
		return manager;
	}

	public void setManager(AuthenticationManager manager)
	{
		this.manager = manager;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
		throws Exception
	{
		SpringSecurityAuthenticator.instance = this;
	}
	
	public static SpringSecurityAuthenticator getInstance()
	{
		return instance;
	}

}
