package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

/**
 * 
 * @author Vu The Tran
 * @since 08/01/2012
 */
public class Vehicle implements IVehicle{

	private int vehicleId;
	private String vehicleName;
	private String vehicleType;
	private int seatCapacity;
	private int standingCapacity;
	
	public int getVehicleId()
	{
		return vehicleId;
	}
	
	public void setVehicleId(int vehicleId)
	{
		this.vehicleId = vehicleId;
	}
	
	public String getVehicleName()
	{
		return vehicleName;
	}
	
	public void setVehicleName(String vehicleName)
	{
		this.vehicleName = vehicleName;
	}
	
	public int getSeatCapacity()
	{
		return seatCapacity;
	}
	
	public void setSeatCapacity(int seatCapacity)
	{
		this.seatCapacity = seatCapacity;
	}
	
	
	public int getStandingCapacity()
	{
		return standingCapacity;
	}
	
	public void setStandingCapacity(int standingCapacity)
	{
		this.standingCapacity = standingCapacity;
	}
	
	public int getTotalCapacity()
	{
		return this.standingCapacity + this.seatCapacity;
	}
	
}
