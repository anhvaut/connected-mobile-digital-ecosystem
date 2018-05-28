package uow.cmde.transim.multiobjective.pso;

import uow.cmde.transim.multiobjective.functions.*;


public class MOPSOParameters {

    public static Problem problem;
    //public static  double DELTA = 8;
    //public static  double LOWER_LIMIT = 8;
    //public static  double UPPER_LIMIT = 8;
    public static  int NUMBER_OF_DIMENSIONS = 3;
    public static  int NUMBER_OF_OBJECTIVES = 2;
    
    public static final int DOMINATED = -1;
    public static final int INCOMPARABLE = 0;
    public static final int DOMINATES = 1;


    public static final int SIZE_OF_SWARM = 30;
    public static final int TOT_ITERATIONS =30;
    public static final double MUTATION_RATE = 0.5;
    public static final int NUM_OF_GRIDS = 20;
    public static final int EXTERNAL_ARCHIVE_SIZE = 100;
    public static final double X = 10.0;


    public static final double C1 = 1.0;
    public static final double C2 = 1.0;
    public static final double W = 0.4;
}
