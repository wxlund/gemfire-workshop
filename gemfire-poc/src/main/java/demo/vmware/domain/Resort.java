/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.domain;

import java.io.Serializable;

/**
 * TODO: Describe Resort
 *
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class Resort
implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	
	/**
	 * TODO Describe constructor
	 *
	 */
	public Resort()
	{
		super();
	}

	/**
	 * TODO Describe constructor
	 *
	 * @param id
	 * @param name
	 */
	public Resort(String id, String name)
	{
		super();
		this.id = id;
		this.name = name;
	}

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
