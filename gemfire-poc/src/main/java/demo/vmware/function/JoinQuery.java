/**
 * 
 */
package demo.vmware.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemstone.bp.edu.emory.mathcs.backport.java.util.Arrays;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.Struct;

import demo.vmware.domain.Loan;
import demo.vmware.domain.LoanKey;

/**
 * @author cdelashmutt
 * 
 */
public class JoinQuery
	extends FunctionAdapter
{

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(JoinQuery.class);

	public static final String ID = "JoinQuery";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gemstone.gemfire.cache.execute.FunctionAdapter#execute(com.gemstone
	 * .gemfire.cache.execute.FunctionContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(FunctionContext context)
	{
		log.trace("Executing JoinQuery function");
		if (context instanceof RegionFunctionContext)
		{
			RegionFunctionContext rc = (RegionFunctionContext) context;
			Region<LoanKey, Loan> localRegion = rc.getDataSet();
			String args[] = (String[]) rc.getArguments();
			String queryStr = args[0];

			Object params[] = null;
			try
			{
				params = Arrays.copyOfRange(args, 1, args.length);
			}
			catch (Exception e)
			{
				// Ignore
			}
			try
			{
				Query query = localRegion.getRegionService().getQueryService()
						.newQuery(queryStr);
				SelectResults<Struct> results;
				if (params != null && params.length != 0)
				{
					log.trace(String.format("Executing JoinQuery function with query (%s) and parameters %s", queryStr, Arrays.deepToString(params)));

					results = (SelectResults<Struct>) query.execute(rc, params);
				}
				else
				{
					log.trace(String.format("Executing JoinQuery function with query (%s)", queryStr));
					results = (SelectResults<Struct>) query.execute(rc);
				}

				rc.getResultSender().sendResult(results.asList());
			}
			catch (Exception e)
			{
				throw new FunctionException(e);
			}
		}
		else
		{
			// Handle non-region function context.
		}
		context.getResultSender().lastResult(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemstone.gemfire.cache.execute.FunctionAdapter#getId()
	 */
	@Override
	public String getId()
	{
		// TODO Auto-generated method stub
		return ID;
	}

}
