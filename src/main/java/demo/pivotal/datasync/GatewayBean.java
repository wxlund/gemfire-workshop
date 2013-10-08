/*
 * pivotal
 * Copyright, pivotal, Inc.
 */
package demo.pivotal.datasync;

import java.util.ArrayList;
import java.util.List;

import com.gemstone.gemfire.cache.util.Gateway;
import com.gemstone.gemfire.cache.util.GatewayEventListener;
import com.gemstone.gemfire.cache.util.GatewayHub;
import com.gemstone.gemfire.cache.util.GatewayQueueAttributes;

/**
 * A convenient way to configure gateway endpoints for hubs.
 *
 * @author <a href="mailto:cdelashmutt@pivotal.com">cdelashmutt</a>
 * @version 1.0
 */
public class GatewayBean
{
	Gateway gateway;
	
	String id;
	List<GatewayEndpointBean> endpoints = new ArrayList<GatewayEndpointBean>();
	List<GatewayEventListener> listeners = new ArrayList<GatewayEventListener>();
	GatewayQueueAttributes attribs = new GatewayQueueAttributes();
	int queueSize;
	int socketBufferSize = Gateway.DEFAULT_SOCKET_BUFFER_SIZE;
	int socketReadTimeout = Gateway.DEFAULT_SOCKET_READ_TIMEOUT;
	
	/**
	 * @return the gateway
	 */
	public Gateway getGateway()
	{
		return gateway;
	}

	/**
	 * @param gateway the gateway to set
	 */
	public void setGateway(Gateway gateway)
	{
		this.gateway = gateway;
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
	 * @return the endpoints
	 */
	public List<GatewayEndpointBean> getEndpoints()
	{
		return endpoints;
	}

	/**
	 * @param endpoints the endpoints to set
	 */
	public void setEndpoints(List<GatewayEndpointBean> endpoints)
	{
		this.endpoints = endpoints;
	}

	/**
	 * @return the listeners
	 */
	public List<GatewayEventListener> getListeners()
	{
		return listeners;
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(List<GatewayEventListener> listeners)
	{
		this.listeners = listeners;
	}

	/**
	 * @return the queueSize
	 */
	public int getQueueSize()
	{
		return queueSize;
	}

	/**
	 * @param queueSize the queueSize to set
	 */
	public void setQueueSize(int queueSize)
	{
		this.queueSize = queueSize;
	}

	/**
	 * @return the socketBufferSize
	 */
	public int getSocketBufferSize()
	{
		return socketBufferSize;
	}

	/**
	 * @param socketBufferSize the socketBufferSize to set
	 */
	public void setSocketBufferSize(int socketBufferSize)
	{
		this.socketBufferSize = socketBufferSize;
	}

	/**
	 * @return the socketReadTimeout
	 */
	public int getSocketReadTimeout()
	{
		return socketReadTimeout;
	}

	/**
	 * @param socketReadTimeout the socketReadTimeout to set
	 */
	public void setSocketReadTimeout(int socketReadTimeout)
	{
		this.socketReadTimeout = socketReadTimeout;
	}

	/**
	 * @return the attribs
	 */
	public GatewayQueueAttributes getAttribs()
	{
		return attribs;
	}

	/**
	 * @param attribs the attribs to set
	 */
	public void setAttribs(GatewayQueueAttributes attribs)
	{
		this.attribs = attribs;
	}

	public void addToGatewayHub(GatewayHub hub)
	{
		gateway = hub.addGateway(id);
		gateway.setSocketBufferSize(socketBufferSize);
		gateway.setSocketReadTimeout(socketReadTimeout);
		gateway.setQueueAttributes(attribs);
		
		for(GatewayEventListener listener : listeners)
		{
			gateway.addListener(listener);
		}
		
		for(GatewayEndpointBean endpoint : endpoints)
		{
			endpoint.addToGateway(gateway);
		}
	}
}
