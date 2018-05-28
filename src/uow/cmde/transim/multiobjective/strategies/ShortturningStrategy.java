package uow.cmde.transim.multiobjective.strategies;

/**
 * 
 * @author Vu The Tran
 * @since 08/08/2012
 *
Short-turning" involves directing a bus to end its current trip before it
reaches the terminus, and service the route in the other direction. This strategy
is employed to return a late bus to schedule, or when an extra service is needed
in the opposite direction, whether it is due to higher passenger demand or from
large gaps in service
 */
public class ShortturningStrategy {

	private int shortturning = 0;
	
	public ShortturningStrategy()
	{
		
	}
	
	public int getX()
	{
		return this.shortturning;
	}
	
	public void setX(int shortturning)
	{
		this.shortturning = shortturning;
	}
}
