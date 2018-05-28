package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Vu The Tran
 * @since 21/12/2011
 */
public class StopActivities implements IStopActivities{

	private int numberOfStopActivities = 0;
	private List<IStopActivity> stopActivities = new ArrayList<IStopActivity>();
	
	public void addStopActivity(IStopActivity stopActivity)
	{
		stopActivities.add(stopActivity);
		numberOfStopActivities ++;
	}
	
	public int count()
	{
		return numberOfStopActivities;
	}
	
	public List<IStopActivity> getAllStopActivities()
	{
		return stopActivities;
	}
	
	
}
