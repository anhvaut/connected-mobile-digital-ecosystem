package uow.cmde.transim.transit.outputanalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.transit.model.impl.Stop;
import uow.cmde.transim.transit.model.impl.StopActivities;
import uow.cmde.transim.transit.model.impl.StopActivity;
import uow.cmde.transim.util.TimeConverter;



public class PeakHour {

	public static void splitAmPmData(String filteredDate,String outputFolder, String outputFile, String amFilePath, String pmFilePath) throws Exception
	{

		StopActivities stopActivitiesAM = new StopActivities();
		StopActivities stopActivitiesPM = new StopActivities();
		
		CsvReader csvReader= new CsvReader(outputFolder + "/" + outputFile);
		csvReader.readHeaders();
		
		while (csvReader.readRecord())
		{
			StopActivity stopActivity = new StopActivity();
			
			int numberPassengerOn = Integer.parseInt(csvReader.get("passenger_on"));
			int numberPassengerOff = Integer.parseInt(csvReader.get("passenger_off"));
			int numberPassengerLeftOver = Integer.parseInt(csvReader.get("passenger_left_over"));
			int numberPassengerOnVehicle = Integer.parseInt(csvReader.get("current_passenger_on"));
			
			stopActivity.setNumberPassengerOn(numberPassengerOn);
			stopActivity.setNumberPassengerOff(numberPassengerOff);
			stopActivity.setDate(csvReader.get("date"));
			stopActivity.setDayOfWeek(csvReader.get("day_of_week"));
			stopActivity.setArrivalTime(csvReader.get("arrival_time"));
			stopActivity.setLastDepartureTime(csvReader.get("last_departure_time"));
			stopActivity.setDepartureTime(csvReader.get("departure_time"));
			stopActivity.setForecastDepartureTime(csvReader.get("forecast_departure_time"));
			stopActivity.setTrip(csvReader.get("trip_number"));
			stopActivity.setNumberPassengerLeftOver(numberPassengerLeftOver);
			stopActivity.setNumberPassengerOnVehicle(numberPassengerOnVehicle);
			stopActivity.setVehicleId(csvReader.get("vehicle"));
			
			Stop stop = new Stop();
			stop.setStopCode(csvReader.get("stop_code"));
			
			stopActivity.setStop(stop);
			
			if(filteredDate.equals(stopActivity.getDate()))
			{
				if(TimeConverter.convertTimeToSecond(stopActivity.getDepartureTime()) > TimeConverter.convertTimeToSecond("12:00:00"))
				{
					stopActivitiesPM.addStopActivity(stopActivity);
				}
				else
				{
					stopActivitiesAM.addStopActivity(stopActivity);
				}
			}
		}
		
		csvReader.close();
		
		writeStopActivitiesToFile(amFilePath,stopActivitiesAM);
		writeStopActivitiesToFile(pmFilePath,stopActivitiesPM);
	}
	
	
	private static void writeStopActivitiesToFile(String outputFile,StopActivities stopActivities)
	{
		
		boolean alreadyExists = new File(outputFile).exists();
			
		try {
			
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
			
			if(alreadyExists) (new File(outputFile)).delete();

			if (!alreadyExists)
			{
				csvOutput.write("vehicle");
				csvOutput.write("stop_code");
				csvOutput.write("date");
				csvOutput.write("day_of_week");
				csvOutput.write("arrival_time");
				csvOutput.write("departure_time");
				csvOutput.write("forecast_departure_time");
				csvOutput.write("last_departure_time");
				csvOutput.write("trip_number");
				csvOutput.write("current_passenger_on");
				csvOutput.write("passenger_on");
				csvOutput.write("passenger_off");
				csvOutput.write("passenger_left_over");
				csvOutput.endRecord();
			}
			
			for(IStopActivity stopActivity: stopActivities.getAllStopActivities())
			{
				csvOutput.write(stopActivity.getVehicleId());
				csvOutput.write(stopActivity.getStop().getStopCode());
				csvOutput.write(stopActivity.getDate());
				csvOutput.write(stopActivity.getDayOfWeek());
				csvOutput.write(stopActivity.getArrivalTime());
				csvOutput.write(stopActivity.getDepartureTime());
				csvOutput.write(stopActivity.getForecastDepartureTime());
				csvOutput.write(stopActivity.getLastDepartureTime());
				csvOutput.write(stopActivity.getTrip());
				csvOutput.write("" + stopActivity.getNumberPassengerOnVehicle());
				csvOutput.write("" + stopActivity.getNumberPassengerOn());
				csvOutput.write("" + stopActivity.getNumberPassengerOff());
				csvOutput.write("" + stopActivity.getNumberPassengerLeftOver());
				csvOutput.endRecord();
			}
			
			csvOutput.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
