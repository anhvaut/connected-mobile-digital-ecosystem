package uow.cmde.transim.multiobjective.pso;

import java.util.Random;

public class Position implements Comparable<Position>{

    //private double[] position;
	private String position;
    private double[] fitness;
    private double crowdingDistance;

    public void setFitness(double[] fitness) {
        this.fitness = fitness;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Position(double[] fitness)
    {
    	 this.fitness = fitness;
    }
    public Position() {
        //this.position =  new double[MOPSOParameters.NUMBER_OF_DIMENSIONS];
    	
    	randomPosition(MOPSOParameters.NUMBER_OF_DIMENSIONS);
        this.fitness = new double[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        /*
        for(int i=0;i<position.length;i++){
            this.position[i] = Parameters.LOWER_LIMIT + Parameters.DELTA * Math.random();         
        }
        */
        
        //System.out.println("position:" + position);
        
        for(int i=0;i<MOPSOParameters.NUMBER_OF_OBJECTIVES;i++){
            fitness[i] = MOPSOParameters.problem.getFunctions().get(i).evaluate(position);
        	//System.out.println("" + MOPSOParameters.problem.getFunctions().get(i));
        }
    }

    public double[] getFitness() {
        return this.fitness;
    }

    public Position clone(){
        Position p = new Position();
        p.fitness =  this.fitness.clone();
        p.position = this.position;
        return p;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public String getPosition() {
        return position;
    }
    
    public void randomPosition(int n)
    {
    	String st ="";
    	for(int i=0;i<n;i++)
    	{
    		Random r = new Random();
    		int k = r.nextInt(MOPSOConstants.NUMBER_OF_STRATEGIES);
    		st += "" + k;
    	}
    	
    	this.position = st;
    	
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public int compareTo(Position o) {
        if(this.crowdingDistance < o.getCrowdingDistance()){
            return -1;
        }else if(this.crowdingDistance > o.getCrowdingDistance()){
            return 1;
        }
        return 0;
    }
}
