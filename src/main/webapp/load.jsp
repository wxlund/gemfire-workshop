<%@page import="org.springframework.context.ApplicationContext"%>
<%
	try {
        ApplicationContext ctx = (ApplicationContext) application.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
        System.out.println("Load without rebalance");
        demo.pivotal.app.Datanode.useCase1_Main(ctx);
        System.out.println("Rebalancing....");
        //demo.pivotal.app.App.useCase10_Rebalance(ctx);
        System.out.println("Loading....");
        demo.pivotal.app.Datanode.useCase1_Main(ctx);
    } catch (Exception x) {
        x.printStackTrace();
    }
%>