package uow.cmde.transim.transit.strategies;

import uow.cmde.transim.util.TimeConverter;
import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.transit.model.impl.*;
import uow.cmde.transim.util.*;

public class HoldingHandler {


	private String actualDepartureTimeOfPredecessorVehicle;
	private String forecastDepartureTime;
	private TransitParameters transitParameters;
	
	public HoldingHandler(TransitParameters transitParameters)
	{
		this.transitParameters = transitParameters;
	}
	
	public void setActualArrivalTimeOfPredecessorVehicle(String actualDepartureTimeOfPredecessorVehicle)
	{
		this.actualDepartureTimeOfPredecessorVehicle = actualDepartureTimeOfPredecessorVehicle;
	}
	
	public void setForecastDepartureTime(String currentTime)
	{
		this.forecastDepartureTime = currentTime;
	}
	
	public String oneHeadwayBasedHolding()
	{
		BayesianHoldingHandler bayesianHoldingHandler = new BayesianHoldingHandler(transitParameters);
		String departureTimeOfControlBus ="";
		
		TransitParameters transitParameters = new TransitParameters();
		int forecastDeparturetimeInSecond = TimeConverter.convertTimeToSecond(this.forecastDepartureTime);
		int actualDepartureTimeOfPredecessorVehicleInSecond = TimeConverter.convertTimeToSecond(actualDepartureTimeOfPredecessorVehicle);
		
		//Holding policy
		if(forecastDeparturetimeInSecond < actualDepartureTimeOfPredecessorVehicleInSecond + AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_SECOND)
		{
			departureTimeOfControlBus = TimeConverter.convertSecondToTime(TimeConverter.convertTimeToSecond(actualDepartureTimeOfPredecessorVehicle) + AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_SECOND);
		}
		else
		{
			departureTimeOfControlBus = this.forecastDepartureTime;
		}
		
		//Apply Bayesian based decision making
		int headwayNoAction = Math.abs(forecastDeparturetimeInSecond - actualDepartureTimeOfPredecessorVehicleInSecond);
		int headwayHolding = Math.abs(TimeConverter.convertTimeToSecond(departureTimeOfControlBus) - actualDepartureTimeOfPredecessorVehicleInSecond);
		
		
		//System.out.println(departureTimeOfControlBus + "@"  + forecastDepartureTime);
		if(bayesianHoldingHandler.chooseAction(headwayNoAction,headwayHolding))
		{
			return departureTimeOfControlBus;
		}
		
		return departureTimeOfControlBus;
	}
	
	
	public boolean allStopControlPoint()
	{
		return true;
	}
	
	public boolean twoStopControlPoint(int stopIndex)
	{
		return (stopIndex ==0 || stopIndex ==5);
		
	}
	
	public boolean oneStopControlPoint(int stopIndex)
	{
		return (stopIndex ==0);
	}
	
	
}
