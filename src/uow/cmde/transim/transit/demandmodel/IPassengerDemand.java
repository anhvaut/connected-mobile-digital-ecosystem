package uow.cmde.transim.transit.demandmodel;

import java.util.Random;
import org.uncommons.maths.random.PoissonGenerator;

import uow.cmde.transim.util.TimeConverter;
import uow.cmde.transim.transit.controller.TransitParameters;


/**
 * 
 * @author Vu The Tran
 * @since 01/01/2012
 */
public interface IPassengerDemand {


	public double generatePassengerOffAtStop(String dayOfWeek, String time);
	
	public double generatePassengerOnAtStopPerTimeAndDay(String dayOfWeek, String time);
	public double generatePassengerArrivalRatePerMinute(String dayOfWeek, String time);
	
	public double generatePassengerOffRatePerTimeAndDay(String dayOfWeek, String time);
	public IWeekDemand getWeekDemand();
	
	public void setWeekDemand(IWeekDemand weekDemand);
	
	
}
