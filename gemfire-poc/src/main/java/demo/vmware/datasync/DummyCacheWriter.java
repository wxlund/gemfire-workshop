package demo.vmware.datasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheWriterAdapter;

public class DummyCacheWriter extends CacheWriterAdapter<Object, Object> {

	private static final Logger logger = LoggerFactory.getLogger(DummyCacheWriter.class);
	
	@Override
	public void beforeCreate(EntryEvent<Object, Object> event) {
		logger.info("Before Adding to Cache: " + messageLog(event));
		//throw new RuntimeException("don't do it!");
	}

	@Override
	public void beforeDestroy(EntryEvent<Object, Object> event) {
		logger.info("Before Removed from Cache: " + messageLog(event));
	}

	@Override
	public void beforeUpdate(EntryEvent<Object, Object> event) {
		logger.info("Before Updated in Cache: " + messageLog(event));
	}

	private String messageLog(EntryEvent<Object, Object> event) {
		Object key = event.getKey();
		Object value = event.getNewValue();

		if (event.getOperation().isUpdate()) {
			return "[" + key + "] from [" + event.getOldValue() + "] to [" + event.getNewValue() + "]";
		}
		return "[" + key + "=" + value + "]";
	}
}


