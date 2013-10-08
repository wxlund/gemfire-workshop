/**
 * 
 */
package demo.vmware.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdelashmutt
 *
 */
public class LoanLineItem
implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String loanKey;
	
	private String lineItemId;
	
	private Date paymentDate;
	
	private String amount;

	public LoanLineItem()
	{
		super();
	}
	
	public LoanLineItem(String loanKey, Date paymentDate, String amount)
	{
		super();
		this.loanKey = loanKey;
		this.paymentDate = paymentDate;
		this.amount = amount;
	}

	public String getLoanKey()
	{
		return loanKey;
	}

	public void setLoanKey(String loanKey)
	{
		this.loanKey = loanKey;
	}

	public Date getPaymentDate()
	{
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public String getLineItemId()
	{
		return lineItemId;
	}

	public void setLineItemId(String lineItemId)
	{
		this.lineItemId = lineItemId;
	}
	
}
