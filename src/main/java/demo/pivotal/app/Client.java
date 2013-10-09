package demo.pivotal.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.util.StopWatch;

import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gemstone.gemfire.cache.query.CqAttributesFactory;
import com.gemstone.gemfire.cache.query.CqEvent;
import com.gemstone.gemfire.cache.query.CqQuery;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.Struct;
import com.gemstone.gemfire.cache.util.CqListenerAdapter;

import demo.pivotal.dao.DeclarativeCachingDAO;
import demo.pivotal.domain.Dummy;
import demo.pivotal.domain.Loan;
import demo.pivotal.domain.LoanKey;
import demo.pivotal.function.AggregateField2Function;
import demo.pivotal.function.JoinQuery;

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
		System.out.println("4. Declarative caching");
		System.out.println("5. Distributed Function");
		System.out.println("6. Put for expiry");
		System.out.println("7. Put for eviction");
		System.out.println("8. Programmatic CQ Registration");
		System.out
				.println("9. Manual join vs. Distributed Function Join on all data");
		System.out
				.println("10. Manual join vs. Distributed Function Join on single key");
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
				case 9:
				{
					useCase9_Main(mainContext);
					break;
				}
				case 10:
				{
					useCase10_Main(mainContext);
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
		System.out.println("Press any key to retrieve entity again");
		System.in.read();

		d = declarativeCachingDao.getEntityForId(key);
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

	public static void useCase8_Main(ApplicationContext mainContext)
		throws Exception
	{

		GemFireCache cache = (GemFireCache) mainContext
				.getBean("gemfire-cache");

		CqAttributesFactory cqaf = new CqAttributesFactory();
		cqaf.addCqListener(new CqListenerAdapter()
		{
			@Override
			public void onEvent(CqEvent event)
			{
				System.out.println("Recieved Event for CQ: "
						+ event.getNewValue());
			}
		});

		CqQuery myCQ = cache.getQueryService().newCq("myCQ",
				"SELECT * from /DUMMY where field3 != null", cqaf.create(),
				true);

		myCQ.execute();

		System.out.println("Press enter to close CQ...");
		System.in.read();

		myCQ.close();
	}

	@SuppressWarnings("unchecked")
	public static void useCase9_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gtLoan = (GemfireTemplate) mainContext
				.getBean("gtLoan");
		GemfireTemplate gtLoanLineItem = (GemfireTemplate) mainContext
				.getBean("gtLoanLineItem");

		StopWatch timer = new StopWatch();

		List<Object[]> loanAndLineItems = new ArrayList<Object[]>();

		timer.start("Manual Join");
		SelectResults<Loan> loans = gtLoan.query("SELECT * from /LOAN");
		for (Loan loan : loans)
		{
			List<Object> loanLineItems = gtLoanLineItem.find(
					"SELECT * from /LOANLINEITEM where loanKey = $1",
					loan.getCasefileId()).asList();
			for (Object loanLineItem : loanLineItems)
			{
				loanAndLineItems.add(new Object[]
					{ loan, loanLineItem });
			}
		}
		timer.stop();
		System.out.println("Got " + loanAndLineItems.size() + " join results.");

		Execution exec = FunctionService
				.onRegion(gtLoan.getRegion())
				.withArgs(
						new String[]
							{ "SELECT * from /LOAN loan,/LOANLINEITEM loanLineItem where loanLineItem.loanKey = loan.casefileId" });

		List<Struct> functionJoinResults = new ArrayList<Struct>();
		timer.start("Distributed Function");
		ResultCollector<?, ?> rc = exec.execute(JoinQuery.ID);
		Object results = rc.getResult();
		ArrayList<Object> resultList = (ArrayList<Object>) results;
		for (int i = 0; i < resultList.size() - 1; i++)
		{
			functionJoinResults.addAll((ArrayList<Struct>) resultList.get(0));
		}
		timer.stop();
		System.out.println("Got " + functionJoinResults.size()
				+ " join results.");

		System.out.println(timer.prettyPrint());
	}

	@SuppressWarnings("unchecked")
	public static void useCase10_Main(ApplicationContext mainContext)
		throws Exception
	{
		GemfireTemplate gtLoan = (GemfireTemplate) mainContext
				.getBean("gtLoan");
		GemfireTemplate gtLoanLineItem = (GemfireTemplate) mainContext
				.getBean("gtLoanLineItem");

		StopWatch timer = new StopWatch();

		List<Object[]> loanAndLineItems = new ArrayList<Object[]>();

		timer.start("Manual Join");
		SelectResults<Loan> loans = gtLoan.find(
				"SELECT * from /LOAN where casefileId = $1", "YRS18WFU8AQ");
		Loan loan = loans.asList().get(0);
		List<Object> loanLineItems = gtLoanLineItem.find(
				"SELECT * from /LOANLINEITEM where loanKey = $1",
				loan.getCasefileId()).asList();
		for (Object loanLineItem : loanLineItems)
		{
			loanAndLineItems.add(new Object[]
				{ loan, loanLineItem });
		}
		timer.stop();
		System.out.println("Got " + loanAndLineItems.size() + " join results.");

		Execution exec = FunctionService
				.onRegion(gtLoan.getRegion())
				.withArgs(
						new String[]
							{
									"SELECT * from /LOAN loan,/LOANLINEITEM loanLineItem where loan.casefileId = $1 AND loanLineItem.loanKey = loan.casefileId",
									"YRS18WFU8AQ" })
				.withFilter(new HashSet<LoanKey>(Arrays.asList(new LoanKey("YRS18WFU8AQ"))));

		List<Struct> functionJoinResults = new ArrayList<Struct>();
		timer.start("Distributed Function");
		ResultCollector<?, ?> rc = exec.execute(JoinQuery.ID);
		Object results = rc.getResult();
		ArrayList<Object> resultList = (ArrayList<Object>) results;
		for (int i = 0; i < resultList.size() - 1; i++)
		{
			functionJoinResults.addAll((ArrayList<Struct>) resultList.get(0));
		}
		timer.stop();
		System.out.println("Got " + functionJoinResults.size()
				+ " join results.");

		System.out.println(timer.prettyPrint());
	}
}
