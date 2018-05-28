package uow.cmde.transim.multiobjective.pso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import uow.cmde.transim.multiobjective.strategies.ControlStrategy;

public class MOPSOUtil {

    public static int verifyDominace(Position p1,Position p2){
        double[] fitness1 = p1.getFitness();
        double[] fitness2 = p2.getFitness();
        boolean winInAllDimension = true; 
        boolean lostInAllDimension = true; 
        for(int i=0;i<fitness1.length;i++){
            if(fitness1[i] > fitness2[i]){
                winInAllDimension = false; 
                if(!lostInAllDimension){
                    return MOPSOParameters.INCOMPARABLE;
                }
            }
            if(fitness1[i] < fitness2[i]){
                lostInAllDimension = false; 
                if(!winInAllDimension){
                    return MOPSOParameters.INCOMPARABLE;
                }
            }
        }

        if(winInAllDimension && lostInAllDimension){
            return MOPSOParameters.INCOMPARABLE;
        }
        if(winInAllDimension){
            return MOPSOParameters.DOMINATES;
        }
        if(lostInAllDimension){
            return MOPSOParameters.DOMINATED;
        }

        return MOPSOParameters.INCOMPARABLE;
    }

    public static ArrayList<Position> retrivePositionsIncomparable(Particle[] swarm){
        ArrayList<Position> result = new ArrayList<Position>();
        boolean isDominated[] = new boolean[swarm.length];
        int dominance = 0;
        for(int i=0;i < swarm.length ;i++){
            
            for(int j=0;j<swarm.length  && !isDominated[i];j++){
               if(i != j && !isDominated[j]){
                    dominance =
                          verifyDominace(swarm[i].getPosition(), swarm[j].getPosition());
                   if(dominance == MOPSOParameters.DOMINATES){
                        isDominated[j] = true;
                   }
                   if(dominance == MOPSOParameters.DOMINATED){
                       isDominated[i] = true;
                   }
               }
            }
        }

        for(int i=0;i<swarm.length;i++){
            if(!isDominated[i]){
                result.add(swarm[i].getPosition().clone());
            }
        }
        return result;
    }
    
    /*
    public static void calculateCrowdingDistance(ArrayList<Position> externalArchive){
        if(externalArchive.size() == 0) return;

        double[] crowdingDistance = null;
        ArrayList[] values = new ArrayList[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        crowdingDistance = new double[externalArchive.size()];

        for(int i=0;i< MOPSOParameters.NUMBER_OF_OBJECTIVES ;i++){
            values[i] = new ArrayList();
            for(int j=0;j<externalArchive.size();j++){
                Position p = externalArchive.get(j);
                values[i].add(p.getFitness()[i]);
            }
            Collections.sort(values[i]);
            Double min = (Double) values[i].get(0);
            Double max = (Double) values[i].get(values[i].size()-1);
            double deltaMax = Math.abs(max.doubleValue() - min.doubleValue());

            for(int j=1;j<externalArchive.size()-1 && deltaMax != 0.0 ;j++){
                double x0 = ((Double)values[i].get(j-1)).doubleValue();
                double x1 = ((Double)values[i].get(j)).doubleValue();
                double x2 = ((Double)values[i].get(j+1)).doubleValue();
                double delta1 = Math.abs(x1-x0);
                double delta2 = Math.abs(x2-x1);
                crowdingDistance[j] += (delta1 + delta2)/deltaMax;
            }

            if(externalArchive.size() >= 2 && deltaMax != 0.0){
                int size = externalArchive.size();
                double x0 = ((Double)values[i].get(1)).doubleValue();
                double x1 = ((Double)values[i].get(0)).doubleValue();
                double x2 = ((Double)values[i].get(size-1)).doubleValue();
                double delta1 = Math.abs(x1-x0);
                double delta2 = Math.abs(x2-x1);
                crowdingDistance[0] += (delta1 + delta2)/deltaMax;

                 x0 = ((Double)values[i].get(0)).doubleValue();
                 x1 = ((Double)values[i].get(size-1)).doubleValue();
                 x2 = ((Double)values[i].get(size-2)).doubleValue();
                 delta1 = Math.abs(x1-x0);
                 delta2 = Math.abs(x2-x1);
                 crowdingDistance[size-1] += (delta1 + delta2)/deltaMax;
            }

        }

        for(int i=0; i<externalArchive.size();i++){
            externalArchive.get(i).setCrowdingDistance(crowdingDistance[i]);
        }

    }
    
    public static void normilizeVector(double[] vector,double base){
        for(int i=0;i<vector.length;i++){
            vector[i] = vector[i]/base;
        }
    }

    public static void orderByCrowdingDistance(ArrayList<Position> externalArchive){
        Collections.sort(externalArchive,Collections.reverseOrder());
    }
     */
    
    /*
    public static void turbulence(Particle[] swarm, int currentIteration){
        Random r = new Random();
        Random random = new Random();
        for(int i =0; i < swarm.length; i++){
            double value =
                Math.pow((1.0 - ( ((double)currentIteration) / MOPSOParameters.TOT_ITERATIONS)), (5.0 / Constants.MUTATION_RATE));
            if(flip(value)){
                int dim = r.nextInt(MOPSOParameters.NUMBER_OF_DIMENSIONS);
                double mutRange = MOPSOParameters.DELTA*value;
                double lb = swarm[i].getPosition().getPosition()[dim] - mutRange;
                double ub = swarm[i].getPosition().getPosition()[dim] + mutRange;
                if(lb < Parameters.LOWER_LIMIT){
                    lb = Parameters.LOWER_LIMIT;
                }
                if(ub > Parameters.UPPER_LIMIT){
                    ub = Parameters.UPPER_LIMIT;
                }

                swarm[i].getPosition().getPosition()[dim] = lb + (ub-lb) * random.nextDouble();
            }
        }

    }

    private static boolean flip(double value) {
        if (Math.random() <= value) {
            return true;
        }

        return false;
    }

*/

    public static double[] maxValuesFitnessExternArchive(ArrayList<Position> externalArchive ){
        double[] maxValues = new double[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        for(int i=0;i<maxValues.length;i++){
            double max = 0.0; 
            for(int j=0;j<externalArchive.size();j++){
                max = Math.max(max, externalArchive.get(j).getFitness()[i]);
            }
            maxValues[i] = max;
        }
        return maxValues;
    }

    public static double[] minValuesFitnessExternArchive(ArrayList<Position> externalArchive ){
        double[] minValues = new double[MOPSOParameters.NUMBER_OF_OBJECTIVES];
        for(int i=0;i<minValues.length;i++){
            double min = Double.MAX_VALUE; 
            for(int j=0;j<externalArchive.size();j++){
                min = Math.min(min, externalArchive.get(j).getFitness()[i]);
            }
            minValues[i] = min;
        }
        return minValues;
    }

    public static MOPSOHipercube[] createCubes(){
    	MOPSOHipercube[] cubes;
        int nCubes = (int)Math.pow(MOPSOParameters.NUM_OF_GRIDS, MOPSOParameters.NUMBER_OF_OBJECTIVES);
        cubes = new MOPSOHipercube[nCubes];
        double[] line;
        for(int i=0;i<cubes.length;i++){
            line = MOPSOUtil.convertBase(i, MOPSOParameters.NUM_OF_GRIDS, MOPSOParameters.NUMBER_OF_OBJECTIVES);
            cubes[i] = new MOPSOHipercube(line);
        }
        return cubes;
    }

    public static Position selectPbest(Particle particle){
        int dominance;
        dominance = MOPSOUtil.verifyDominace(particle.getPosition(), particle.getPbest());
        if (dominance == MOPSOParameters.DOMINATES) {
            return particle.getPosition().clone();
        } else if (dominance == MOPSOParameters.INCOMPARABLE) {
            if (Math.random() > 0.5) {
                  return particle.getPosition().clone();
            }
        }
        return particle.getPbest();   
    }

    public static ArrayList<Position>  updateExternalArchive(ArrayList<Position> externalArchive,Particle[] swarm) {
        ArrayList<Position> result = MOPSOUtil.retrivePositionsIncomparable(swarm);
        result.addAll(externalArchive);
        if(externalArchive.size() > 0){
        	result  = MOPSOUtil.retrivePositionsIncomparable(result);
        }
        return result;
   }
   
    public static ArrayList<Position> retrivePositionsIncomparable(ArrayList<Position> archive){
    	//System.out.println("size:" + archive.size() +"@");
        ArrayList<Position> result = new ArrayList<Position>();
        boolean isDominated[] = new boolean[archive.size()];
        int dominance = 0;
        for(int i=0;i < archive.size() ;i++){
            
            for(int j=0;j<archive.size() && !isDominated[i];j++){
               if(i != j && !isDominated[j]){
                    dominance =
                          verifyDominace(archive.get(i), archive.get(j));
                   if(dominance == MOPSOParameters.DOMINATES){
                        isDominated[j] = true;
                   }
                   if(dominance == MOPSOParameters.DOMINATED){
                       isDominated[i] = true;
                   }
               
               }

            }
            
        }

        for(int i=0;i<archive.size() ;i++){
            if(!isDominated[i]){
                result.add(archive.get(i).clone());
            }
        }
        return result;
    }

   
    
    public static void trunkExternalArchive(MOPSOGrid grid, ArrayList<Position> externalArchive) {
           while(externalArchive.size() > MOPSOParameters.EXTERNAL_ARCHIVE_SIZE){
               Position positon =  grid.selectLider();
               externalArchive.remove(positon);
           }
    }
    
    public static double[] convertBase(int d,int base,int wide){
        double[] value = new double[wide];
        ArrayList result = new ArrayList();
        int i =0;
        while(d != 0){
            result.add(i, d % base);
            d = d/base;
        }
        int size = result.size();
        for(i =0; i < size; i++){
            value[i] = ((Integer)result.get(size-1-i)).intValue();
        }
        
       // System.out.println("size:" + size + "-- value:" + value[0] + " = " + value[1]);
        
        return value;
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
    
    public static String partiallyCrossOver(String position1, String position2, int index, int length)
    {
    	
    	String crossoverArea = position2.substring(index, index + length);
    	int leftOverLength = position1.length() - crossoverArea.length();
    	
    	//String newPosition = crossoverArea + position1.substring(length);
    	//String newPosition = crossoverArea + position1;
    	
    	//System.out.println("crossover:" + crossoverArea);
    	//System.out.println("position1:" + position1);
    	
    	for(int i=0;i<crossoverArea.length();i++)
    	{
    		position1 = position1.replaceFirst( "" + crossoverArea.charAt(i), "");
    		if(position1.length() == leftOverLength) break;
    	}
    	
    	if(position1.length() > leftOverLength)
    	{
    		position1 = position1.substring(0,leftOverLength);
    	}
    	
    	String newPosition = crossoverArea + position1;
    	
    	return newPosition;
    	
    }
    
    public static void main(String[] args)
    {
    	 int i1 = 0, i2=0, l1=0, l2=0;
    	
    	 String p = "235";
    	 String gbest = "143";
    	 String pbest = "052";
    	 
    	 int length = p.length();
         
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
        
         //System.out.println("i=" + i1 + "==" + i2);
         //System.out.println("l=" + l1 + "==" + l2);
         
         
         p = MOPSOUtil.partiallyCrossOver(p, pbest, i1, l1);
         p = MOPSOUtil.partiallyCrossOver(p, gbest, i2, l2); 
         
         //System.out.println("p:" + p);
         
    }
    

}
