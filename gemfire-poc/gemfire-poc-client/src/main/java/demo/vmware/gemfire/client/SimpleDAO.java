package demo.vmware.gemfire.client;

import javax.annotation.Resource;

import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.stereotype.Service;

import demo.vmware.gemfire.domain.Simple;

@Service
public class SimpleDAO
{
	@Resource
	public GemfireTemplate gtSimple;
	
	public Simple findById(String id)
	{
		return gtSimple.get(id);
	}
	
	public void save(Simple s)
	{
		gtSimple.put(s.getId(), s);
	}
}
