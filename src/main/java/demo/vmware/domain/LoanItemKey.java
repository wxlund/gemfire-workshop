/**
 * 
 */
package demo.vmware.domain;

import java.io.Serializable;

import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.PartitionResolver;

/**
 * @author cdelashmutt
 *
 */
public class LoanItemKey
implements PartitionResolver<String, LoanLineItem>, Serializable
{

	private static final long serialVersionUID = 1L;

	private String loanKey;
	
	private String loanItemKey;

	public LoanItemKey()
	{
	}

	public LoanItemKey(String loanKey, String loanItemKey)
	{
		this.loanKey = loanKey;
		this.loanItemKey = loanItemKey;
	}

	/**
	 * @param opDetails
	 *            the detail of the entry operation e.g.
	 *            {@link com.gemstone.gemfire.cache.Region#get(Object)}
	 * @return object associated with entry operation which allows the
	 *         Partitioned Region to store associated data together
	 * @throws RuntimeException
	 *             any exception thrown will terminate the operation and the
	 *             exception will be passed to the calling thread.
	 */
	public Serializable getRoutingObject(EntryOperation<String, LoanLineItem> opDetails)
	{
		return loanKey;
	}

	/**
	 * Returns the name of the PartitionResolver
	 * 
	 * @return String name
	 */
	public String getName()
	{
		return LoanItemKey.class.getName();
	}

	/**
	 * Called when the region containing this callback is closed or destroyed,
	 * when the cache is closed, or when a callback is removed from a region
	 * using an <code>AttributesMutator</code>.
	 * <p/>
	 * <p>
	 * Implementations should cleanup any external resources such as database
	 * connections. Any runtime exceptions this method throws will be logged.
	 * <p/>
	 * <p>
	 * It is possible for this method to be called multiple times on a single
	 * callback instance, so implementations must be tolerant of this.
	 * 
	 * @see com.gemstone.gemfire.cache.Cache#close()
	 * @see com.gemstone.gemfire.cache.Region#close
	 * @see com.gemstone.gemfire.cache.Region#localDestroyRegion()
	 * @see com.gemstone.gemfire.cache.Region#destroyRegion()
	 * @see com.gemstone.gemfire.cache.AttributesMutator
	 */
	public void close()
	{

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((loanItemKey == null) ? 0 : loanItemKey.hashCode());
		result = prime * result + ((loanKey == null) ? 0 : loanKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanItemKey other = (LoanItemKey) obj;
		if (loanItemKey == null)
		{
			if (other.loanItemKey != null)
				return false;
		}
		else if (!loanItemKey.equals(other.loanItemKey))
			return false;
		if (loanKey == null)
		{
			if (other.loanKey != null)
				return false;
		}
		else if (!loanKey.equals(other.loanKey))
			return false;
		return true;
	}

	public String getLoanKey()
	{
		return loanKey;
	}

	public void setLoanKey(String loanKey)
	{
		this.loanKey = loanKey;
	}

	public String getLoanItemKey()
	{
		return loanItemKey;
	}

	public void setLoanItemKey(String loanItemKey)
	{
		this.loanItemKey = loanItemKey;
	}

}
