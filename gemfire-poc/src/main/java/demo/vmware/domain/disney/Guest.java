/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.domain.disney;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Be our guest, be our guest, put our service to the test...
 *
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class Guest
implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Set<Reservation> reservations = new HashSet<Reservation>();

	/**
	 * TODO Describe constructor
	 *
	 */
	public Guest()
	{
		super();
	}

	/**
	 * TODO Describe constructor
	 *
	 * @param id
	 * @param name
	 * @param reservations
	 */
	public Guest(String id, String name, Set<Reservation> reservations)
	{
		super();
		this.id = id;
		this.name = name;
		this.reservations = reservations;
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
	public Set<Reservation> getReservations()
	{
		return reservations;
	}
	public void setReservations(Set<Reservation> reservations)
	{
		this.reservations = reservations;
	}
}
