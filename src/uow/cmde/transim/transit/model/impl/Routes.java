package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class Routes implements IRoutes{
	
	private int numberOfRoutes = 0;
	private List<IRoute> routes = new ArrayList<IRoute>();
	
	/**
	 * addRoute
	 * @param aRoute
	 */
	public void addRoute(IRoute aRoute)
	{
		routes.add(aRoute);
		numberOfRoutes ++;
	}
	
	/**
	 * remove Route object 
	 * @param aRoute
	 */
	public void removeRoute(IRoute aRoute)
	{
		
		if(routes.remove(aRoute))
		{
			numberOfRoutes --;
		}
	}
	
	/**
	 * removeRoute
	 * @param i
	 */
	public void removeRoute(int i)
	{
		
		if(i>0)
		{
			routes.remove(i);
			numberOfRoutes --;
		}
	}
	
	/**
	 * updateRoute
	 * @param i
	 * @param aRoute
	 */
	public void updateRoute(int i, IRoute aRoute)
	{
		routes.set(i, aRoute);
	}
	
	
	/**
	 * getRoute
	 * @param i
	 * @return
	 */
	public IRoute getRoute(int i)
	{
		return routes.get(i);
	}
	
	/**
	 * count
	 * @return
	 */
	public int count()
	{
		return numberOfRoutes;
	}
	
	/**
	 * getAllRoutes
	 * @return
	 */
	public List<IRoute> getAllRoutes()
	{
		return routes;
	}
}
