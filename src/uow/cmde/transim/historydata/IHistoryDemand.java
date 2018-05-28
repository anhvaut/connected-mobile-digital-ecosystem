package uow.cmde.transim.historydata;

import uow.cmde.transim.transit.model.IStopActivities;

/**
 * 
 * Passenger demand for a date
 * @author Vu The Tran
 * @since 01/01/2012
 */
public interface IHistoryDemand {

	public IStopActivities getDemandByDay(String stopCode) throws Exception;
	public IStopActivities getDemandByTimeAndDay(String stopCode) throws Exception;
}
