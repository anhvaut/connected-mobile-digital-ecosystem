package uow.cmde.transim.multiobjective.strategies;

/**
 * 
 * @author Vu The Tran
 * @since 08/08/2012
 *
 Prevention strategies focus on reducing the variability of running times and dwell times
 *
 */
public class PreventiveStrategy {

	public double speedControl = 0;
	
	public PreventiveStrategy()
	{
		
	}
	
	public double getX()
	{
		return speedControl;
	}
	
	public void setX(double speedControl)
	{
		this.speedControl = speedControl;
	}
}
