package uow.cmde.transim.transit.model;

import java.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public interface IStops {


	
	/**
	 * addStop
	 * @param aStop
	 */
	public void addStop(IStop aStop);
	
	/**
	 * removeStop
	 * @param aStop
	 */
	public void removeStop(IStop aStop);
	
	/**
	 * removeStop
	 * @param i
	 */
	public void removeStop(int i);
	
	/**
	 * getStop
	 * @param i
	 * @return
	 */
	public IStop getStop(int i);
	
	/**
	 * updateStop
	 * @param i
	 * @param aStop
	 */
	public void updateStop(int i, IStop aStop);
	
	/**
	 * moveUp
	 * @param iSelectedStop
	 */
	public void moveUp(int iSelectedStop);
	
	/**
	 * moveDown
	 * @param iSelectedStop
	 */
	public void moveDown(int iSelectedStop);
	
	/**
	 * count
	 * @return
	 */
	public int count();
	
	/**
	 * getAllStops
	 * @return
	 */
	public List<IStop> getAllStops();
	
	
	
}
