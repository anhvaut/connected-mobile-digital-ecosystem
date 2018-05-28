package uow.cmde.transim.transit.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Vu The Tran
 * @since 08/01/2012
 */
public interface IVehicles {

	
	public void addVehicle(IVehicle vehicle);
	
	public void removeVehicle(IVehicle vehicle);
	
	public void removeVehicle(int i);
	
	public List<IVehicle> getAllVehicles();
	
	public int count();
	
	
}
