package uow.cmde.transim.multiobjective.pso;


import java.util.ArrayList;

public class MOPSOHipercube {

    private double[] line;
    private ArrayList<Position> positions;
    private double[] upperLimit;
    private double[] lowerLimit;
    private double fitness;

    public MOPSOHipercube(double[] line) {
        this.line = line;
        this.positions = new ArrayList<Position>();
        this.lowerLimit = new double[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        this.upperLimit = new double[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        this.fitness = 0.0;
    }
    public void update(double[] ranges){
        for(int i=0;i<ranges.length;i++){
            this.lowerLimit[i] = line[i] * ranges[i];
            this.upperLimit[i] = lowerLimit[i] + ranges[i];
            
            //System.out.println(this.lowerLimit[i] + " @ " + this.upperLimit[i]);
        }
        this.fitness = 0.0;
        this.positions.clear();
    }

    public void locatePositions(ArrayList<Position> externArchive){
        
        for(int i=0;i<externArchive.size();i++){
            Position p = externArchive.get(i);
            //if(this.isWithin(p.getFitness())){
                this.positions.add(p);
            //}
        }
    }
    public boolean isWithin(double[] p){
        for(int i=0; i < p.length; i++){
            if(p[i] < lowerLimit[i] || p[i] >= upperLimit[i]){
                return false;
            }
        }
        return true;
    }

    public void calculateFitnessDiverty(){
        double numPositions = (double)this.positions.size();
        if(numPositions > 0){
        this.fitness = MOPSOParameters.X/numPositions;
        }
    }

    public double getFitnessDiverty(){
        return this.fitness;
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }
}
