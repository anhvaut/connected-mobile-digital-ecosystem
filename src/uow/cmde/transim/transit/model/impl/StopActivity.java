package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class StopActivity implements IStopActivity{
	
	/**
	 * contains an ID that identifies a trip
	 */
	private String trip;
	
	/**
	 * date of running trip
	 */
	private String date;
	
	private String timeStamp;
	
	private String dayOfWeek;
	
	/**
	 * specifies the arrival time at a specific stop for a specific trip on a route
	 */
	private String arrivalTime;
	
	/**
	 * specifies the departure time from a specific stop for a specific trip on a route
	 */
	private String departureTime;
	
	private String lastDepartureTime;
	
	private String forecastDepartureTime;
	
	/**
	 * contains an ID that uniquely identifies a stop
	 */
	private IStop stop;
	
	/**
	 * identifies the order of the stops for a particular trip
	 */
	private int stopSequence;
	
	/**
	 * 
	 */
	private String stopHeadsign;
	
	/**
	 * contains the text that appears on a sign that identifies the trip's destination to passengers
	 */
	private int pickupType;
	
	/**
	 * indicates whether passengers are picked up at a stop as part of the normal schedule or whether a pickup at the stop is not available
	 */
	private int dropOffType;
	
	/**
	 * indicates whether passengers are dropped off at a stop as part of the normal schedule or whether a drop off at the stop is not available
	 */
	private double shapeDistTraveled;
	
	/**
	 * counts number of passengers aboarding at bus stop
	 */
	private int numberPassengerOn;
	
	/**
	 * counts number of passengers alighting at bus stop
	 */
	private int numberPassengerOff;
	
	private int numberPassengerLeftOver;
	
	private int numberPassengerOnVehicle;
	
	private String vehicleId;
	
	
	public void setTrip(String trip)
	{
		this.trip = trip;
	}
	
	public String getTrip()
	{
		return this.trip;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	public String getTimeStamp()
	{
		return this.timeStamp;
	}
	
	public void setDayOfWeek(String dayOfWeek)
	{
		this.dayOfWeek = dayOfWeek;
	}
	
	public String getDayOfWeek()
	{
		return this.dayOfWeek;
	}
	
	public void setArrivalTime(String arrivalTime)
	{
		this.arrivalTime= arrivalTime;
	}
	
	public String getArrivalTime()
	{
		return this.arrivalTime;
	}
	
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	public String getDepartureTime()
	{
		return this.departureTime;
	}
	
	public void setNumberPassengerOn(int numberPassengerOn)
	{
		this.numberPassengerOn = numberPassengerOn;
	}
	
	public int getNumberPassengerOn()
	{
		return this.numberPassengerOn;
	}
	
	public void setNumberPassengerOff(int numberPassengerOff)
	{
		this.numberPassengerOff = numberPassengerOff;
	}
	
	public int getNumberPassengerOff()
	{
		return this.numberPassengerOff;
	}
	
	public int getTotalNumberPassenger()
	{
		return (this.numberPassengerOn + this.numberPassengerOff);
	}
	
	public int getNumberPassengerLeftOver()
	{
		return this.numberPassengerLeftOver;
	}
	
	public void setNumberPassengerLeftOver(int numberPassengerLeftOver)
	{
		this.numberPassengerLeftOver = numberPassengerLeftOver;
	}
	
	public int getNumberPassengerOnVehicle()
	{
		return this.numberPassengerOnVehicle;
	}
	
	public void setNumberPassengerOnVehicle(int numberPassengerOnVehicle)
	{
		this.numberPassengerOnVehicle = numberPassengerOnVehicle;
	}
	
	public IStop getStop()
	{
		return this.stop;
	}
	
	public void setStop(IStop stop)
	{
		this.stop = stop;
	}
	
	public void setForecastDepartureTime(String forecastDepartureTime)
	{
		this.forecastDepartureTime = forecastDepartureTime;
	}
	
	public String getForecastDepartureTime()
	{
		return this.forecastDepartureTime;
	}
	
	public void setLastDepartureTime(String lastDepartureTime)
	{
		this.lastDepartureTime = lastDepartureTime;
	}
	
	public String getLastDepartureTime()
	{
		return this.lastDepartureTime;
	}
	
	public void setVehicleId(String vehicleId)
	{
		this.vehicleId = vehicleId;
	}
	
	public String getVehicleId()
	{
		return this.vehicleId;
	}
	
	
}
