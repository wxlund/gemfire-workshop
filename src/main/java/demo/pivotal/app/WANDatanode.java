package demo.pivotal.app;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import demo.pivotal.domain.Resort;

/**
 * Hello world!
 * 
 */
public class WANDatanode
{

	public static void main(String[] args)
		throws Exception
	{
		String resource = "spring-cache-server-wan.xml";
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
		System.out.println("1. Insert Resort for replication");
		System.out.println("2. Update Resort for replication");
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
			}
		}
	}

	public static void useCase1_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext
				.getBean("gtResortRepl");

		// Insert Resort
		Resort r = new Resort("505",
				"Disney Land Resort NEW");
		gt.put(r.getId(), r);
	}

	public static void useCase2_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext
				.getBean("gtResortRepl");

		// update resort
		Resort r = gt.get("505");
		if(r != null)
		{
			r.setName("Disney Land Resort UPDATED-" + String.valueOf(System.currentTimeMillis()));
			gt.put(r.getId(), r);
		}
		else
		{
			System.out.println("No resort with ID 505 found.");
		}
	}

}
