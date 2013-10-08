/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.datasync;

import com.gemstone.gemfire.cache.util.Gateway;

/**
 * TODO: Describe GatewayEndpointBean
 *
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class GatewayEndpointBean
	implements Gateway.Endpoint
{

	private String host;
	private String id;
	private int port;

	/**
	 * @return the host
	 */
	public String getHost()
	{
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * @return the port
	 */
	public int getPort()
	{
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public void addToGateway(Gateway gateway)
	{
		gateway.addEndpoint(id, host, port);
	}
}
