package uow.cmde.transim.multiobjective.strategies;

/**
 * 
 * @author Vu The Tran
 * @since 08/08/2012
 *
 *Holding is the control strategy of delaying a bus at some a point in the
 *network for a set amount of time. It aims to rectify a bus-running-early event or
 *to prevent buses from forming short headways, or \bunching up" (clustering of
 *the buses within a short distance of one another). Holding can be schedule-based
 *to ensure on-time performance, or headway-based to maintain even headways
 *between consecutive buses
 *
 */
public class HoldingStrategy {

	private int holdingTime = 0;
	
	public HoldingStrategy()
	{
		
	}
	
	public int getX()
	{
		return this.holdingTime;
	}
	
	public void setX(int holdingTime)
	{
		this.holdingTime = holdingTime;
	}
}
