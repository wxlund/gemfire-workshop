package demo.vmware.gemfire.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServerPortGenerator
	implements ServerPortGenerator
{
	
	private int min;
	private int max;
	private Logger log = LoggerFactory.getLogger(DefaultServerPortGenerator.class);
	
	/**
	 * 
	 * Will choose a random, open port in the range of 1025 to 65535, inclusive.
	 *
	 */
	public DefaultServerPortGenerator()
	{
		this(1025, 65535);
	}
	
	/**
	 * 
	 * Will choose a random, open port between min and max, inclusive.
	 *
	 * @param min Minimum port number to allocate
	 * @param max Maximum port number to allocate
	 */
	public DefaultServerPortGenerator(int min, int max)
	{
		this.min = min;
		this.max = max;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see demo.vmware.gemfire.util.ServerPortGenerator#generatePort()
	 */
	@Override
	public int generatePort()
	{
		SecureRandom random = new SecureRandom();
		Integer port = null;
		while(port == null)
		{
			int portToTest = random.nextInt(max-min);
			portToTest += min;
			log.trace("Examining port " + portToTest + " to see if it is available.");
			if(canBind(portToTest))
			{
				port = portToTest;
			}
		}
		
		log.trace("Found open port: " + port);
		return port;
	}

	protected boolean canBind(int port)
	{
		boolean canBind = false;
		ServerSocket socket = null;
		try
		{
			socket = new ServerSocket();
			InetSocketAddress addr = new InetSocketAddress(port);
			socket.bind(addr);
			canBind = true;
		}
		catch(IOException e)
		{
			canBind = false;
		}
		finally
		{
			try
			{
				if(socket != null && socket.isBound())
				{
					socket.close();
				}
			}
			catch(Exception e)
			{
				//Consume close failure...what can we do?
			}
		}
		
		return canBind;
	}

}
