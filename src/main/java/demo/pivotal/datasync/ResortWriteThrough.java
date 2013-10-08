package demo.pivotal.datasync;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheWriterAdapter;

import demo.pivotal.domain.Resort;

public class ResortWriteThrough extends CacheWriterAdapter<Object, Object> {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	private static final Logger logger = LoggerFactory
			.getLogger(ResortWriteThrough.class);

	@Override
	public void beforeCreate(EntryEvent<Object, Object> event) {

		logger.info("Before Adding to Cache: " + messageLog(event));
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		Resort r = (Resort) event.getNewValue();
		try {
			this.jdbcTemplate.update("insert into resort values(?,?)",
					r.getId(), r.getName());
		} catch (DuplicateKeyException e) {
			logger.error("Attempted to insert a duplicate key: " + r.getId());
		}
	}

	@Override
	public void beforeUpdate(EntryEvent<Object, Object> event) {

		logger.info("Before Updated in Cache: " + messageLog(event));
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		Resort r = (Resort) event.getNewValue();

		this.jdbcTemplate.update("update resort " + "set name = ? "
				+ "where resort_id = ?", r.getName(), r.getId());

	}

	private String messageLog(EntryEvent<Object, Object> event) {
		Object key = event.getKey();
		Object value = event.getNewValue();

		if (event.getOperation().isUpdate()) {
			return "[" + key + "] from [" + event.getOldValue() + "] to ["
					+ event.getNewValue() + "]";
		}
		return "[" + key + "=" + value + "]";
	}

}
