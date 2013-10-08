package demo.vmware.app;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import demo.vmware.domain.Resort;

/**
 * Hello world!
 * 
 */
public class SecureDatanode {

	public static void main(String[] args) throws Exception {
		String resource = "spring-cache-server-withdb.xml";
		ClassPathXmlApplicationContext mainContext = new ClassPathXmlApplicationContext(
				new String[] { resource }, false);
		mainContext.setValidating(true);
		mainContext.refresh();
		userMenu(mainContext);
	}

	public static void printMenu() {
		System.out.println();
		System.out.println("1. Update Resort with DB write-through");
		System.out.println("2. Insert Resort with DB write-behind");
		System.out.print("Your choice:");

	}

	public static void userMenu(ApplicationContext mainContext)
			throws Exception {
		while (true) {
			printMenu();
			Scanner s = new Scanner(System.in);
			int choice = s.nextInt();
			switch (choice) {
			case 1: {
				useCase1_Main(mainContext);
				break;
			}
			case 2: {
				useCase2_Main(mainContext);
				break;
			}
			}
		}
	}

	public static void useCase1_Main(ApplicationContext mainContext)
			throws Exception {
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtResortThru");

		// update dummy
		Resort r = gt.get("9769f40e0f074b4fb88076eb6ea91c23");
		r.setName("Disney Land Resort UPDATED");
		gt.put(r.getId(), r);
	}
	
	public static void useCase2_Main(ApplicationContext mainContext)
			throws Exception {
		GemfireTemplate gt = (GemfireTemplate) mainContext.getBean("gtResortBehind");

		// update dummy
		Resort r = new Resort(String.valueOf(System.currentTimeMillis()), "Disney Land Resort NEW");
		gt.put(r.getId(), r);
		
		System.out.println("Sleeping 15 seconds then executing update");
		Thread.sleep(15000);
		
		Resort ru = gt.get(r.getId());
		ru.setName("Disney Land Resort NEW2");
		gt.put(ru.getId(), ru);
	}

}
