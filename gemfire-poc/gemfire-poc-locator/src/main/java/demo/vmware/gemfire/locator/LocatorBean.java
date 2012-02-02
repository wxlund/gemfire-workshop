package demo.vmware.gemfire.locator;

import java.io.File;
import java.net.InetAddress;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemstone.gemfire.distributed.Locator;
import com.gemstone.gemfire.distributed.internal.InternalLocator;
import com.gemstone.gemfire.i18n.LogWriterI18n;

public class LocatorBean
{
	
	private Logger log = LoggerFactory.getLogger(LocatorBean.class);

	private int port = 10334;

	private File logFile;

	private File stateFile;

	private InetAddress bind;

	private boolean peerLocator = true;

	private boolean serverLocator = true;

	private String hostnameForClients;

	private String locators = "";

	private Locator locator = null;

	public void startLocator()
	{
		try
		{
			Properties props = new Properties();
			props.setProperty("locators", locators);
			props.setProperty("mcast-port", "0");
			locator = InternalLocator.startLocator(port, logFile, stateFile,
					(LogWriterI18n) null, (LogWriterI18n) null, bind, true,
					props, peerLocator, serverLocator, hostnameForClients);
			log.info("Started locator bind=" + bind + " port=" + port
					+ " locators=" + locators);
		}
		catch (Exception x)
		{
			throw new RuntimeException(x);
		}
	}

	public void stopLocator()
	{
		if (locator != null)
		{
			locator.stop();
			locator = null;
		}
	}

	public File getLogFile()
	{
		return logFile;
	}

	public void setLogFile(File log)
	{
		this.logFile = log;
	}

	public File getStateFile()
	{
		return stateFile;
	}

	public void setStateFile(File state)
	{
		this.stateFile = state;
	}

	public String getLocators()
	{
		return locators;
	}

	public void setLocators(String locators)
	{
		this.locators = locators;
	}

}
