package uow.cmde.transim.bayes;

import aima.core.probability.domain.ArbitraryTokenDomain;
import aima.core.probability.domain.BooleanDomain;
import aima.core.probability.util.RandVar;

public class TransitNetworkRV {
	
	public static final RandVar VECHICLE_SPEED_RV = new RandVar("VehicleSpeed", new BooleanDomain());
	
	public static final RandVar VECHICL_POSITION_RV = new RandVar("VehiclePosition", new BooleanDomain());
	
	public static final RandVar PASSENGER_ALIGHTING_RV = new RandVar("PassengerAlighting", new BooleanDomain());
	
	public static final RandVar PASSENGER_BOARDING_RV = new RandVar("PassengerBoarding", new BooleanDomain());
	
	public static final RandVar RUNNING_TIME_RV = new RandVar("RunningTime", new BooleanDomain());
	
	public static final RandVar DWELL_TIME_RV = new RandVar("DwellTime", new BooleanDomain());
	
	public static final RandVar ACTION_IMPACT_RV = new RandVar("ActionImpact", new BooleanDomain());
	
	public static final RandVar PASSENGER_WAIT_TIME_tm1_RV = new RandVar("PassegerWaitTime_t-1", 
			new ArbitraryTokenDomain("Normal","Excessive", "Unaccepted"));
	
	public static final RandVar PASSENGER_WAIT_TIME_t_RV = new RandVar("PassegerWaitTime_t", 
			new ArbitraryTokenDomain("Normal","Excessive", "Unaccepted"));
	
	public static final RandVar HEADWAY_ADHERENCE_tm1_RV = new RandVar("HeadwayAdherence_t-1",
			new ArbitraryTokenDomain("Adherence","OffAdherence", "Bunches"));
	
	public static final RandVar HEADWAY_ADHERENCE_t_RV = new RandVar("HeadwayAdherence_t",
			new ArbitraryTokenDomain("Adherence","OffAdherence", "Bunches"));
	
	public static final RandVar PASSENGER_COMFORT_tm1_RV = new RandVar("PassengerComfort_t-1", 
			new ArbitraryTokenDomain("A", "B", "C", "D","E","F1"));
	
	public static final RandVar PASSENGER_COMFORT_t_RV = new RandVar("PassengerComfort_t", 
			new ArbitraryTokenDomain("A", "B", "C", "D","E","F1"));
}
