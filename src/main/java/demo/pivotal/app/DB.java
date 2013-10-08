package demo.pivotal.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DB {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String resource = ("spring-db.xml");
        ClassPathXmlApplicationContext mainContext = new ClassPathXmlApplicationContext(new String[] {resource}, false);
        mainContext.setValidating(true);
        mainContext.refresh();

        Thread.sleep(Long.MAX_VALUE);
    }

}
