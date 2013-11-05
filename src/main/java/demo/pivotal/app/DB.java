package demo.pivotal.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DB {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext mainContext = new ClassPathXmlApplicationContext(new String[] {"spring-db.xml"}, false);
        mainContext.setValidating(true);
        mainContext.refresh();

        try
		{
        	System.out.println("\nDB Started");
        	System.out.println("Go to http://localhost:8082 for DB Web Interface");
			Thread.sleep(Long.MAX_VALUE);
		}
		finally
		{
			mainContext.close();
		}
    }

}
