package uow.cmde.transim.multiobjective.pso;

import java.util.ArrayList;
import java.util.Random;



public class MOPSOGrid {

    private MOPSOHipercube[] cubes;
    private double[] maxValues;
    private double[] minValues;
    private double[] ranges;
    private int totPositions;

    public MOPSOGrid (MOPSOHipercube[] cubes) {
        this.cubes = cubes;
        this.ranges = new double[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        this.maxValues = new double[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        this.minValues = new double[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        this.totPositions = 0;
    }

    public MOPSOHipercube[] getHupercubes() {
        return this.cubes;
    }

    public void updateGrid(ArrayList<Position> externArchive) {
        this.totPositions = externArchive.size();
        this.maxValues = MOPSOUtil.maxValuesFitnessExternArchive(externArchive);
        this.minValues = MOPSOUtil.minValuesFitnessExternArchive(externArchive);
        
        //System.out.println(this.maxValues + " @ " + this.minValues);
        
        for(int i=0;i<this.ranges.length;i++){
            this.ranges[i] = this.maxValues[i] = this.maxValues[i];
        }
        for(int i=0;i<cubes.length;i++){
            cubes[i].update(ranges);
        }
        
        for(int i=0;i<cubes.length;i++){
            this.cubes[i].locatePositions(externArchive);
        }
        
        for(int i=0;i<cubes.length;i++){
            this.cubes[i].calculateFitnessDiverty();
        }

    }

    public Position selectLider(){
        if(totPositions == 0) return null;      
        double sum = 0.0;
        for(int i=0;i<this.cubes.length ;i++){
        	
             if(cubes[i].getFitnessDiverty() != 0){
               sum += cubes[i].getFitnessDiverty();
             }
        }
        //System.out.println("Sum:" + sum + " --length:" + this.cubes.length);
        double rouletteValue;
        double lowerValue = 0.0;
        double upperValue = 0.0;
        rouletteValue = Math.random() * sum;
        MOPSOHipercube cubeSelected = null;

        
        for(int i=0;i<cubes.length;i++){
            if(cubes[i].getFitnessDiverty() != 0.0){
                upperValue += cubes[i].getFitnessDiverty();
                if( rouletteValue >= lowerValue && rouletteValue < upperValue){
                    cubeSelected = cubes[i];
                    
                   // System.out.println("selected:" + i );
                    break;
                }
                lowerValue = upperValue;
            }
        }
        
        if (cubeSelected == null)
        {
        	cubeSelected = cubes[0];
        }
        
       // System.out.println(cubes[0].getPositions().size());
        		
        Position leader;
        Random r = new Random();
        int intRadom = r.nextInt(cubeSelected.getPositions().size());
        leader = cubeSelected.getPositions().get(intRadom);
        return leader;
    }
}
