package uow.cmde.transim.transit.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Vu The Tran
 * @since 08/01/2012
 */
public interface IActiveVehicles {

	
	public void addVehicle(IActiveVehicle vehicle);
	
	public void removeVehicle(IActiveVehicle vehicle);
	
	public void removeVehicle(int i);
	
	public List<IActiveVehicle> getAllVehicles();
	
	public int count();
	
	public int getNumberActiveVehicle();
	
	
}
