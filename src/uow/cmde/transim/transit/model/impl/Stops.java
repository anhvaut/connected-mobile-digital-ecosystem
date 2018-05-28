package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

import java.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class Stops implements IStops{

	private int numberOfStops = 0;
	private List<IStop> stops = new ArrayList<IStop>();
	
	/**
	 * addStop
	 * @param aStop
	 */
	public void addStop(IStop aStop)
	{
		stops.add(aStop);
		numberOfStops ++;
	}
	
	/**
	 * removeStop
	 * @param aStop
	 */
	public void removeStop(IStop aStop)
	{
		
		if(stops.remove(aStop))
		{
			numberOfStops --;
		}
	}
	
	/**
	 * removeStop
	 * @param i
	 */
	public void removeStop(int i)
	{
		if(i>0)
		{
			stops.remove(i);
			numberOfStops --;
		}
		
	}
	
	/**
	 * getStop
	 * @param i
	 * @return
	 */
	public IStop getStop(int i)
	{
		return stops.get(i);
		
	}
	
	/**
	 * updateStop
	 * @param i
	 * @param aStop
	 */
	public void updateStop(int i, IStop aStop)
	{
		stops.set(i, aStop);
	}
	
	/**
	 * moveUp
	 * @param iSelectedStop
	 */
	public void moveUp(int iSelectedStop)
	{
		if(iSelectedStop>0)
		{
			IStop tmpStop = stops.get(iSelectedStop-1);
			stops.set(iSelectedStop-1,stops.get(iSelectedStop));
			stops.set(iSelectedStop, tmpStop);
		}
	}
	
	/**
	 * moveDown
	 * @param iSelectedStop
	 */
	public void moveDown(int iSelectedStop)
	{
		if(iSelectedStop < numberOfStops - 1)
		{
			IStop tmpStop = stops.get(iSelectedStop+1);
			stops.set(iSelectedStop+1,stops.get(iSelectedStop));
			stops.set(iSelectedStop, tmpStop);
		}
	}
	
	/**
	 * count
	 * @return
	 */
	public int count()
	{
		return numberOfStops;
	}
	
	/**
	 * getAllStops
	 * @return
	 */
	public List<IStop> getAllStops()
	{
		return stops;
	}
	
	
	
}
