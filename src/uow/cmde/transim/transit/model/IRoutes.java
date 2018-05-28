package uow.cmde.transim.transit.model;

import java.util.List;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public interface IRoutes {
	
	
	/**
	 * addRoute
	 * @param aRoute
	 */
	public void addRoute(IRoute aRoute);
	
	/**
	 * remove Route object 
	 * @param aRoute
	 */
	public void removeRoute(IRoute aRoute);
	
	/**
	 * removeRoute
	 * @param i
	 */
	public void removeRoute(int i);
	
	/**
	 * updateRoute
	 * @param i
	 * @param aRoute
	 */
	public void updateRoute(int i, IRoute aRoute);
	
	
	/**
	 * getRoute
	 * @param i
	 * @return
	 */
	public IRoute getRoute(int i);
	
	/**
	 * count
	 * @return
	 */
	public int count();
	
	/**
	 * getAllRoutes
	 * @return
	 */
	public List<IRoute> getAllRoutes();
}
