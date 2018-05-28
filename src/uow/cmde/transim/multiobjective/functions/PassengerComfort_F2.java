package uow.cmde.transim.multiobjective.functions;

import java.util.List;

import uow.cmde.transim.multiobjective.strategies.ControlStrategy;
import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.multiobjective.controller.MOHandler;

/**
 * 
 * @author Vu The Tran
 * @since 08/07/2011
 * 
 	Level of service:
		     *      Can sit next to unoccupied seat: 1
			 * 		Can choose seat: 2
			 * 		Seated: 3
			 * 		Standing but not crowded: 4
			 * 		Full: 5
			 * 		Borderline of crowded and overcrowded: 6
 *
 */
public class PassengerComfort_F2 implements IObjectiveFunction{

	
	
	public PassengerComfort_F2()
	{
		
	}
	
	public double evaluate(String position)
	{
		
		List<ControlStrategy> controlStrategies = MOHandler.getControlStrategy(position);
		double comfortLevel = 0;
		int i =0;
		
		
		Object[] keys = TransitParameters.listTransitParameters.keySet().toArray();
		
		
		for(Object k:keys)
		{
		
			TransitParameters parameters = TransitParameters.listTransitParameters.get(k);
			comfortLevel += calculateComfortLevel(controlStrategies.get(i),
					parameters.getNumberPassengerOnVehicle(),
					parameters.getDwellTime(),
					parameters.getStandingCapacity(),
					parameters.getSeatedCapacity());
		
			i++;
			
		}
		
		
		return comfortLevel;
	}
	
	public int calculateComfortLevel(ControlStrategy controlStrategy, int numberPassengerOnVehicle, int dwellTime, int standingCapacity, int seatedCapacity )
	{
		
		if(numberPassengerOnVehicle!=0)
		{
			numberPassengerOnVehicle += (controlStrategy.getDeadheadingStrategy().getX() 
					                    + controlStrategy.getExpressingStrategy().getX() 
					                    + controlStrategy.getShortturningStrategy().getX())* dwellTime;
			
			if((seatedCapacity ) >= 2*numberPassengerOnVehicle) return 1;
			else if (seatedCapacity > numberPassengerOnVehicle) return 2;
			else if (seatedCapacity == numberPassengerOnVehicle) return 3;
			else if (standingCapacity > numberPassengerOnVehicle - seatedCapacity) return 4;
			else if (standingCapacity  == (numberPassengerOnVehicle - seatedCapacity)) return 5;
		}
		
		return 6;
		
	}
	
	
}
