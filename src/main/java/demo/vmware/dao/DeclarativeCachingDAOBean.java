/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import demo.vmware.domain.Dummy;

/**
 * Provides an example around declaritive caching.
 *
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
@Service("declarativeCachingDao")
public class DeclarativeCachingDAOBean implements DeclarativeCachingDAO
{
	private Logger log = LoggerFactory.getLogger(DeclarativeCachingDAOBean.class);
	
	@Override
	@Cacheable("DUMMY")
	public Dummy getEntityForId(int i)
	{
		log.info("Made it into DAO method!");
		return new Dummy("bogus", 9999);
	}
	
	@Override
	@CacheEvict("DUMMY")
	public void removeEntityById(int i)
	{
		log.info("Not really doing anything here but causing an eviction.");
	}
}
