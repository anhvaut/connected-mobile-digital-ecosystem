package uow.cmde.transim.transit.model;



/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public interface IRoute {
	
	public IStops getSequenceStops();
	
	public void setSequenceStops(IStops sequenceStops);
	
	public void addAStopInSequence(IStop aStop);
	
	public void removeAStopInSequence(IStop aStop);
	
	public void updateStop(int i,IStop aStop);
	
	public int getAgencyId();
	
	public void setAgencyId(int agencyId);
	
	public int getRouteId();
	
	public void setRouteId(int routeId);
	
	public int getRouteType();
	
	public void setRouteType(int routeType);
	
	public String getRouteShortName();
	
	public void setRouteShortName(String routeShortName);
	
	public String getRouteLongName();
	
	public void setRouteLongName(String routeLongName);
	
	public String getRouteDesc();
	
	public void setRouteDesc(String routeDesc);
	
	public String getRouteUrl();
	
	public void setRouteUrl(String routeUrl);
	
	public String getRouteColor();
	
	public void setRouteColor(String routeColor);
	
	public String getRouteTextColor();
	
	public void setRouteTextColor(String routeTextColor);
	
	public void setShapes(IShapes shapes);
	
	
	public IShapes getShapes();
	
	public int getNumberOfStop();
}
