package demo.vmware.gemfire.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

public class CacheLogger
	extends CacheListenerAdapter<Object, Object>
{

	private static final Logger logger = LoggerFactory
			.getLogger(CacheLogger.class);

	@Override
	public void afterCreate(EntryEvent<Object, Object> event)
	{
		if (!event.getRegion().getName().equalsIgnoreCase("DEALS"))
		{
			logger.info("Added to Cache: " + messageLog(event));
		}
	}

	@Override
	public void afterDestroy(EntryEvent<Object, Object> event)
	{
		logger.info("Removed from Cache: " + messageLog(event));
	}

	@Override
	public void afterUpdate(EntryEvent<Object, Object> event)
	{
		logger.info("Updated in Cache: " + messageLog(event));
	}

	private String messageLog(EntryEvent<Object, Object> event)
	{
		Object key = event.getKey();
		Object value = event.getNewValue();

		if (event.getOperation().isUpdate())
		{
			return "[" + key + "] from [" + event.getOldValue() + "] to ["
					+ event.getNewValue() + "]";
		}
		return "[" + key + "=" + value + "]";
	}
}
