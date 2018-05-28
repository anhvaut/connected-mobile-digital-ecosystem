package uow.cmde.transim.util.constants;

public class XMLNetworkConstant {
	
	/***
	 * network.xml
	 */
	public static final String NETWORK = "network";
	
	public static final String ROUTE = "route";
	public static final String ROUTE_ID = "id";
	public static final String ROUTE_AGENCY_ID = "agency_id";
	public static final String ROUTE_SHORT_NAME = "short_name";
	public static final String ROUTE_LONG_NAME = "long_name";
	public static final String ROUTE_DESC = "desc";
	public static final String ROUTE_TYPE = "type";
	public static final String ROUTE_URL = "url";
	public static final String ROUTE_COLOR = "color";
	public static final String ROUTE_TEXT_COLOR = "text_color";
	
	
	public static final String STOP = "stop";
	public static final String STOP_ID = "id";
	public static final String STOP_CODE = "code";
	public static final String STOP_NAME = "name";
	public static final String STOP_DESC = "desc";
	public static final String STOP_LAT = "lat";
	public static final String STOP_LONG = "lon";
	public static final String STOP_LOCATION_TYPE = "location_type";
	public static final String STOP_ZONE_ID = "zone_id";
	public static final String STOP_URL = "url";
	public static final String STOP_PARENT_STATION = "parent_station";
	
	public static final String SHAPE = "shape";
	public static final String SHAPE_LAT = "lat";
	public static final String SHAPE_LON = "lon";
	public static final String SHAPE_IS_STOP = "stop";
	
	public static final String STREET = "street";
	public static final String STREET_NAME = "name";
	public static final String STREET_LAT = "lat";
	public static final String STREET_LON = "lon";
	
	
	/**
	 * XML transport_plan.xml
	 */
	public static final String TRANSIT_PARAMETER = "transit_parameters";
	public static final String TRANSIT_DEMAND_SCALE = "demand_scale";
	public static final String TRANSIT_AVERAGE_ALIGHTING_TIME = "average_alighting_time";
	public static final String TRANSIT_AVERAGE_BOARDING_TIME = "average_boarding_time";
	public static final String TRANSIT_ALIGHTING_FRACTION = "aligting_fraction";
	public static final String TRANSIT_SCHEDULE_HEADWAY = "scheduled_headway";
	public static final String TRANSIT_HEADWAY_STANDARD_DEVIATION = "headway_standard_deviation";
	public static final String TRANSIT_DECELERATION_TIME = "deceleration_time";
	public static final String TRANSIT_AVERAGE_VEHICLE_SPEED = "average_vehicle_speed";
	public static final String STATION_SCHEDULE_CONTROL = "station_schedule_control";
	
	public static final String VEHICLE = "vehicle";
	public static final String VEHICLES = "vehicles";
	public static final String VEHICLE_NAME = "name";
	public static final String VEHICLE_COLOR = "color";
	public static final String VEHICLE_ROUTE_ID = "route_id";
	public static final String VEHICLE_START_STATION = "start_station";
	public static final String VEHICLE_START_TIME = "start_time";
	public static final String VEHICLE_NUMBER_TRIP = "number_trip";
	public static final String VEHICLE_SEAT_CAPACITY = "seat_capacity";
	public static final String VEHICLE_STANDING_CAPACITY = "standing_capacity";
	
	public static final String SCHEDULE = "schedule";
	public static final String TRIP = "trips";
	public static final String ID = "id";
	public static final String TIME = "times";
	
	/***
	 * config.xml
	 */
	public static final String CONFIG_NAME= "config";
	public static final String NETWORK_NAME= "network";
	public static final String MAP_NAME= "map";
	public static final String TRANSPORT_PLAN= "plan";
	public static final String FILE_ATTRIBUTE= "file";
	public static final String FOLDER_ATTRIBUTE= "folder";
	public static final String INPUT= "input";
	public static final String OUTPUT= "output";
	public static final String OUTPUT_DATA= "output_data";
	public static final String OVERWRITE_OUTPUT = "overwrite_output";
	
	/***
	 * XML Database
	 */
	public static final String HISTORICAL_DATA= "historical_data";
	public static final String DATABASE_HOST= "host";
	public static final String DATABASE_SOURCE= "data_source";
	public static final String DATABASE_USERNAME= "username";
	public static final String DATABASE_PASSWORD= "password";
	
	/**
	 * control_strategy.xml
	 */
	public static final String ALGORITHM= "algorithm";
	public static final String CONTROL_STRATEGY= "control_strategy";
	public static final String USING_STRATEGY= "using_strategy";
	public static final String SETTINGS= "settings";
	public static final String NUMBER_DIMENSIONS= "number_dimensions";
	public static final String NUMBER_OBJECTIVES= "number_objectives";
	public static final String OBJECTIVES= "objectives";
	public static final String GENERATIONS= "generations";
	public static final String INTERATION= "interation";
	public static final String BATCH_SIZE= "batch_size";
	public static final String PASSENGER_WAIT_TIME_WEIGHT= "passenger_wait_time_weight";
	public static final String HEADWAY_WEIGHT= "headway_weight";
	public static final String SELECTED_BUS= "selected_bus";

	
}
