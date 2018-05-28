package uow.cmde.transim.multiobjective.functions;

import java.util.List;

import uow.cmde.transim.multiobjective.controller.MOHandler;
import uow.cmde.transim.multiobjective.strategies.ControlStrategy;
import uow.cmde.transim.transit.controller.TransitParameters;

/**
 * 
 * @author Vu The Tran
 * @since 08/07/2011
 *
 */
public class ActionImpact_F3 implements IObjectiveFunction{


	
	public ActionImpact_F3()
	{
		
	}
	
	public double evaluate(String position)
	{
		List<ControlStrategy> controlStrategies = MOHandler.getControlStrategy(position);
		double impact = 0;
		
		int i =0;
		
		
		Object[] keys = TransitParameters.listTransitParameters.keySet().toArray();
		
		for(Object k:keys)
		{
			TransitParameters parameters = TransitParameters.listTransitParameters.get(k.toString());
			impact += calculateImpact(controlStrategies.get(i),
					parameters.getForecastNumberPassengerOnVehicle(),
					parameters.getNumberPassengerWait(),
					parameters.getForecastHeadwayInMinute());

			
			i++;
		}
		
		//System.out.println(position + " -- impact:" + impact);
		
		return impact;
	}
	
	public double calculateImpact(ControlStrategy controlStrategy, int numberPassengerOnVehicle, int numberPassengerWait, int headway)
	{
		double impact = 0;
		//int headway = runningTime*(int)(1 + controlStrategy.getPreventiveStrategy().getX()) + dwellTime;
		
		impact = numberPassengerOnVehicle * controlStrategy.getHoldingStrategy().getX();
		impact += numberPassengerWait * headway * (controlStrategy.getExpressingStrategy().getX());
		
		return impact;
		
	}
	
	
	
	
	
	
}
