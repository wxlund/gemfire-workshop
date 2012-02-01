package demo.vmware.gemfire.util;

public interface ServerPortGenerator
{

	/**
	 * 
	 * Generates a port that is guaranteed to be available to bind to at the point this method is called.
	 *
	 * @return A port that is available to be bound to at the point this method is called.
	 */
	public abstract int generatePort();

}