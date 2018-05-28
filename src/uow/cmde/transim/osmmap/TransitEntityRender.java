package uow.cmde.transim.osmmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import uow.cmde.transim.util.ColorHandler;
import uow.cmde.transim.util.osm.MapCoordTransformer;
import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.transit.model.impl.*;
import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/01/2012
 */
public class TransitEntityRender {
	
	private IRoutes routes;
	private IActiveVehicles vehicles;
	private float displayFactor;
	private float scale;
	private boolean runStatus = false;
	
	public TransitEntityRender()
	{
		
	}
	
	/**
	 * 
	 * @param transformer
	 * @param graphics
	 */
	public void drawEntities(MapCoordTransformer transformer, Graphics2D graphics)
	{
		scale = transformer.computeScale();
		displayFactor = 3* transformer.getDotsPerUnit();
		
		drawRoutes(transformer,graphics);
		drawVehicles(transformer,graphics);
	}
	/**
	 * drawStops
	 * @param routes
	 */
	private void  drawRoutes(MapCoordTransformer transformer, Graphics2D graphics)
	{
		if(routes!=null)
		{
			for(IRoute route:routes.getAllRoutes())
			{
				Color routeColor = ColorHandler.getColor(route.getRouteColor());
				Color textColor = ColorHandler.getColor(route.getRouteTextColor());
				
				if(routeColor ==null)
				{
					routeColor = Color.RED;
				}
				
				if(textColor == null)
				{
					textColor = Color.RED;
				}
				

				
				//Draw Stop
				for(IStop stop:route.getSequenceStops().getAllStops())
				{
					int x = transformer.x(stop.getStopLon());
					int y = transformer.y(stop.getStopLat());
					
					graphics.setColor(routeColor);
					graphics.fillOval(x, y, 10, 10);
					
					//draw Stop name
					graphics.setColor(textColor);
					graphics.drawString(stop.getStopName(), x, y);
				}
				
				//Draw Shapes
				IShapes shapes= route.getShapes();
				if(shapes!=null)
				{
					int k = 0;
					int[] xPoints = new int[shapes.count()];
					int[] yPoints = new int[shapes.count()];
	
					for(IShape shape:shapes.getAllShapes())
					{
						int x = transformer.x(shape.getLon());
						int y = transformer.y(shape.getLat());
						xPoints[k] = x;
						yPoints[k] = y;
						k++;
						
					}
				
					graphics.setStroke(new BasicStroke(displayFactor));
					graphics.setColor(routeColor);
					graphics.drawPolyline(xPoints, yPoints, shapes.count());
				}
			}
		}
	}
	
	/**
	 * drawVehicles
	 * @param transformer
	 * @param graphics
	 */
	private void drawVehicles(MapCoordTransformer transformer, Graphics2D graphics)
	{
		if(vehicles !=null)
		{
			
			for(IActiveVehicle vehicle:vehicles.getAllVehicles())
			{	
				
				if(vehicle.isStartRunning())
				{
		
					vehicle.setSpeedByKmPerHour(AppConfig.TRANSIT_AVERAGE_SPEED_KM_PER_HOUR);
					
					
					int stopSequence = vehicle.isCommingStop();
					int stopIndex = stopSequence-1;
					
					if(stopSequence == vehicle.getRoute().getNumberOfStop() + 1)
					{
						vehicle.setTravelledMeter(0);
						vehicle.setCurrentTripNumber(vehicle.getCurrentTripNumber() + 1);
						vehicle.setLastStopIndex(-1);
						
					}
					
					else if(stopSequence!=-1 )//stop
					{
						//if(vehicle.getVehicleName().equals("vehicle5"))
						//{
						//	System.out.println("ok1:" + stopSequence);
						//}
						
						IStop s=vehicle.getRoute().getSequenceStops().getStop(stopIndex);
						vehicle.setCurrentPosition(s.getStopLat(),s.getStopLon());
						vehicle.setLastStopIndex(stopIndex);
						
					}
					
					if(stopSequence!=  (vehicle.getRoute().getNumberOfStop() + 1))
					{
						IShape p = vehicle.getCurrentPosition();
						//vehicle.setCurrentPosition(p.getLat(),p.getLon());
						
						int x = transformer.x(p.getLon());
						int y = transformer.y(p.getLat());
							
						graphics.setColor(vehicle.getColor());
						graphics.fillRect(x, y, 10, 10);
						
						//if(vehicle.getVehicleName().equals("vehicle5"))
						//{
						//	System.out.println(vehicle.getLastStopIndex() + " = " + vehicle.getCurrentTripNumber());
							//System.out.println("stops:" + vehicle.getRoute().getNumberOfStop());
						//	System.out.println(vehicle.isPause());
						//}
						
						if(vehicle.getCurrentTripNumber() == vehicle.getTotalTrip())
						{
							vehicle.setBackToGarage();
							
						}
						
						
						if(!vehicle.isWaitAtStop() && !vehicle.isPause())
						{
							if(runStatus)
							{
								vehicle.move();
							}
							
							IShape shape=vehicle.checkCurrentPosition();
							if(shape!=null)
							{
								vehicle.setCurrentPosition(shape);
							}
						}
						
					}
				}
					
			}
			
		}
	}
	
	public void setRoutes(IRoutes routes)
	{
		this.routes = routes;
	}
	
	public void setVehicles(IActiveVehicles vehicles)
	{
		this.vehicles = vehicles;
	}
	
	public void setRunStatus(boolean runStatus)
	{
		this.runStatus = runStatus;
	}
}
