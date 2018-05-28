package uow.cmde.transim.transit.model;

/**
 * 
 * @author Vu The Tran
 * @since 08/01/2012
 */
public interface IVehicle {

	
	
	public int getVehicleId();
	
	public void setVehicleId(int vehicleId);
	
	public String getVehicleName();
	
	public void setVehicleName(String vehicleName);
	
	public int getSeatCapacity();
	
	public void setSeatCapacity(int seatCapacity);
	
	public int getStandingCapacity();
	
	public void setStandingCapacity(int standingCapacity);
	
	public int getTotalCapacity();
	
	
}
