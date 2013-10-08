package demo.pivotal.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Locator {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String resource = ("spring-cache-locator.xml");
        ClassPathXmlApplicationContext mainContext = new ClassPathXmlApplicationContext(new String[] {resource}, false);
        mainContext.setValidating(true);
        mainContext.refresh();

        Thread.sleep(Long.MAX_VALUE);
    }

}
