package uow.cmde.transim.multiobjective.strategies;

/**
 * 
 * @author Vu The Tran
 * @since 08/08/2012
 *
 * Deadheading involves pulling a bus from service and running it empty for
 * a segment of the route
 *
 */
public class DeadheadingStrategy {

	private int deadheading = 0;
	
	public DeadheadingStrategy()
	{
		
	}
	
	public int getX()
	{
		return this.deadheading;
	}
	
	public void setX(int deadheading)
	{
		this.deadheading = deadheading;
	}
}
