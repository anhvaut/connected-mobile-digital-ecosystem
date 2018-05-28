package uow.cmde.transim.multiobjective.controller;

import java.util.*;

import org.opt4j.optimizer.ea.*;
import org.opt4j.start.*;
import com.google.inject.*;
import org.opt4j.viewer.ViewerModule;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.Archive;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		EvolutionaryAlgorithmModule ea = new EvolutionaryAlgorithmModule ();
		ea. setGenerations (200);
		ea.setAlpha (100);
		
		ServiceReliabitlityModule serviceReliability = new ServiceReliabitlityModule();
		
	
		ViewerModule gui = new ViewerModule();
		Collection <Module > modules = new ArrayList <Module >();
		modules.add(ea);
		modules.add(serviceReliability);
		modules.add(gui);
		
		Opt4JTask task = new Opt4JTask(false);
		task.init(modules);
		
		try {
			task.execute ();
			
			/*
			Archive archive = task. getInstance(Archive.class);
			Population population = task.getInstance(Population.class);
			
			for(Individual individual: population){
				System.out.println(individual.getObjectives());
				//System.out.println(individual.getState());
				
				
			}*/
			
			/*
			for(Individual individual: archive){
				System.out.println(individual.getObjectives());
				//System.out.println(individual.getState());
				
				
			}*/
		/*
		} catch (Exception e) {
			e. printStackTrace ();
		} finally {
			task.close ();
		}
		*/

	}

}
