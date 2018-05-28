package uow.cmde.transim.transit.model;


import java.awt.Color;

/**
 * 
 * @author Vu The Tran
 * @since 08/01/2012
 */
public interface IActiveVehicle extends IVehicle{

	
	
	public String getDriverName();
	
	public void setDriverName(String driverName);
	
	public IShape getCurrentPosition();
	
	public void setCurrentPosition(double lat, double lon);
	
	public void setCurrentPosition(IShape currentPosition);
	
	public void setCurrentPassengerOn(int currentPassengerOn);
	
	public void addPassengerOn(int passengerOn);
	
	public int getCurrentPassengerOn();
	
	public void setSpeedByMeterPerSecond(int speed);
	
	public void setSpeedByKmPerHour(int speed);
	
	public int getSpeedByMeterPerSecond();
	
	public void setRoute(IRoute route);
	
	public IRoute getRoute();
	
	public void setWaitAtStop(boolean isWait);
	
	public boolean isWaitAtStop();
	
	public void setWaitingStopIndex(int waitingStopIndex);
	
	public int getWaitingStopIndex();
	
	public void setDwellTime(int dwellTime);
	
	public int getDwellTime();
	
	public void addTravelledMeter(double meter);
	
	public void setTravelledMeter(double travelledMeter);
	
	public double getTravelledMeter();
	
	public void setStopActivity(IStopActivity stopActivity);
	
	public IStopActivity getStopActivity();
	
	public void setStartTime(String startTime);
	
	public String getStartTime();
	
	public void setEndTime(String endTime);
	
	public void setStartRunning(boolean startRunning);
	
	public boolean isStartRunning();
	
	public String getEndTime();
	
	public void setTotalTrip(int totalTrip);
	
	public int getTotalTrip();
	
	public void setCurrentTripNumber(int currentTripNumber);
	
	public int getCurrentTripNumber();
	
	public void setColor(Color color);
	
	public Color getColor();
	
	public void setLastStopIndex(int lastStopIndex);
	
	public int getLastStopIndex();
	
	public void setStartStopIndex(int startStopIndex);
	
	public void addDepartureTime(int stopIndex, String departureTime);
	
	public String getDepartureTime(int stopIndex);
	
	public void setBackToGarage();
	public boolean isBackToGarage();
	
	public void setPause(boolean isPaused);
	
	public boolean isPause();
	
	public void setVehicleIndex(int vehicleIndex);
	
	public int getVehicleIndex();
	
	public void move();
	
	/**
	 * Check vehicle near to stop
	 */
	public int isCommingStop();
	
	public IShape checkCurrentPosition();
	
	public double getDistanceToNextStopInMeter();
	
}
