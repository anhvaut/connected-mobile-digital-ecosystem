package uow.cmde.transim.multiobjective.pso;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartPanel;

import com.csvreader.CsvWriter;


public class MOPSO {

	 private Particle[] swarm;
	    private ArrayList<Position> externalArchive;
	    private MOPSOGrid grid;
	    private MOPSOPlotManager plotManager;

	    public MOPSO() {
	        this.swarm = MOPSOOperators.createSwarm();
	        this.externalArchive = new ArrayList<Position>();
	        this.grid = MOPSOOperators.createGrid();
	        this.plotManager = new MOPSOPlotManager();
	        this.plotManager.setupPlotExternalArchive();
	        this.plotManager.setupPlotSwarm();
	        this.plotManager.setDebug(true);
	    }

	     public ChartPanel getChartPanelExternalArchive() {
	        return plotManager.getChartPanelExternalArchive();
	    }

	     public ChartPanel getChartPanelSwarm() {
	        return plotManager.getChartPanelSwarm();
	    }
	     
	    public void run(){

	        this.externalArchive = MOPSOUtil.updateExternalArchive(this.externalArchive, swarm);
	        /*
	        System.out.println("=======================");
	        if ((externalArchive.size() != 0)) {
	            for (int i = 0; i < externalArchive.size(); i++) {
	            	System.out.println(i + ":" + externalArchive.get(i).getFitness()[0] + ":" + externalArchive.get(i).getFitness()[1] + "," + externalArchive.get(i).getPosition() );
	            }
	            
	        }*/
	        
	        for(int currentIteration=0; currentIteration < MOPSOParameters.TOT_ITERATIONS; currentIteration++){
	        	/*
	            plotManager.plotSwarm(swarm, currentIteration);
	            
	            plotManager.plotExternalArchive(externalArchive,currentIteration);
	            
	            try {
	                Thread.sleep(100);
	            } catch (InterruptedException ex) {
	                //Logger.getLogger(MOPSO.class.getName()).log(Level.SEVERE, null, ex);
	            	System.out.println(ex.getMessage());
	            }*/
	        	
	        	writeSolutionToFile(currentIteration, externalArchive, swarm);
	          
	            //MOPSOUtil.turbulence(swarm, currentIteration);
	        	
	           this.grid.updateGrid(this.externalArchive);
	            for(int i=0;i<this.swarm.length;i++){
	            	
	            	//System.out.println("swarm:" + this.swarm[i].getPosition().getPosition());
	            	
	                Position leader = this.grid.selectLider();
	                this.swarm[i].setGbest(leader);
	                
	                Position pbest = MOPSOUtil.selectPbest(this.swarm[i]);
	                this.swarm[i].setPbest(pbest);
	                MOPSOOperators.updateParticlePosition(this.swarm[i]);
	                MOPSOOperators.evaluateParticle(this.swarm[i]);
	                
	                
	            }
	           this.externalArchive = MOPSOUtil.updateExternalArchive(this.externalArchive, swarm);
	           this.grid.updateGrid(this.externalArchive);
	           MOPSOUtil.trunkExternalArchive(grid, externalArchive);
	            
	        }
	    }
	    
	    public void writeSolutionToFile(int interation, ArrayList<Position> externalArchive, Particle[] swarm)
	    {
	    	String outputFile = "D:/transimOutput/mopso.csv";
			
			boolean alreadyExists = new File(outputFile).exists();
				
			try {
				
				CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
				String stExternalArchive = "", stSwarm = "";
				if (!alreadyExists)
				{
					csvOutput.write("interation");
					csvOutput.write("external_archive");
					csvOutput.write("swarm");
				}
				
				if ((externalArchive.size() != 0) && (externalArchive.get(0).getFitness().length >= 2)) {
			            for (int i = 0; i < externalArchive.size(); i++) {
			            	stExternalArchive += "" + externalArchive.get(i).getFitness()[0] + "@" + externalArchive.get(i).getFitness()[1] + "@" +externalArchive.get(i).getPosition() + ";";
			            }
			            
			    }
				
				if ((swarm.length != 0) && (swarm[0].getPosition().getFitness().length >= 2)) {
					
					
		            for (int i = 0; i < swarm.length; i++) {
		                stSwarm += swarm[i].getPosition().getFitness()[0] + "@" + swarm[i].getPosition().getFitness()[1] + "@" + swarm[i].getPosition().getPosition() + ";";
		            }
		        }
				
				csvOutput.write("" + interation);
				csvOutput.write("" + stExternalArchive);
				csvOutput.write("" + stSwarm);
				csvOutput.endRecord();
				 
				csvOutput.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

}
