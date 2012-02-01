package demo.vmware.gemfire.locator;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestLocator
{
	@Resource
	LocatorBean locator;

	@Test
	public void testLocatorBound()
	{
		assertThat(locator, notNullValue());
	}
}
