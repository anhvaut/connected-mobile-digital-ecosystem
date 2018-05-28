package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

public class Distance implements IDistance{
	
	private double distance;
	private IShape shape;
	
	public Distance(){}
	
	public Distance(double distance, IShape shape)
	{
		this.distance = distance;
		this.shape = shape;
	}
	
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public void setShape(IShape shape)
	{
		this.shape = shape;
	}
	
	public IShape getShape()
	{
		return this.shape;
	}
}
