package uow.cmde.transim.util;

public class Point2D {
	
	private double x;
	private double y;
	
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D() {

	}

	public double getX() {
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	
	public double distance(Point2D pt) {
		double result = (pt.getX() - x) * (pt.getX() - x);
		result += (pt.getY() - y) * (pt.getY() - y);
		
		return Math.sqrt(result);
	}
}
