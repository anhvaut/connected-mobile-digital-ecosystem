package uow.cmde.transim.multiobjective.pso;

public class Particle {

    private final Position position;
   // private final double[] velocity;
    private Position gbest;
    private Position pbest;
    

    public Position getGbest() {
        return gbest;
    }

    public void setGbest(Position gbest) {
        this.gbest = gbest;
    }


    public Particle(int index) {
        this.position = new Position();
       // this.velocity = new double[MOPSOParameters.NUMBER_OF_DIMENSIONS];
         this.pbest = position.clone();
    }
    
    public Particle(int index, double[] fitness) {
        this.position = new Position(fitness);
    }


    public void setPbest(Position pbest) {
        this.pbest = pbest;
    }

    /*
    public double[] getVelocity() {
        return velocity;
    }*/

    
    public Position getPosition() {
        return this.position;
    }

    public Position getPbest() {
        return pbest;
    }

   

   
}
