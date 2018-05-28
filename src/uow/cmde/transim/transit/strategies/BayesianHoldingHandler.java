package uow.cmde.transim.transit.strategies;

import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.util.*;


public class BayesianHoldingHandler {

	private TransitParameters transitParameter;
	public BayesianHoldingHandler(TransitParameters transitParameter)
	{
		this.transitParameter = transitParameter;
	}
	
	private double calculateExpectedUtility(double headway)
	{
		
		double expectedUtility = -1;
		
		try
		{
			NormalDistribution normalDistribution = new NormalDistributionImpl();
			normalDistribution.setMean( AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_MINUTE);
			normalDistribution.setStandardDeviation(AppConfig.TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_MINUTE);
			
			double headwayAdherenceProbability = normalDistribution.cumulativeProbability(headway);
			double utility = headway / 2 + Math.abs(headway - AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_SECOND)/2*headway;
			
			expectedUtility = headwayAdherenceProbability * utility;
			
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return expectedUtility;
		
	}
	
	public boolean chooseAction(int currentHeadway, int headwayAfterHolding)
	{
		double EU_noaction = calculateExpectedUtility(currentHeadway);
		double EU_holding = calculateExpectedUtility(headwayAfterHolding);
		
		//System.out.println(EU_noaction + " @ " + EU_holding); 
		return (EU_noaction>EU_holding);
	}
	
}
