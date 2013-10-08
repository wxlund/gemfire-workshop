/*
 * pivotal
 * Copyright, pivotal, Inc.
 */
package demo.pivotal.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;

import com.gemstone.gemfire.cache.execute.Function;
import com.gemstone.gemfire.cache.execute.FunctionService;

/**
 * Handles registering functions with the server for execution
 *
 * @author <a href="mailto:cdelashmutt@pivotal.com">cdelashmutt</a>
 * @version 1.0
 */
public class FunctionRegistrar
implements InitializingBean
{
	private Set<Function> functions = new HashSet<Function>();

	public Set<Function> getFunctions()
	{
		return functions;
	}

	public void setFunctions(Set<Function> functions)
	{
		this.functions = functions;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
		throws Exception
	{
		for(Function f : functions)
		{
			FunctionService.registerFunction(f);
		}
	}
	
	
}
