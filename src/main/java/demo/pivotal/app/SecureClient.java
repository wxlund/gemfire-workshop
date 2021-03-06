package demo.pivotal.app;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;

import demo.pivotal.dao.DeclarativeCachingDAO;
import demo.pivotal.domain.Dummy;
import demo.pivotal.function.AggregateField2Function;

public class SecureClient
{

	public static void main(String[] args)
		throws Exception
	{
		String resource = ("spring-cache-client.xml");
		ClassPathXmlApplicationContext mainContext = new ClassPathXmlApplicationContext(
				new String[]
					{ resource }, false);
		mainContext.setValidating(true);
		mainContext.refresh();
		userMenu(mainContext);
	}

	public static void printMenu()
	{
		System.out.println();
		System.out.println("1. Populate Dummy");
		System.out.println("2. Update Dummy");
		System.out.println("3. OQL for Dummy");
		System.out.println("4. Declarative caching");
		System.out.println("5. Distributed Function");
		System.out.println("6. Put for expiry");
		System.out.println("7. Put for eviction");
		System.out.print("Your choice:");

	}

	public static void userMenu(ApplicationContext mainContext)
		throws Exception
	{
		while (true)
		{
			printMenu();
			Scanner s = new Scanner(System.in);
			int choice = s.nextInt();
			switch (choice)
			{
				case 1:
				{
					useCase1_Main(mainContext);
					break;
				}
				case 2:
				{
					useCase2_Main(mainContext);
					break;
				}
				case 3:
				{
					useCase3_Main(mainContext);
					break;
				}
				case 4:
				{
					useCase4_Main(mainContext);
					break;
				}
				case 5:
				{
					useCase5_Main(mainContext);
					break;
				}
				case 6:
				{
					useCase6_Main(mainContext);
					break;
				}
				case 7:
				{
					useCase7_Main(mainContext);
					break;
				}
			}
		}
	}

	public static void useCase1_Main(ApplicationContext mainContext)
		throws Exception
	{
		int puts = 1000;
		long start = System.currentTimeMillis();
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtDummy");

		for (int i = 0; i < puts; i++)
		{
			gt.put(UUID.randomUUID(), new Dummy("field1-" + i, i));
		}
		long stop = System.currentTimeMillis();

		System.out.println("\nParse complete in: " + (stop - start) + " ms."
				+ "\n\tPuts=" + puts);
	}

	public static void useCase2_Main(ApplicationContext mainContext)
		throws Exception
	{

		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtDummy");

		// insert dummy if it doesn't exist
		gt.put(1, new Dummy("field1", 100));

		// update dummy
		Dummy d = gt.get(1);
		d.setField2(200);
		d.setField3("New Field");
		gt.put(1, d);

		System.out.println(gt.get(1));
	}

	public static void useCase3_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtDummy");
		List<Object> results = gt.query(
				"select distinct d from /DUMMY d where field2 = 200").asList();
		for (Object o : results)
		{
			Dummy d = (Dummy) o;
			System.out.println(d);
		}

	}

	public static void useCase4_Main(ApplicationContext mainContext)
		throws Exception
	{

		DeclarativeCachingDAO declarativeCachingDao = (DeclarativeCachingDAO) mainContext
				.getBean("declarativeCachingDao");

		int key = 5678;

		Dummy d = declarativeCachingDao.getEntityForId(key);
		System.out.println("Got entity: " + d.toString());
		System.out.println("Press any key to evict entity");
		System.in.read();

		declarativeCachingDao.removeEntityById(key);
		System.out.println("Press any key to call remove again");
		System.in.read();

		declarativeCachingDao.removeEntityById(key);
	}

	public static void useCase5_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtDummy");

		Set<Integer> keys = new HashSet<Integer>();
		for (int i = 0; i < 500; i++)
		{
			keys.add(i);
		}

		Execution exec = FunctionService.onRegion(gt.getRegion()).withFilter(
				keys);

		ResultCollector<?, ?> rc = exec.execute(AggregateField2Function.ID);

		@SuppressWarnings("unchecked")
		List<Object> results = (List<Object>) rc.getResult();

		for (Object o : results)
		{
			System.out.println(o.toString());
		}
	}

	public static void useCase6_Main(ApplicationContext mainContext)
		throws Exception
	{

		GemfireTemplate gt = (GemfireTemplate) mainContext
				.getBean("gtDummyExpire");

		// insert dummy if it doesn't exist
		gt.put(1, new Dummy("field1", 100));

	}

	public static void useCase7_Main(ApplicationContext mainContext)
		throws Exception
	{

		GemfireTemplate gt = (GemfireTemplate) mainContext
				.getBean("gtDummyEvict");

		// insert dummy if it doesn't exist
		gt.put(UUID.randomUUID().toString(), new Dummy("field1", 100));

	}

}
