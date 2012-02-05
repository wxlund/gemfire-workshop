package demo.vmware.datasync;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.LoaderHelper;

import demo.vmware.domain.Resort;

public class ResortCacheLoader implements CacheLoader<String, Resort>,
		Declarable {
	private static Logger log = Logger.getLogger(ResortCacheLoader.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	public Resort load(LoaderHelper<String, Resort> helper) {

		String dbLookupKey = helper.getKey();

		this.jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			Resort resort = this.jdbcTemplate.queryForObject(
					"select RESORT_ID, NAME from RESORT where RESORT_ID = ?",
					new Object[] { dbLookupKey }, new RowMapper<Resort>() {
						public Resort mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Resort r = new Resort();
							r.setId(rs.getString("RESORT_ID"));
							r.setName(rs.getString("NAME"));
							return r;
						}
					});
			log.info("Loaded resort from DB: " + resort);
			return resort;
		} catch (EmptyResultDataAccessException e) {
			log.error("No resort matching key: " + dbLookupKey);
			return null;
		}
	}

	public void close() {

		log.info("Closing CacheLoaderResort");

	}

	public void init(Properties p) {

		log.info("Initializing CacheLoaderResort");

	}
}
