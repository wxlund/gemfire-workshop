/*
 * pivotal
 * Copyright, pivotal, Inc.
 */
package demo.pivotal.dao;

import org.springframework.cache.annotation.Cacheable;

import demo.pivotal.domain.Dummy;

/**
 * TODO: Describe DeclarativeCachingDAO
 *
 * @author <a href="mailto:cdelashmutt@pivotal.com">cdelashmutt</a>
 * @version 1.0
 */
public interface DeclarativeCachingDAO
{

	@Cacheable("DUMMY")
	public abstract Dummy getEntityForId(int i);

	public abstract void removeEntityById(int i);

}