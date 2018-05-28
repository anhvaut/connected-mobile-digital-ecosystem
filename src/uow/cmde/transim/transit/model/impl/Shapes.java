package uow.cmde.transim.transit.model.impl;

import java.util.ArrayList;
import java.util.List;

import uow.cmde.transim.util.osm.MapPosition;
import uow.cmde.transim.transit.model.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class Shapes implements IShapes{

	private int numberOfShapes = 0;
	private int stopSequence = 0;
	private List<IShape> shapes = new ArrayList<IShape>();
	private List<IDistance> distances = new ArrayList<IDistance>();
	private ArrayList<Double> stopDistances;
	
	public void addShape(IShape shape)
	{
		shapes.add(shape);
		numberOfShapes ++;
	}
	
	public void addShape(double lat, double lon, boolean isStop)
	{
		Shape shape = new Shape();
		shape.setLat(lat);
		shape.setLon(lon);
		shape.setAsStop(isStop);
		if(isStop)
		{
			shape.setShapePointSequence(stopSequence++);
		}
		
		shapes.add(shape);
		numberOfShapes ++;
	}
	
	public IShape getShape(int index)
	{
		return shapes.get(index);
	}
	
	public IShape getLastShape()
	{
		return shapes.get(numberOfShapes - 1);
	}
	
	public void calculateStopDistance()
	{
		double distance = 0;
		int i = 0;
		for(IShape shape:shapes)
		{
			
			if(shape.isStop())
			{
				if(distance !=0)
				{
					shape.setStopDistanceByKM(distance);
					
				}
				distance = 0;
			}
			else
			{
				if(i<numberOfShapes -1)
				{
					MapPosition position = new MapPosition((float)shape.getLat(), (float)shape.getLon());
					distance += position.getDistKM((float)shape.getLat(), (float)shape.getLon(), (float)getShape(i+1).getLat(), (float)getShape(i+1).getLon());
				}
					
			}
			
			i++;
		}
		
		stopDistances = new ArrayList<Double>();
		
		for(IShape shape:shapes)
		{
			if(shape.isStop())
			{
				stopDistances.add(shape.getStopDistanceByMeter());
			}
		}
	}
	
	public void calculateDistance()
	{
			double distance = 0;
			int i = 0;
		
		
			for(IShape shape:shapes)
			{
				if(i==0)
				{
					distances.add(new Distance(0, shape));
				}
				else if(i<numberOfShapes)
				{
					MapPosition position = new MapPosition((float)shape.getLat(), (float)shape.getLon());
					distance += position.getDistKM((float)getShape(i-1).getLat(), (float)getShape(i-1).getLon(),(float)shape.getLat(), (float)shape.getLon());
					
					//System.out.println(distance);
					distances.add(new Distance(distance*1000, shape));
					
				}
				i++;
			}
			
			
		
		
	}
	
	public IShape getPositionFromDistance(double traveledDistance)
	{
		IShape shape = null;
		for(IDistance d:distances)
		{
			if(traveledDistance>d.getDistance())
			{
				shape = d.getShape();
			}
		}
		
		
		return shape;
	}
	
	public double getStopDistanceFromFirstStop(int stopIndex)
	{
		double distance = 0;
		
		if(stopIndex == -1) return 0;
		
		for(int i=0;i<=stopIndex;i++)
		{
			distance+= stopDistances.get(i);
		}
		
		return distance;
	}
	
	public ArrayList<Double> getStopDistances()
	{
		return stopDistances;
	}
	
	public List<IShape> getAllShapes()
	{
		return shapes;
	}
	
	public int count()
	{
		return numberOfShapes;
	}
}
