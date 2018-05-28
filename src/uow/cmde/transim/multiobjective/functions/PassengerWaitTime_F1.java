package uow.cmde.transim.multiobjective.functions;
import java.util.List;

import uow.cmde.transim.multiobjective.strategies.ControlStrategy;
import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.multiobjective.controller.MOHandler;
import uow.cmde.transim.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 08/07/2011
 *
 */
public class PassengerWaitTime_F1 implements IObjectiveFunction{


	
	public PassengerWaitTime_F1()
	{
	}
	
	public double evaluate(String position)
	{
		List<ControlStrategy> controlStrategies = MOHandler.getControlStrategy(position);
		double waitTime = 0;
		int i =0;
		
		
		Object[] keys = TransitParameters.listTransitParameters.keySet().toArray();
		
		//System.out.println("length:" + keys.length);
		
		for(Object k:keys)
		{
		
			TransitParameters parameters = TransitParameters.listTransitParameters.get(k);
			
			waitTime +=calculateWaitTime(controlStrategies.get(i),
                    parameters.getForecastHeadwayInMinute(),
                    parameters.getNumberPassengerWait(),
                    AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_MINUTE); 
			i++;
			
			/*
			if(parameters.getNumberPassengerWait() == 0)
			{
				System.out.println("=0" + parameters.getForecastHeadwayInMinute());
				System.out.println(position + "-- wait time:" + waitTime);
			}*/
			
			//System.out.println(position + "-- wait time:" + waitTime);
		}
		
		
		//System.out.println(position + "--total waitime:" + waitTime);
		return waitTime;
		
	}
	
	
	private double calculateWaitTime(ControlStrategy controlStrategy,int headway, int numberPassengerWait, int scheduledHeadway)
	{
		
		double passengerWaitTime = 0;
		
		//double passengerWaitTime = (runningTime*(1 + controlStrategy.getPreventiveStrategy().getX()) + dwellTime) * numberPassengerWait;
		
		double newHeadway = headway + (double)controlStrategy.getHoldingStrategy().getX()/60.0;
		passengerWaitTime = AppConfig.MO_PASSENGER_WAIT_WEIGHT * (newHeadway * numberPassengerWait) + AppConfig.MO_HEADWAY_WEIGHT * (Math.pow(newHeadway - scheduledHeadway,2));
		
		return passengerWaitTime;
	}
	
}
