/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.domain.disney;

import java.io.Serializable;

import org.joda.time.LocalDate;

/**
 * A booking at some location.
 *
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class Reservation
implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private LocalDate startDate;
	private LocalDate endDate;
	private Resort resort;

	/**
	 * TODO Describe constructor
	 *
	 */
	public Reservation()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * TODO Describe constructor
	 *
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @param resort
	 */
	public Reservation(String id, LocalDate startDate, LocalDate endDate,
			Resort resort)
	{
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.resort = resort;
	}


	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public LocalDate getStartDate()
	{
		return startDate;
	}
	public void setStartDate(LocalDate startDate)
	{
		this.startDate = startDate;
	}
	public LocalDate getEndDate()
	{
		return endDate;
	}
	public void setEndDate(LocalDate endDate)
	{
		this.endDate = endDate;
	}
	public Resort getResort()
	{
		return resort;
	}
	public void setResort(Resort resort)
	{
		this.resort = resort;
	}
}
