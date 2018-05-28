package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class Route implements IRoute {
	/**
	 * contains an ID that uniquely identifies a route
	 */
	private int routeId;
	
	
	/**
	 * Contains list of sequence stops
	 */
	private IStops sequenceStops;
	
	/**
	 * defines an agency for the specified route
	 */
	private int agencyId;
	
	/**
	 * contains the short name of a route
	 */
	private String routeShortName;
	
	/**
	 * contains the full name of a route
	 */
	private String routeLongName;
	
	/**
	 * contains a description of a route
	 */
	private String routeDesc;
	
	/**
	 * describes the type of transportation used on a route
	 */
	private int routeType;
	
	/**
	 * contains the URL of a web page about that particular route
	 */
	private String routeUrl;
	
	/**
	 * defines a color that corresponds to a route
	 */
	private String routeColor;
	
	/**
	 * specify a legible color to use for text drawn against a background of route_color
	 */
	private String routeTextColor;
	
	
	/**
	 * contains an ID that defines a shape for the routes
	 */
	private IShapes shapes;
	
	
	public Route()
	{
		sequenceStops = new Stops();
	}
	
	
	public IStops getSequenceStops()
	{
		return sequenceStops;
	}
	
	public void setSequenceStops(IStops sequenceStops)
	{
		this.sequenceStops = sequenceStops;
	}
	
	public void addAStopInSequence(IStop aStop)
	{
		sequenceStops.addStop(aStop);
	}
	
	public void removeAStopInSequence(IStop aStop)
	{
		sequenceStops.removeStop(aStop);
	}
	
	public void updateStop(int i,IStop aStop)
	{
		sequenceStops.updateStop(i,aStop);
	}
	
	public int getAgencyId()
	{
		return this.agencyId;
	}
	
	public void setAgencyId(int agencyId)
	{
		this.agencyId = agencyId;
	}
	
	public int getRouteId()
	{
		return this.routeId;
	}
	
	public void setRouteId(int routeId)
	{
		this.routeId = routeId;
	}
	
	public int getRouteType()
	{
		return this.routeType;
	}
	
	public void setRouteType(int routeType)
	{
		this.routeType = routeType;
	}
	
	public String getRouteShortName()
	{
		return this.routeShortName;
	}
	
	public void setRouteShortName(String routeShortName)
	{
		this.routeShortName = routeShortName;
	}
	
	public String getRouteLongName()
	{
		return this.routeLongName;
	}
	
	public void setRouteLongName(String routeLongName)
	{
		this.routeLongName = routeLongName;
	}
	
	public String getRouteDesc()
	{
		return this.routeDesc;
	}
	
	public void setRouteDesc(String routeDesc)
	{
		this.routeDesc = routeDesc;
	}
	
	public String getRouteUrl()
	{
		return this.routeUrl;
	}
	
	public void setRouteUrl(String routeUrl)
	{
		this.routeUrl = routeUrl;
	}
	
	public String getRouteColor()
	{
		return this.routeColor;
	}
	
	public void setRouteColor(String routeColor)
	{
		this.routeColor = routeColor;
	}
	
	public String getRouteTextColor()
	{
		return this.routeTextColor;
	}
	
	public void setRouteTextColor(String routeTextColor)
	{
		this.routeTextColor = routeTextColor;
		
	}
	
	public void setShapes(IShapes shapes)
	{
		this.shapes = shapes;
		this.shapes.calculateStopDistance();
		this.shapes.calculateDistance();
		//this.shapes.getPositionFromDistance(0);
	}
	
	public IShapes getShapes()
	{
		return shapes;
	}
	
	public int getNumberOfStop()
	{
		return this.sequenceStops.count();
	}
}
