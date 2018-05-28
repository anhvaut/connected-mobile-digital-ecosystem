package org.opt4j.tutorial;

import org.opt4j.optimizer.ea.*;
import org.opt4j.optimizer.sa.*;
import org.opt4j.benchmark.zdt.*;
import org.opt4j.benchmark.dtlz.*;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.viewer.*;
import org.opt4j.config.*;
import java.util.*;
import org.opt4j.start.*;
import com.google.inject.*;
import org.opt4j.core.Individual;
import org.opt4j.start.*;
import org.opt4j.viewer.ViewerModule;
import org.opt4j.optimizer.mopso.*;
import org.opt4j.core.optimizer.Population;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EvolutionaryAlgorithmModule ea = new EvolutionaryAlgorithmModule ();
		ea. setGenerations (500);
		ea.setAlpha (100);
		
		SimulatedAnnealingModule sa = new SimulatedAnnealingModule();
		sa.setIterations(500);
		
		//MOPSOModule mopso = new MOPSOModule ();
		//mopso.setIterations(100);
		//mopso.setParticles(20);
		//mopso.setArchiveSize(200);
		
		
		//DTLZModule dtlz = new DTLZModule ();
		//dtlz.setFunction(DTLZModule.Function.DTLZ1);
		
		HelloWorldModule hlModule = new HelloWorldModule();
		SalesmanModule tsm = new SalesmanModule();
		
	
		ViewerModule gui = new ViewerModule();
		//GUIModule gui = new GUIModule();
		//gui. setCloseOnStop (true);a
		Collection <Module > modules = new ArrayList <Module >();
		//modules.add(sa);
		modules.add(ea);
		//modules.add(mopso);
		//modules.add(dtlz);
		modules.add(hlModule);
		//modules.add(tsm);
		modules.add(gui);
		Opt4JTask task = new Opt4JTask(false);
		task.init(modules);
		try {
		task.execute ();
		Archive archive = task. getInstance(Archive.class);
		
		Population population = task.getInstance(Population.class);
		
		
		/*
		for(Individual individual: population){
			System.out.println(individual.getPhenotype());
			System.out.println(individual.getObjectives());
			System.out.println(individual.getState());
			
			
		}
		
		
		for(Individual individual: archive){
			System.out.println(individual.getObjectives());
			//System.out.println(individual.getState());
			
			
		}*/
		
		} catch (Exception e) {
			e. printStackTrace ();
		} finally {
			task.close ();
		}
		
		//tsm.config();
	}

}
