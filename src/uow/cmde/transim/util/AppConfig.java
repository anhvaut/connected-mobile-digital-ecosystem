package uow.cmde.transim.util;

public class AppConfig {

	/***
	 * App settings
	 */
	public static String CONFIG_FILE = "config.xml";
	public static String NETWORK_FILE;
	public static String MAP_FILE;
	public static String TRANSPORT_PLAN_FILE;
	public static String PROJECT_PATH = "D:/My Research/My Project/MyTranSim/projects/uow";
	//public static String PROJECT_PATH = "C:/tools/MyTranSim/projects/uow";
	public static String OUTPUT_PATH;
	public static String OUTPUT_FILE;
	public static String CONTROL_STRATEGY_FILE;
	public static boolean OVERWRITE_OUTPUT_FILE;
	
	public static String FULL_CONFIG_FILE;
	public static String FULL_NETWORK_FILE;
	public static String FULL_MAP_FILE;
	public static String FULL_TRANSPORT_PLAN_FILE;
	public static String FULL_CONTROL_STRATEGY_FILE;
	
	
	/**
	 * Database settings
	 */
	public static String DATABASE_HOST;
	public static String DATABASE_SOURCE;
	public static String DATABASE_USERNAME;
	public static String DATABASE_PASSWORD;
	
	/**
	 * Transit parameters
	 */
	public static double TRANSIT_PASSENGER_ALIGHTING_FRACTION;
	public static double TRANSIT_DEMAND_SCALE;
	public static int TRANSIT_AVERAGE_ALIGHTING_TIME_EACH_PASSENGER = 2; 
	public static int TRANSIT_AVERAGE_BOARDING_TIME_EACH_PASSENGER = 3; 
	public static int TRANSIT_DECELERATION_TIME;  	 
	public static int TRANSIT_SCHEDULE_HEADWAY_IN_MINUTE = 15;
	public static int TRANSIT_SCHEDULE_HEADWAY_IN_SECOND = 15 * 60;
	public static int TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_MINUTE = 5;
	public static int TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_SECOND = 5 * 60;
	public static int TRANSIT_AVERAGE_SPEED_KM_PER_HOUR;
	public static  boolean TRANSIT_USING_STRATEGY = true;
	public static  boolean STATION_SCHEDULE_CONTROL = true;
	
	/**
	 * Schedule
	 */
	
	
	
	/**
	 * Multi-objective optimizing settings
	 */
	 public static  String MO_ALGORITHM = null;
	 public static  int MO_NUMBER_OF_DIMENSIONS = 0;
	 public static  int MO_NUMBER_OF_OBJECTIVES = 0;
	 public static  String MO_OBJECTIVES = null;
	 public static  int MO_TOT_GENERATIONS = 0;
	 public static  int MO_TOT_INTERATION = 0;
	 public static  int MO_BATCH_SIZE = 0;
	 
	 public static int MO_SELECTED_NUMBER_BUS = 0;
	 
	 public static double MO_PASSENGER_WAIT_WEIGHT = 0;
	 public static double MO_HEADWAY_WEIGHT = 0; 
	 public static String MO_OUTPUT_FILE = null;
	
}
