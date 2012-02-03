package demo.vmware.app;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import com.gemstone.gemfire.cache.Region;

import demo.vmware.domain.Dummy;

public class Client
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

	@SuppressWarnings("unchecked")
	public static void useCase4_Main(ApplicationContext mainContext)
		throws Exception
	{

		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtDummy");

		// insert dummy if it doesn't exist
		((Region<Integer, Dummy>) gt.getRegion()).registerInterest(2000);

		// update dummy
		Dummy d = gt.get(1);
		d.setField2(200);
		d.setField3("New Field");
		gt.put(1, d);

		System.out.println(gt.get(1));
	}

}
