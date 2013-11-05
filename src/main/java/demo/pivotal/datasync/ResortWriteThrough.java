package demo.pivotal.datasync;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheWriterAdapter;

import demo.pivotal.domain.Resort;

public class ResortWriteThrough
	extends CacheWriterAdapter<Object, Object>
{

	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory
			.getLogger(ResortWriteThrough.class);

	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void beforeCreate(EntryEvent<Object, Object> event)
	{

		logger.info("Before Adding to Cache: " + messageLog(event));
		Resort r = (Resort) event.getNewValue();
		doUpsert(r);
	}

	@Override
	public void beforeUpdate(EntryEvent<Object, Object> event)
	{

		logger.info("Before Updated in Cache: " + messageLog(event));
		Resort r = (Resort) event.getNewValue();
		doUpsert(r);

	}

	private void doUpsert(Resort r)
	{
		//Assume updates and try to insert if key doesn't exist
		try
		{
			logger.info("Before Updating to DB: " + r.toString());
			this.jdbcTemplate.update("update resort " + "set name = ? "
					+ "where resort_id = ?", r.getName(), r.getId());
		}
		catch (DataRetrievalFailureException e)
		{
			logger.info("Update failed, trying insert: " + r.toString());
			try
			{
				this.jdbcTemplate.update("insert into resort values(?,?)",
						r.getId(), r.getName());
			}
			catch (DuplicateKeyException e2)
			{
				logger.error("Attempted to insert a duplicate key: "
						+ r.getId());
			}
		}
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
