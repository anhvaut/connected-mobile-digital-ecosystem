package uow.cmde.transim.historydata;

import uow.cmde.transim.transit.model.IStopActivities;
import uow.cmde.transim.transit.model.impl.StopActivities;

/**
 * 
 * Passenger demand for a date
 * @author Vu The Tran
 * @since 01/01/2012
 */
public class SqlHistoryDemand implements IHistoryDemand{

	
	public IStopActivities getDemandByTimeAndDay(String stopCode) throws Exception
	{
		IStopActivities stopActivities = new StopActivities();

		StopActivityQueryer stopActivityHandler = new StopActivityQueryer();
		String sql = "select sum(number_passenger_on),sum(number_passenger_off), stop_name, date, time";
		sql +=" from manual_stop_activity";
		sql +=" where stop_code ='" + stopCode + "'";
		sql +=" group by date, time";
			
		stopActivityHandler.setIndexNumberPassengerOn(1);
		stopActivityHandler.setIndexNumberPassengerOff(2);
		stopActivityHandler.setIndexStopName(3);
		stopActivityHandler.setIndexDate(4);
		stopActivityHandler.setIndexArrivalTime(5);
			
		stopActivities = stopActivityHandler.queryStopActivity(sql);
			

		
		return stopActivities;
	}
	
	public IStopActivities getDemandByDay(String stopCode) throws Exception
	{
		IStopActivities stopActivities = new StopActivities();

		StopActivityQueryer stopActivityHandler = new StopActivityQueryer();
		String sql = "select sum(number_passenger_on),sum(number_passenger_off), stop_name, date, time";
		sql +=" from manual_stop_activity";
		sql +=" where stop_code ='" + stopCode + "'";
		sql +=" group by date";
			
		stopActivityHandler.setIndexNumberPassengerOn(1);
		stopActivityHandler.setIndexNumberPassengerOff(2);
		stopActivityHandler.setIndexStopName(3);
		stopActivityHandler.setIndexDate(4);
		stopActivityHandler.setIndexArrivalTime(5);
			
		stopActivities = stopActivityHandler.queryStopActivity(sql);
			

		return stopActivities;
	}
	
}

/*****
 
 DROP TABLE IF EXISTS `manual_stop_activity`;
CREATE TABLE IF NOT EXISTS `manual_stop_activity` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` varchar(50) DEFAULT NULL,
  `trip_id` varchar(10) DEFAULT NULL,
  `bus_type` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `stop_code` varchar(10) DEFAULT NULL,
  `stop_name` varchar(50) DEFAULT NULL,
  `number_passenger_on` varchar(6) DEFAULT NULL,
  `number_passenger_off` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30891 DEFAULT CHARSET=latin1;


 ****/

/****
 
DROP TABLE IF EXISTS `manual_bus_stop`;
CREATE TABLE IF NOT EXISTS `manual_bus_stop` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `stop_name` varchar(50) DEFAULT NULL,
  `stop_desc` varchar(50) DEFAULT NULL,
  `stop_code` smallint(50) DEFAULT NULL,
  `stop_number` smallint(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

***/
