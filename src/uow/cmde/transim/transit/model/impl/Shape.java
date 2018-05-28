package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class Shape implements IShape{
	/**
	 * contains an ID that uniquely identifies a shape
	 */
	int shapeId;
	
	double shapePtLat;
	double shapePtLon;
	
	/**
	 * associates the latitude and longitude of a shape point with its sequence order along the shape
	 */
	int shapePtSequence;
	
	/**
	 * positions a shape point as a distance traveled along a shape from the first shape point
	 */
	int shapeDistTraveled;
	
	/**
	 * Identify a shape point is a stop
	 */
	private boolean isStop = false;
	
	/**
	 * identify distance from this point to next stop (M)
	 */
	private double distanceToNextStop = 0;
	
	
	public double getLat()
	{
		return shapePtLat;
	}
	
	public void setLat(double shapePtLat)
	{
		this.shapePtLat = shapePtLat;
	}
	
	public double getLon()
	{
		return shapePtLon;
	}
	
	public void setLon(double shapePtLon)
	{
		this.shapePtLon = shapePtLon;
	}
	
	public void setAsStop(boolean isStop)
	{
		this.isStop = isStop;
	}
	
	public boolean isStop()
	{
		return this.isStop;
	}
	
	public void setShapePointSequence(int shapePtSequence)
	{
		this.shapePtSequence = shapePtSequence;
	}
	
	public int getShapePointSequence()
	{
		return this.shapePtSequence;
	}
	
	public double getStopDistanceByMeter()
	{
		return this.distanceToNextStop * 1000;
	}
	
	public void setStopDistanceByKM(double distanceToNextStop)
	{
		this.distanceToNextStop = distanceToNextStop;
	}
}
