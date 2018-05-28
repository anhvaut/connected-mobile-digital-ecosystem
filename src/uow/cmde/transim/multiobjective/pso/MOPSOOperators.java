package uow.cmde.transim.multiobjective.pso;

import java.util.Random;

public class MOPSOOperators {

    public static Particle[] createSwarm(){
        Particle[] swarm = new Particle[MOPSOParameters.SIZE_OF_SWARM];
        for(int i=0;i<swarm.length;i++){
            swarm[i] = new Particle(i);
        }
        return swarm;
    }

    public static MOPSOGrid createGrid(){
        MOPSOHipercube[] cubes = MOPSOUtil.createCubes();
        return new MOPSOGrid(cubes);
    }

    public static void updateParticlePosition(Particle particle){
    	
    	int i1 = 0, i2=0, l1=0, l2=0;
    	
        String position = particle.getPosition().getPosition();
        String pbest = particle.getPbest().getPosition();
        String gbest = particle.getGbest().getPosition();
        
      
        int length = position.length();
        
        Random r = new Random();
        
        //int randomNumber = length > 1? length - 1 : 1;
        
        i1 = r.nextInt(length);
        i2 = r.nextInt(length);
        if(i1<0) i1 = 0;
        if(i2<0) i2 = 0;
        
        l1 = r.nextInt(length - i1);
        l2 = r.nextInt(length - i2);
        
        if(l1<=0) l1 = 1;
        if(l2<=0) l2 = 1;
        
        
        position = MOPSOUtil.partiallyCrossOver(position, pbest, i1, l1);
        position = MOPSOUtil.partiallyCrossOver(position, gbest, i2, l2); 
        
        particle.getPosition().setPosition(position);
        
    }
    
    public static void evaluateParticle(Particle particle){
        String position = particle.getPosition().getPosition();
        double[] fitness = particle.getPosition().getFitness(); 
        
        for(int i=0; i < MOPSOParameters.NUMBER_OF_OBJECTIVES; i++){
            fitness[i] = MOPSOParameters.problem.getFunctions().get(i).evaluate(position);
        }
    }
}
