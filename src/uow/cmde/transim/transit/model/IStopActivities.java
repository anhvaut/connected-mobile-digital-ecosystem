package uow.cmde.transim.transit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Vu The Tran
 * @since 21/12/2011
 */
public interface IStopActivities {
	
	public void addStopActivity(IStopActivity stopActivity);
	public int count();
	
	public List<IStopActivity> getAllStopActivities();
	
	
}
