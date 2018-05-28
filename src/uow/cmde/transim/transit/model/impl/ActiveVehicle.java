package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

import java.util.Hashtable;
import java.awt.Color;

/**
 * 
 * @author Vu The Tran
 * @since 08/01/2012
 */
public class ActiveVehicle extends Vehicle implements IActiveVehicle{

	private String driverName;
	private String startTime;
	private String endTime;
	
	private int currentPassengerOn = 0;
	private int speed; //meter per second
	private int waitingStopIndex = -1;
	private int dwellTime = 0; //seconds
	private int totalTrip;
	private int currentTripNumber = 0;
	private int lastStopIndex = -1;
	private int vehicleIndex = -1;
	private int startStopIndex = -1;
	private double travelledMeter = 0;
	
	private boolean startRunning = false;
	private boolean isPaused = false;
	private boolean isWaitAtStop = false;
	private boolean backToGarage = false;
	
	
	private Color color;
	private Hashtable listDepartureTime = new Hashtable();
	private IStopActivity stopActivity;
	private IRoute  route;
	private IShape currentPosition;
	
	
	public String getDriverName()
	{
		return driverName;
	}
	
	public void setDriverName(String driverName)
	{
		this.driverName = driverName;
	}
	
	public IShape getCurrentPosition()
	{
		return this.currentPosition;
	}
	
	public void setCurrentPosition(double lat, double lon)
	{
		IShape shape = new Shape();
		shape.setLat(lat);
		shape.setLon(lon);
		this.currentPosition = shape;
	}
	
	public void setCurrentPosition(IShape currentPosition)
	{
		this.currentPosition = currentPosition;
	}
	
	public void setCurrentPassengerOn(int currentPassengerOn)
	{
		this.currentPassengerOn = currentPassengerOn;
	}
	
	public void addPassengerOn(int passengerOn)
	{
		this.currentPassengerOn += passengerOn;
	}
	public int getCurrentPassengerOn()
	{
		return this.currentPassengerOn;
	}
	
	public void setSpeedByMeterPerSecond(int speed)
	{
		this.speed = speed;
	}
	
	public void setSpeedByKmPerHour(int speed)
	{
		this.speed = speed*1000/3600;
	}
	
	public int getSpeedByMeterPerSecond()
	{
		return this.speed;
	}
	
	public void setRoute(IRoute route)
	{
		this.route = route;
		
		if(startStopIndex!=-1)
		{
			this.travelledMeter = this.route.getShapes().getStopDistanceFromFirstStop(startStopIndex) - getSpeedByMeterPerSecond();
			this.lastStopIndex = startStopIndex - 1;
		}
	}
	
	public IRoute getRoute()
	{
		return this.route;
	}
	
	public void setWaitAtStop(boolean isWait)
	{
		this.isWaitAtStop = isWait;
	}
	
	public boolean isWaitAtStop()
	{
		return this.isWaitAtStop;
	}
	
	public void setWaitingStopIndex(int waitingStopIndex)
	{
		this.waitingStopIndex = waitingStopIndex;
	}
	
	public int getWaitingStopIndex()
	{
		return this.waitingStopIndex;
	}
	
	public void setDwellTime(int dwellTime)
	{
		this.dwellTime = dwellTime;
	}
	
	public int getDwellTime()
	{
		return this.dwellTime;
	}
	
	public void addTravelledMeter(double meter)
	{
		this.travelledMeter += meter;
	}
	
	public void setTravelledMeter(double travelledMeter)
	{
		this.travelledMeter = travelledMeter;
	}
	
	public double getTravelledMeter()
	{
		return travelledMeter;
	}
	
	public void setStopActivity(IStopActivity stopActivity)
	{
		this.stopActivity = stopActivity;
	}
	
	public IStopActivity getStopActivity()
	{
		return this.stopActivity;
	}
	
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
	
	public String getStartTime()
	{
		return this.startTime;
	}
	
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	
	public void setStartRunning(boolean startRunning)
	{
		this.startRunning = startRunning;
	}
	
	public boolean isStartRunning()
	{
		return this.startRunning;
	}
	
	public String getEndTime()
	{
		return this.endTime;
	}
	
	public void setTotalTrip(int totalTrip)
	{
		this.totalTrip = totalTrip;
	}
	
	public int getTotalTrip()
	{
		return this.totalTrip;
	}
	
	public void setCurrentTripNumber(int currentTripNumber)
	{
		this.currentTripNumber = currentTripNumber;
	}
	
	public int getCurrentTripNumber()
	{
		return this.currentTripNumber;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public void setLastStopIndex(int lastStopIndex)
	{
		this.lastStopIndex = lastStopIndex;
	}
	
	public int getLastStopIndex()
	{
		return this.lastStopIndex;
	}
	
	
	public void setStartStopIndex(int startStopIndex)
	{
		this.startStopIndex = startStopIndex;
		
	}
	
	public void addDepartureTime(int stopIndex, String departureTime)
	{
		if(listDepartureTime.contains(stopIndex))
		{
			listDepartureTime.remove(stopIndex);
		}
		
		listDepartureTime.put(stopIndex,departureTime);
		
	}
	
	public String getDepartureTime(int stopIndex)
	{
		String departureTime ="";
		
		if(listDepartureTime.containsKey(stopIndex))
		{
			departureTime = listDepartureTime.get(stopIndex).toString();
			
		}
		
		return departureTime;
	}
	public void setBackToGarage()
	{
		this.startRunning = false;
		this.currentPassengerOn = 0;
		this.isWaitAtStop = false;
		this.currentTripNumber = 0;
		this.lastStopIndex = -1;
		this.isPaused = false;
		this.waitingStopIndex = -1;
		this.backToGarage = true;
		setStartStopIndex(this.startStopIndex);
		listDepartureTime.clear();
	}
	
	public boolean isBackToGarage()
	{
		return this.backToGarage;
	}
	public void setPause(boolean isPaused)
	{
		this.isPaused = isPaused;
	}
	
	public boolean isPause()
	{
		return this.isPaused;
	}
	
	public void setVehicleIndex(int vehicleIndex)
	{
		this.vehicleIndex = vehicleIndex;
	}
	
	public int getVehicleIndex()
	{
		return this.vehicleIndex;
	}
	/**
	 * Move with second in time
	 * Each second,  vehicle will run currentSpeed meter
	 */
	public void move()
	{
		this.travelledMeter += this.speed;
	}
	
	/**
	 * Check vehicle near to stop
	 */
	public int isCommingStop()
	{
		
		//int i=0;
		try
		{
			int commingStopIndex = lastStopIndex + 1;
			double distance = this.route.getShapes().getStopDistanceFromFirstStop(commingStopIndex);
			
			if((travelledMeter + getSpeedByMeterPerSecond()) >= distance )
			{
				//System.out.println((travelledMeter + getSpeedByMeterPerSecond()) + " @ " + distance);
				int commingStopSequence = commingStopIndex + 1;
				return commingStopSequence;
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("isCommingStop error:" + ex.getMessage());
		}
		
		return -1;
	}
	
	public IShape checkCurrentPosition()
	{
		return this.route.getShapes().getPositionFromDistance(travelledMeter);
	}
	
	public double getDistanceToNextStopInMeter()
	{
		int commingStopIndex = lastStopIndex + 1;
		double distance = 0;
		
		if(commingStopIndex < route.getNumberOfStop())
		{
			distance =  this.route.getShapes().getStopDistanceFromFirstStop(commingStopIndex) - travelledMeter;
		}
		
		return distance;
	}

}
