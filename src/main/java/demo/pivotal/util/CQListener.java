/*
 * pivotal
 * Copyright, pivotal, Inc.
 */
package demo.pivotal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple logging listener
 *
 * @author <a href="mailto:cdelashmutt@pivotal.com">cdelashmutt</a>
 * @version 1.0
 */
public class CQListener
{
	private Logger log = LoggerFactory.getLogger(CQListener.class);
	
	public void handleEvent(Object key, Object value)
	{
		log.info("Saw change for value that matches CQ: " + value.toString());
	}
}
