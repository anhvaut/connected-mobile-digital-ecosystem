package uow.cmde.transim.transit.model;

public interface IDistance {
	
	public void setDistance(double distance);
	
	public double getDistance();
	
	public void setShape(IShape shape);
	
	public IShape getShape();
}
