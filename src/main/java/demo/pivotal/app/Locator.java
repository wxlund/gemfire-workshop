package demo.pivotal.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Locator {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext mainContext = new ClassPathXmlApplicationContext(new String[] {"spring-cache-locator.xml"}, false);
        mainContext.setValidating(true);
        mainContext.refresh();

        try
		{
			Thread.sleep(Long.MAX_VALUE);
		}
		finally
		{
			mainContext.close();
		}
    }

}
