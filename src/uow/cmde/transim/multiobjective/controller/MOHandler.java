package uow.cmde.transim.multiobjective.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvWriter;

import uow.cmde.transim.multiobjective.pso.MOPSOConstants;
import uow.cmde.transim.multiobjective.strategies.ControlStrategy;
import uow.cmde.transim.util.*;

public class MOHandler {

	
	 public static void writeSolutionToFile(String generation, String population)
	  {
	    	String outputFile = AppConfig.OUTPUT_PATH + "/" + AppConfig.MO_OUTPUT_FILE; 
			
			boolean alreadyExists = new File(outputFile).exists();
				
			try {
				
				CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
				if (!alreadyExists)
				{
					csvOutput.write("generation");
					csvOutput.write("population");
					csvOutput.endRecord();
				}
				
				csvOutput.write("" + generation);
				csvOutput.write("" + population);
				csvOutput.endRecord();
				 
				csvOutput.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	   }
	
	 public static List<ControlStrategy>  getControlStrategy(String position)
		{
			List<ControlStrategy> controlStrategies = new ArrayList<ControlStrategy>();
			
			for(int i=0;i<position.length(); i++)
			{
				ControlStrategy controlStrategy = new ControlStrategy();
				
				String action = String.valueOf(position.charAt(i));
				
				//System.out.println("action:" + action);
				
				if(action.equals(MOPSOConstants.NOACTION))
				{
					
				}
				else if(action.equals(MOPSOConstants.HOLDING_30))
				{
					controlStrategy.getHoldingStrategy().setX(30);
				}
				else if(action.equals(MOPSOConstants.HOLDING_60))
				{
					controlStrategy.getHoldingStrategy().setX(60);
				}
				else if(action.equals(MOPSOConstants.HOLDING_90))
				{
					controlStrategy.getHoldingStrategy().setX(90);
				}
				
				else if(action.equals(MOPSOConstants.DEAHEADING))
				{
					controlStrategy.getDeadheadingStrategy().setX(1);
				}
				else if(action.equals(MOPSOConstants.EXPRESSING))
				{
					controlStrategy.getExpressingStrategy().setX(1);
				}
				else if(action.equals(MOPSOConstants.SHORTTURING))
				{
					controlStrategy.getShortturningStrategy().setX(1);
				}
				
				controlStrategies.add(controlStrategy);
				
			}
			
			return controlStrategies;
		}
	  
}


