package uow.cmde.transim.multiobjective.pso;

import java.util.ArrayList;


import uow.cmde.transim.multiobjective.functions.ActionImpact_F3;
import uow.cmde.transim.multiobjective.functions.IObjectiveFunction;
import uow.cmde.transim.multiobjective.functions.PassengerWaitTime_F1;
import uow.cmde.transim.multiobjective.pso.*;
import uow.cmde.transim.multiobjective.strategies.ControlStrategy;
import uow.cmde.transim.transit.model.impl.BusNetwork;

public class ServiceReliabilityEvaluation implements Problem {
	
    private ArrayList<IObjectiveFunction> functions;
    private int nFunctions;
    private int nDimensions;
    
    public ServiceReliabilityEvaluation()
    {
    	 functions = new ArrayList<IObjectiveFunction>();

    	
         functions.add( new PassengerWaitTime_F1() );
         functions.add( new ActionImpact_F3() );
         nFunctions = functions.size();
         
         MOPSOParameters.NUMBER_OF_OBJECTIVES = 2;
         MOPSOParameters.NUMBER_OF_DIMENSIONS = 2;
         
        // MOPSOParameters.UPPER_LIMIT = this.getMaxBound(nDimensions);
        // MOPSOParameters.LOWER_LIMIT = this.getMinBound(nDimensions);
        // MOPSOParameters.DELTA = MOPSOParameters.UPPER_LIMIT - MOPSOParameters.LOWER_LIMIT;
        // MOPSOParameters.NUMBER_OF_DIMENSIONS = 3;
    	
    }
    
    public ArrayList<IObjectiveFunction> getFunctions() {
        return functions;
    }

    public int getNDimensions() {
        return nDimensions;
    }

    public int getNObjectives() {
        return nFunctions;
    }

    public double getMaxBound( int dimension ) {
        return 1.0;
    }

    public double getMinBound( int dimension ) {
        return 0.0;
    }

    public double getMaxInitValue( int dimension ) {
        return 1.0;
    }

    public double getMinInitValue( int dimension ) {
        return 0.0;
    }

    
    public double getGValue() {
        //return (( PassengerWaitTime_F1 ) functions.get(1)).getGValue();
    	
    	return 1;
    }
    
  

    public String getName() {
        return "Service reliability evaluation";
    }
}
