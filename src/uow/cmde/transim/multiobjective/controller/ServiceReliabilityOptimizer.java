package uow.cmde.transim.multiobjective.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.opt4j.start.Opt4JTask;
import org.opt4j.viewer.ViewerModule;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.Archive;

import com.csvreader.CsvWriter;
import com.google.inject.Module;
import org.opt4j.core.Individual;

import uow.cmde.transim.multiobjective.ea.*;
import uow.cmde.transim.multiobjective.rs.*;
import uow.cmde.transim.multiobjective.sa.*;
import uow.cmde.transim.util.AppConfig;

public class ServiceReliabilityOptimizer {

	private Collection <Module > modules = new ArrayList <Module >();
	private boolean showGUI = true;
	
	public void setAlgorithm(String algorithm)
	{
		if(algorithm.equals("ea"))
		{
			MyEvolutionaryAlgorithmModule ea = new MyEvolutionaryAlgorithmModule ();
			ea. setGenerations (AppConfig.MO_TOT_GENERATIONS);
			ea.setAlpha (AppConfig.MO_TOT_INTERATION);
			modules.add(ea);
			
			//System.out.println("ea ok");
		}
		else if(algorithm.equals("rs"))
		{
			MyRandomSearchModule rs = new MyRandomSearchModule();
			rs.setIterations(AppConfig.MO_TOT_INTERATION);
			rs.setBatchsize(AppConfig.MO_BATCH_SIZE);
		}
		else if(algorithm.equals("sa"))
		{
			MySimulatedAnnealingModule sa = new MySimulatedAnnealingModule();
			sa.setIterations(AppConfig.MO_TOT_INTERATION);
		}
		
	}
	public void run()
	{
		ServiceReliabitlityModule serviceReliability = new ServiceReliabitlityModule();
			
		
		modules.add(serviceReliability);
		
		if(showGUI)
		{
			ViewerModule gui = new ViewerModule();
			modules.add(gui);
		}
		
		Opt4JTask task = new Opt4JTask(false);
		task.init(modules);
		
		try {
			task.execute ();
			
			
			Archive archive = task. getInstance(Archive.class);
			Population population = task.getInstance(Population.class);
			
			
			String st = "";
			for(Individual individual: population){
				
				if(individual.getPhenotype().toString().length()!= AppConfig.MO_SELECTED_NUMBER_BUS) break;
				
				double[] arr = (double[])individual.getObjectives().array();
				
				st += arr[0] + ";" + arr[1] + ";" + arr[2] + ",";
			
			}
			
			if(!st.equals(""))
			{
				
				//System.out.println("final generation:" + st);
				MOHandler.writeSolutionToFile("L",st);
			}
			
			
			String stArchive = "";
			for(Individual individual: archive){
				if(individual.getPhenotype().toString().length()!= AppConfig.MO_SELECTED_NUMBER_BUS) break;
				
				double[] arr = (double[])individual.getObjectives().array();
				
				stArchive += arr[0] + ";" + arr[1] + ";" + arr[2] + ",";
				
			}
			
			if(!stArchive.equals(""))
			{
				
				MOHandler.writeSolutionToFile("A",stArchive);
			}
		
		} catch (Exception e) {
			e. printStackTrace ();
		} finally {
			task.close ();
		}
		
	}
	
	public void setShowGUI(boolean showGUI)
	{
		this.showGUI = showGUI;
		
	}
}
