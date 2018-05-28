package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Vu The Tran
 * @since 08/01/2012
 */
public class ActiveVehicles implements IActiveVehicles{

	private int numberOfVehicles = 0;
	private List<IActiveVehicle> vehicles = new ArrayList<IActiveVehicle>();
	
	public void addVehicle(IActiveVehicle vehicle)
	{
		vehicles.add(vehicle);
		numberOfVehicles ++;
	}
	
	public void removeVehicle(IActiveVehicle vehicle)
	{
		vehicles.remove(vehicle);
		numberOfVehicles --;
	}
	
	public void removeVehicle(int i)
	{
		vehicles.remove(i);
		numberOfVehicles --;
	}
	
	public List<IActiveVehicle> getAllVehicles()
	{
		return vehicles;
	}
	
	public int count()
	{
		return numberOfVehicles;
	}
	
	public int getNumberActiveVehicle()
	{
		int count  = 0;
		for(IActiveVehicle vehicle:getAllVehicles())
		{
			
			if(vehicle!=null  && vehicle.isStartRunning())
			{
				count++;
			}
		}
		
		return count;
	}
	
	
}
