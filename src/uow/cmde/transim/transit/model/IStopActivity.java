package uow.cmde.transim.transit.model;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public interface IStopActivity {
	
	
	public void setTrip(String trip);
	
	public String getTrip();
	
	public void setDate(String date);
	
	public String getDate();
	
	public void setTimeStamp(String timeStamp);
	
	public String getTimeStamp();
	
	public void setDayOfWeek(String dayOfWeek);
	
	public String getDayOfWeek();
	
	public void setArrivalTime(String arrivalTime);
	
	public String getArrivalTime();
	
	public void setDepartureTime(String departureTime);
	
	public String getDepartureTime();
	
	public void setNumberPassengerOn(int numberPassengerOn);
	
	public int getNumberPassengerOn();
	
	public void setNumberPassengerOff(int numberPassengerOff);
	
	public int getNumberPassengerOff();
	
	public int getTotalNumberPassenger();
	
	public int getNumberPassengerLeftOver();
	
	public void setNumberPassengerLeftOver(int numberPassengerLeftOver);
	
	public int getNumberPassengerOnVehicle();
	
	public void setNumberPassengerOnVehicle(int numberPassengerOnVehicle);
	
	public IStop getStop();
	
	public void setStop(IStop stop);
	
	public void setForecastDepartureTime(String forecastDepartureTime);
	
	public String getForecastDepartureTime();
	
	public void setLastDepartureTime(String lastDepartureTime);
	
	public String getLastDepartureTime();
	
	public void setVehicleId(String vehicleId);
	
	public String getVehicleId();
	
	
}
