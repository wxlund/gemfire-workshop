/*
 * pivotal
 * Copyright, pivotal, Inc.
 */
package demo.pivotal.datasync;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.GatewayException;
import com.gemstone.gemfire.cache.util.GatewayHub;

/**
 * A convenient way to configure Gateway Hubs for the cache.
 *
 * @author <a href="mailto:cdelashmutt@pivotal.com">cdelashmutt</a>
 * @version 1.0
 */
public class GatewayHubBean
	implements InitializingBean
{
	
	private Cache cache;
	
	private String id;
	private int port = GatewayHub.DEFAULT_PORT;
	private String bindAddress = GatewayHub.DEFAULT_BIND_ADDRESS;
	private boolean manualStart = false;
	private int maximumTimeBetweenPings = GatewayHub.DEFAULT_MAXIMUM_TIME_BETWEEN_PINGS;
	private int socketBufferSize = GatewayHub.DEFAULT_SOCKET_BUFFER_SIZE;
	private String startupPolicy = GatewayHub.DEFAULT_STARTUP_POLICY;
	
	private GatewayHub hub;
	
	private List<GatewayBean> gateways = new ArrayList<GatewayBean>();

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
		throws Exception
	{
		hub = cache.addGatewayHub(id, port);
		hub.setBindAddress(bindAddress);
		hub.setManualStart(manualStart);
		hub.setMaximumTimeBetweenPings(maximumTimeBetweenPings);
		hub.setSocketBufferSize(socketBufferSize);
		hub.setStartupPolicy(startupPolicy);
		
		for(GatewayBean gateway : gateways)
		{
			gateway.addToGatewayHub(hub);
		}
		
		hub.start();
	}

	/**
	 * @return
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#getBindAddress()
	 */
	public String getBindAddress()
	{
		return bindAddress;
	}

	/**
	 * @return
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#getId()
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#getManualStart()
	 */
	public boolean getManualStart()
	{
		return manualStart;
	}

	/**
	 * @return
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#getMaximumTimeBetweenPings()
	 */
	public int getMaximumTimeBetweenPings()
	{
		return maximumTimeBetweenPings;
	}

	/**
	 * @return
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#getPort()
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @return
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#getSocketBufferSize()
	 */
	public int getSocketBufferSize()
	{
		return socketBufferSize;
	}

	/**
	 * @return
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#getStartupPolicy()
	 */
	public String getStartupPolicy()
	{
		return startupPolicy;
	}

	/**
	 * @param arg0
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#setBindAddress(java.lang.String)
	 */
	public void setBindAddress(String arg0)
	{
		this.bindAddress = arg0;
	}

	/**
	 * @param arg0
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#setId(java.lang.String)
	 */
	public void setId(String arg0)
	{
		this.id = arg0;
	}

	/**
	 * @param arg0
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#setManualStart(boolean)
	 */
	public void setManualStart(boolean arg0)
	{
		this.manualStart = arg0;
	}

	/**
	 * @param arg0
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#setMaximumTimeBetweenPings(int)
	 */
	public void setMaximumTimeBetweenPings(int arg0)
	{
		this.maximumTimeBetweenPings = arg0;
	}

	/**
	 * @param arg0
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#setPort(int)
	 */
	public void setPort(int arg0)
	{
		this.port = arg0;
	}

	/**
	 * @param arg0
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#setSocketBufferSize(int)
	 */
	public void setSocketBufferSize(int arg0)
	{
		this.socketBufferSize = arg0;
	}

	/**
	 * @param arg0
	 * @throws GatewayException
	 * @see com.gemstone.gemfire.cache.util.GatewayHub#setStartupPolicy(java.lang.String)
	 */
	public void setStartupPolicy(String arg0)
		throws GatewayException
	{
		this.startupPolicy = arg0;
	}

	/**
	 * @return the gateways
	 */
	public List<GatewayBean> getGateways()
	{
		return gateways;
	}

	/**
	 * @param gateways the gateways to set
	 */
	public void setGateways(List<GatewayBean> gateways)
	{
		this.gateways = gateways;
	}

	/**
	 * @return the cache
	 */
	public Cache getCache()
	{
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(Cache cache)
	{
		this.cache = cache;
	}

}
