package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Vu The Tran
 * @since 08/01/2012
 */
public class Vehicles implements IVehicles{

	private int numberOfVehicles = 0;
	private List<IVehicle> vehicles = new ArrayList<IVehicle>();
	
	public void addVehicle(IVehicle vehicle)
	{
		vehicles.add(vehicle);
		numberOfVehicles ++;
	}
	
	public void removeVehicle(IVehicle vehicle)
	{
		vehicles.remove(vehicle);
		numberOfVehicles --;
	}
	
	public void removeVehicle(int i)
	{
		vehicles.remove(i);
		numberOfVehicles --;
	}
	
	public List<IVehicle> getAllVehicles()
	{
		return vehicles;
	}
	
	public int count()
	{
		return numberOfVehicles;
	}
	
	
}
