package demo.pivotal.app;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import demo.pivotal.domain.Dummy;

/**
 * Hello world!
 * 
 */
public class Datanode
{

	public static void main(String[] args)
		throws Exception
	{
		ClassPathXmlApplicationContext mainContext = new ClassPathXmlApplicationContext(
				new String[]
					{ "spring-cache-server.xml" }, false);
		mainContext.setValidating(true);
		mainContext.refresh();
		userMenu(mainContext);
	}

	public static void printMenu()
	{
		System.out.println();
		System.out.println("1. Populate Dummy Data");
		System.out.println("2. Update Dummy Data");
		System.out.println("3. Add Dummy Data for Client Interest");
		System.out.println("4. Add Dummy Data for Client CQ");
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
			gt.put(i, new Dummy("field1-" + i, i));
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
	}

	public static void useCase3_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtDummy");

		// insert a dummy value with key of 2000
		gt.put(2000, new Dummy("cqTest", 1234));
		System.out.println("Press key to update Dummy Value with key of 2000.");
		System.in.read();

		// update dummy
		Dummy d = gt.get(2000);
		d.setField2(4321);
		d.setField3("cqAfter");
		gt.put(2000, d);
	}

	public static void useCase4_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtDummy");
		int key = 2010;

		// insert a dummy value with field2 of 54321
		gt.put(key, new Dummy("cqTest", 54321));
		System.out
				.println("Press key to update Dummy Value of field2 with 12345.");
		System.in.read();

		// update dummy
		Dummy d = gt.get(key);
		d.setField2(12345);
		gt.put(key, d);
		System.out
				.println("Press key to update Dummy Value of field2 with 22222.");
		System.in.read();

		// update dummy
		d = gt.get(key);
		d.setField2(22222);
		gt.put(key, d);

	}

}
