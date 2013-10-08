/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.function;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.Function;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.partition.PartitionRegionHelper;

import demo.vmware.domain.Dummy;

/**
 * TODO: Describe AggregateField2Function
 * 
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
public class AggregateField2Function
	implements Function
{

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(AggregateField2Function.class);
	
	public static final String ID = "AggregateField2Function";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gemstone.gemfire.cache.execute.Function#execute(com.gemstone.gemfire
	 * .cache.execute.FunctionContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(FunctionContext context)
	{
		long sum = 0;
		log.info("Executing function");
		
		if (context instanceof RegionFunctionContext)
		{
			RegionFunctionContext rc = (RegionFunctionContext) context;
			if (PartitionRegionHelper.isPartitionedRegion(rc.getDataSet()))
			{
				Region<Integer, Dummy> efficientReader = PartitionRegionHelper
						.getLocalDataForContext(rc);
				for(Integer key : (Set<Integer>)rc.getFilter())
				{
					Dummy d = efficientReader.get(key);
					if(d != null)
					{
						sum += d.getField2();
					}
				}
			}
		}
		context.getResultSender().lastResult(sum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemstone.gemfire.cache.execute.Function#getId()
	 */
	@Override
	public String getId()
	{
		return ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemstone.gemfire.cache.execute.Function#hasResult()
	 */
	@Override
	public boolean hasResult()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemstone.gemfire.cache.execute.Function#isHA()
	 */
	@Override
	public boolean isHA()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gemstone.gemfire.cache.execute.Function#optimizeForWrite()
	 */
	@Override
	public boolean optimizeForWrite()
	{
		return false;
	}

}