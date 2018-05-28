package uow.cmde.transim.transit.model;


/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public interface IShape {
	
	public double getLat();
	
	public void setLat(double shapePtLat);
	
	public double getLon();
	
	public void setLon(double shapePtLon);
	
	public void setAsStop(boolean isStop);
	
	public boolean isStop();
	
	public void setShapePointSequence(int shapePtSequence);
	
	public int getShapePointSequence();
	
	public double getStopDistanceByMeter();
	
	public void setStopDistanceByKM(double distanceToNextStop);
	
}
