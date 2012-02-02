/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.gemfire.domain;

import java.io.Serializable;

/**
 * A simple domain object to test gets and puts with.
 *
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class Simple
implements Serializable
{
	/**
	 * TODO: Describe serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String value;

	public Simple()
	{
		
	}
	
	public Simple(String id, String value)
	{
		this.id = id;
		this.value = value;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
