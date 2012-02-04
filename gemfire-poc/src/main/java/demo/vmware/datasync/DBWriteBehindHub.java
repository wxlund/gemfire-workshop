package demo.vmware.datasync;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.util.Gateway;
import com.gemstone.gemfire.cache.util.GatewayEventListener;
import com.gemstone.gemfire.cache.util.GatewayHub;

public class DBWriteBehindHub {
	
	@Autowired
	Cache cache;
	GatewayEventListener[] listeners;

	static Logger logger = Logger.getLogger(DBWriteBehindHub.class);
	
	public DBWriteBehindHub(GatewayEventListener[] listeners)
	{
		this.listeners = listeners;
	}

	public void start() {

		try {

			GatewayHub hub = cache.addGatewayHub("DBWriterHub", -1);
			hub.setStartupPolicy(GatewayHub.STARTUP_POLICY_PRIMARY);
			Gateway gateway = hub.addGateway("DBWriter");
			
			for (GatewayEventListener gwe : listeners)
			{
				gateway.addListener(gwe);
				logger.info("DB Writebehind added: " + gwe);
			}
			
			hub.start();

		} catch (IOException e) {
			logger.info("Gateway already running on this OS");
		}
	}
}
