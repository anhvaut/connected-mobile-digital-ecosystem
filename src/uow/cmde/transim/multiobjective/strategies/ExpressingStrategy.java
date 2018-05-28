package uow.cmde.transim.multiobjective.strategies;

/**
 * 
 * @author Vu The Tran
 * @since 08/07/2011
 *
 *Expressing" involves sending a bus to a stop further downstream and skipping
 *(not servicing) some, or all, intermediate stops. The objective of this strategy
 *may be either to increase the headway between buses (splitting bunched up
 *buses apart) or to close a service gap further downstream, both in an attempt to
 *balance headways and improve service past the end of the express segment
 */
public class ExpressingStrategy {

	private int expressing = 0;
	
	public ExpressingStrategy()
	{
		
	}
	
	public int getX()
	{
		return expressing;
	}
	
	public void setX(int expressing)
	{
		this.expressing = expressing;
	}
	
}
