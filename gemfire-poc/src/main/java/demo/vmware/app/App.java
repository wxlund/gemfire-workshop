package demo.vmware.app;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import demo.vmware.domain.Commitment;
import demo.vmware.domain.Dummy;
import demo.vmware.domain.Loan;
import demo.vmware.domain.Product;
import demo.vmware.etl.InjectorBean;

/**
 * Hello world!
 * 
 */
public class App
{

	public static int batchSize = 5000;

	public static void main(String[] args)
		throws Exception
	{
		String resource = "spring-cache-server.xml";
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
		System.out.println("1. Populate Dummy Data");
		System.out.println("2. Update Dummy Data");
		System.out.println("3. Populate Data (Loan,Product,Commitment)");
		System.out.println("4. Request/Response through JMS");
		System.out.println("5. Update Product Data");
		System.out.println("6. Find Commitments By User ID");
		System.out.println("7. Find Loans By User ID");
		System.out.println("8. Find Product By ID");
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
				case 8:
				{
					useCase8_Main(mainContext);
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
	}

	public static void useCase3_Main(ApplicationContext mainContext)
		throws Exception
	{
		InjectorBean ib = (InjectorBean) mainContext.getBean("InjectorBean");
		ib.loadData();
	}

	public static void useCase4_Main(ApplicationContext mainContext)
		throws Exception
	{

		// MessageChannel inputChannel = mainContext.getBean("requestChannel",
		// MessageChannel.class);
		// inputChannel.send(new GenericMessage<String>("800403"));
	}

	public static void useCase5_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtProduct");

		// insert dummy if it doesn't exist
		gt.put(1, new Product("0005FR", "30-Year Fixed Rate - demo"));

		// update dummy
		Product p = gt.get(1);
		p.setProductName("20-Year Fixed Rate");
		gt.put(1, p);
	}

	public static void useCase6_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext
				.getBean("gtCommitment");

		List results = gt.query("userId = 'p8wert'").asList();
		for (Object o : results)
		{
			Commitment c = (Commitment) o;
			System.out.println(c);
		}
	}

	public static void useCase7_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtLoans");

		List results = gt.query("userId = 'user1'").asList();
		for (Object o : results)
		{
			Loan l = (Loan) o;
			System.out.println(l);
		}
	}

	public static void useCase8_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtProduct");

		List results = gt.query("productId = '123'").asList();
		for (Object o : results)
		{
			Product p = (Product) o;
			System.out.println(p);
		}
	}
}
