/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.security;

import java.util.Properties;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.distributed.DistributedMember;
import com.gemstone.gemfire.security.AuthInitialize;
import com.gemstone.gemfire.security.AuthenticationFailedException;

/**
 * Simple authentication info collector
 * 
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class ClientAuthInit
	implements AuthInitialize
{

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
	 * com.gemstone.gemfire.security.AuthInitialize#getCredentials(java.util
	 * .Properties, com.gemstone.gemfire.distributed.DistributedMember, boolean)
	 */
	@Override
	public Properties getCredentials(Properties props, DistributedMember arg1,
			boolean arg2)
		throws AuthenticationFailedException
	{
		Properties returnProps = new Properties();
		returnProps.setProperty("userName", props.getProperty("security-userName"));
		returnProps.setProperty("password", props.getProperty("security-password"));
		return returnProps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gemstone.gemfire.security.AuthInitialize#init(com.gemstone.gemfire
	 * .LogWriter, com.gemstone.gemfire.LogWriter)
	 */
	@Override
	public void init(LogWriter arg0, LogWriter arg1)
		throws AuthenticationFailedException
	{
		// NOP
	}

	public static ClientAuthInit create()
	{
		return new ClientAuthInit();
	}
}
