/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.dao;

import org.springframework.cache.annotation.Cacheable;

import demo.vmware.domain.Dummy;

/**
 * TODO: Describe DeclarativeCachingDAO
 *
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public interface DeclarativeCachingDAO
{

	@Cacheable("DUMMY")
	public abstract Dummy getEntityForId(int i);

	public abstract void removeEntityById(int i);

}