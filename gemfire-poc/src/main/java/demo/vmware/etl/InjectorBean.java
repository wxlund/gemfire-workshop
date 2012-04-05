package demo.vmware.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.data.gemfire.GemfireTemplate;

import demo.vmware.domain.Loan;
import demo.vmware.domain.LoanItemKey;
import demo.vmware.domain.LoanKey;
import demo.vmware.domain.LoanLineItem;
import demo.vmware.domain.Resort;

public class InjectorBean
{

	private GemfireTemplate gtResort;

	private GemfireTemplate gtLoan;

	private GemfireTemplate gtLoanLineItem;

	private Boolean loadData;

	private FlatFileItemReader<Resort> resortItemReader;

	private FlatFileItemReader<Loan> loanItemReader;

	private FlatFileItemReader<LoanLineItem> loanLineItemReader;

	private static final Logger logger = LoggerFactory
			.getLogger(InjectorBean.class);

	public void loadData()
		throws Exception
	{
		if (loadData)
		{
			long start = System.currentTimeMillis();
			loadResorts();
			loadLoans();
			loadLoanLineItems();

			long stop = System.currentTimeMillis();

			logger.info("Data load complete in: " + (stop - start) + " ms.");
		}
	}

	private void loadResorts()
		throws Exception
	{
		resortItemReader.open(new ExecutionContext());
		Resort resort = resortItemReader.read();

		while (resort != null)
		{
			gtResort.put(resort.getId(), resort);
			resort = resortItemReader.read();
		}
	}

	private void loadLoans()
		throws Exception
	{
		loanItemReader.open(new ExecutionContext());
		Loan loan = loanItemReader.read();

		while (loan != null)
		{
			gtLoan.put(new LoanKey(loan.getCasefileId()), loan);
			loan = loanItemReader.read();
		}
	}

	private void loadLoanLineItems()
		throws Exception
	{
		loanLineItemReader.open(new ExecutionContext());
		LoanLineItem loanLineItem = loanLineItemReader.read();

		while (loanLineItem != null)
		{
			gtLoanLineItem.put(new LoanItemKey(loanLineItem.getLoanKey(),
					loanLineItem.getLineItemId()), loanLineItem);
			loanLineItem = loanLineItemReader.read();
		}
	}

	public Boolean getLoadData()
	{
		return loadData;
	}

	public void setLoadData(Boolean loadData)
	{
		this.loadData = loadData;
	}

	public FlatFileItemReader<Resort> getResortItemReader()
	{
		return resortItemReader;
	}

	public void setResortItemReader(FlatFileItemReader<Resort> resortItemReader)
	{
		this.resortItemReader = resortItemReader;
	}

	public GemfireTemplate getGtResort()
	{
		return gtResort;
	}

	public void setGtResort(GemfireTemplate gtResort)
	{
		this.gtResort = gtResort;
	}

	public FlatFileItemReader<Loan> getLoanItemReader()
	{
		return loanItemReader;
	}

	public void setLoanItemReader(FlatFileItemReader<Loan> loanItemReader)
	{
		this.loanItemReader = loanItemReader;
	}

	public FlatFileItemReader<LoanLineItem> getLoanLineItemReader()
	{
		return loanLineItemReader;
	}

	public void setLoanLineItemReader(
			FlatFileItemReader<LoanLineItem> loanLineItemReader)
	{
		this.loanLineItemReader = loanLineItemReader;
	}

	public GemfireTemplate getGtLoan()
	{
		return gtLoan;
	}

	public void setGtLoan(GemfireTemplate gtLoan)
	{
		this.gtLoan = gtLoan;
	}

	public GemfireTemplate getGtLoanLineItem()
	{
		return gtLoanLineItem;
	}

	public void setGtLoanLineItem(GemfireTemplate gtLoanLineItem)
	{
		this.gtLoanLineItem = gtLoanLineItem;
	}

}