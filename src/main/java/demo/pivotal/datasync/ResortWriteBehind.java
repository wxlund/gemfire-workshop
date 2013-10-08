package demo.pivotal.datasync;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.util.GatewayEvent;
import com.gemstone.gemfire.cache.util.GatewayEventListener;

import demo.pivotal.domain.Resort;

public class ResortWriteBehind implements GatewayEventListener, Declarable {

	private static Logger logger = Logger.getLogger(ResortWriteBehind.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	/**
	 * Depending on the gateway event, execute an insert/update or delete.
	 * 
	 * @param ge
	 */
	protected void process(GatewayEvent ge) {

		try {
			logger.info("DB Write behind START ");

			if (ge.getOperation().isCreate()) {
				logger.info("Before Adding to Cache: " + messageLog(ge));
				this.jdbcTemplate = new JdbcTemplate(dataSource);

				Resort r = (Resort) ge.getDeserializedValue();

				this.jdbcTemplate.update("insert into resort values(?,?)",
						r.getId(), r.getName());

			} else if (ge.getOperation().isUpdate()) {
				logger.info("Before Updated in Cache: " + messageLog(ge));
				this.jdbcTemplate = new JdbcTemplate(dataSource);
				Resort r = (Resort) ge.getDeserializedValue();

				this.jdbcTemplate.update("update resort " + "set name = ? "
						+ "where resort_id = ?", r.getName(), r.getId());
			}

		} catch (Exception se) {
			throw new CacheWriterException(se);
		}
	}

	public boolean processEvents(List<GatewayEvent> events) {
		for (GatewayEvent ge : events) {
			process(ge);
		}
		return true;
	}

	@Override
	public void close() {
		// do nothing
	}

	public void init(Properties p) {

	}

	private String messageLog(GatewayEvent event) {
		return "[Event: + " + event.getOperation() + "[" + event.getKey() + "=" + event.getDeserializedValue()
				+ "]";
	}
}
