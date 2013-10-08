package demo.pivotal.util;

import java.sql.SQLException;

public class H2RunScriptUtil {

	private String[] args;
	
	public H2RunScriptUtil(String[] args)
	{
		this.args = args;
	}
	
	public void run() throws SQLException
	{
		org.h2.tools.RunScript.main(args);
	}
}
