package uow.cmde.transim.transit.model;

import java.util.ArrayList;
import java.util.List;

import uow.cmde.transim.util.osm.MapPosition;;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public interface IShapes {

	
	
	public void addShape(IShape shape);
	
	public void addShape(double lat, double lon, boolean isStop);
	
	public IShape getShape(int index);
	
	public IShape getLastShape();
	
	public void calculateStopDistance();
	
	public void calculateDistance();
	
	public IShape getPositionFromDistance(double traveledDistance);
	
	public double getStopDistanceFromFirstStop(int stopIndex);
	
	public ArrayList<Double> getStopDistances();
	
	public List<IShape> getAllShapes();
	
	public int count();
}
