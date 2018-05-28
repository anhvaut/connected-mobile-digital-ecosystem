package uow.cmde.transim.multiobjective.strategies;

/**
 * 
 * @author Vu The Tran
 * @since 08/07/2011
 *
 *
 */
public class ControlStrategy {

	private HoldingStrategy holdingStrategy;
	private DeadheadingStrategy deadheadingStrategy;
	private ExpressingStrategy expressingStrategy;
	private ShortturningStrategy shortturningStrategy;
	private PreventiveStrategy preventiveStrategy;
	
	public ControlStrategy()
	{
		holdingStrategy = new HoldingStrategy();
		deadheadingStrategy = new DeadheadingStrategy();
		expressingStrategy = new ExpressingStrategy();
		shortturningStrategy = new ShortturningStrategy();
		preventiveStrategy = new PreventiveStrategy();
	}
	
	public HoldingStrategy getHoldingStrategy()
	{
		return this.holdingStrategy;
	}
	
	public void setHoldingStrategy(HoldingStrategy holdingStrategy)
	{
		this.holdingStrategy = holdingStrategy;
	}
	
	public DeadheadingStrategy getDeadheadingStrategy()
	{
		return this.deadheadingStrategy;
	}
	
	public void setDeadheadingStrategy(DeadheadingStrategy deadheadingStrategy)
	{
		this.deadheadingStrategy = deadheadingStrategy;
	}
	
	public ExpressingStrategy getExpressingStrategy()
	{
		return this.expressingStrategy;
	}
	
	public void setExpressingStrategy(ExpressingStrategy expressingStrategy)
	{
		this.expressingStrategy = expressingStrategy;
	}
	
	public ShortturningStrategy getShortturningStrategy()
	{
		return this.shortturningStrategy;
	}
	
	public void setShortturningStrategy(ShortturningStrategy shortturningStrategy)
	{
		this.shortturningStrategy = shortturningStrategy;
	}
	
	public PreventiveStrategy getPreventiveStrategy()
	{
		return this.preventiveStrategy;
	}
	
	public void setPreventiveStrategy(PreventiveStrategy preventiveStrategy)
	{
		this.preventiveStrategy = preventiveStrategy;
	}
}
