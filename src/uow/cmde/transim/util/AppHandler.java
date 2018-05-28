package uow.cmde.transim.util;


import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.*;
import java.io.*;

import uow.cmde.transim.util.xml.*;
import uow.cmde.transim.util.constants.XMLNetworkConstant;
import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.transit.model.impl.*;
import uow.cmde.transim.transit.model.*;
/**
 * 
 * @author Vu The Tran
 * @since 14/12/2011
 */
public class AppHandler {
	
		//public static String inputFolder="C:/tools/MyTranSim/projects/uow";
		
		/**
		 * configFilePath
		 * @param configFilePath
		 */
		public static void readConfigFile(String configFilePath) throws Exception
		{
	
			    AppConfig.FULL_CONFIG_FILE = configFilePath;
				
				XMLReader xmlReader = new XMLReader(configFilePath);
				
				while (xmlReader.hasNext()) {
					xmlReader.nextEvent();
					
					StartElement startElement = null;
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.NETWORK_NAME)) != null)
					{
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.FILE_ATTRIBUTE))
							{
								AppConfig.NETWORK_FILE = attribute.getValue();
							}
						}
					
					}
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.MAP_NAME)) != null)
					{
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.FILE_ATTRIBUTE))
							{
								AppConfig.MAP_FILE = attribute.getValue();
							}
						}
					
					}
					
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.TRANSPORT_PLAN)) != null)
					{
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.FILE_ATTRIBUTE))
							{
								AppConfig.TRANSPORT_PLAN_FILE = attribute.getValue();
							}
						}
					
					}
					
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.OUTPUT)) != null)
					{
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.FOLDER_ATTRIBUTE))
							{
								AppConfig.OUTPUT_PATH = attribute.getValue();
							}
							
							if(attribute.getName().toString().equals(XMLNetworkConstant.OUTPUT_DATA))
							{
								AppConfig.OUTPUT_FILE = attribute.getValue();
							}
							
							if(attribute.getName().toString().equals(XMLNetworkConstant.OVERWRITE_OUTPUT))
							{
								if(attribute.getValue().equals("yes"))
								{
									AppConfig.OVERWRITE_OUTPUT_FILE = true;
								}
								else
								{
									AppConfig.OVERWRITE_OUTPUT_FILE = false;
								}
							}
						}
					
					}
					
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.HISTORICAL_DATA)) != null)
					{
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.DATABASE_HOST))
							{
								AppConfig.DATABASE_HOST= attribute.getValue();
							}
							
							if(attribute.getName().toString().equals(XMLNetworkConstant.DATABASE_SOURCE))
							{
								AppConfig.DATABASE_SOURCE = attribute.getValue();
							}
							
							if(attribute.getName().toString().equals(XMLNetworkConstant.DATABASE_USERNAME))
							{
								AppConfig.DATABASE_USERNAME = attribute.getValue();
							}
							
							if(attribute.getName().toString().equals(XMLNetworkConstant.DATABASE_PASSWORD))
							{
								AppConfig.DATABASE_PASSWORD = attribute.getValue();
							}
						}
					}
					
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.CONTROL_STRATEGY)) != null)
					{
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.FILE_ATTRIBUTE))
							{
								AppConfig.CONTROL_STRATEGY_FILE = attribute.getValue();
							}
						}
					
					}
				}
				
		}
		
		/**
		 * validateProjectFolder
		 * @param projectFolder
		 * @return
		 * @throws Exception
		 */
		public static boolean readConfig(String projectFolder) throws Exception
		{
			AppConfig.PROJECT_PATH = projectFolder;
			AppConfig.FULL_CONFIG_FILE = AppConfig.PROJECT_PATH + "/" + AppConfig.CONFIG_FILE;
			
			
			File f=new File(AppConfig.FULL_CONFIG_FILE);
			if(!f.isFile()) return false;
			
			readConfigFile(AppConfig.FULL_CONFIG_FILE);
			
			AppConfig.FULL_MAP_FILE = AppConfig.PROJECT_PATH + "/" + AppConfig.MAP_FILE;
			AppConfig.FULL_NETWORK_FILE = AppConfig.PROJECT_PATH + "/" + AppConfig.NETWORK_FILE;
			AppConfig.FULL_TRANSPORT_PLAN_FILE = AppConfig.PROJECT_PATH + "/" + AppConfig.TRANSPORT_PLAN_FILE;
			AppConfig.FULL_CONTROL_STRATEGY_FILE = AppConfig.PROJECT_PATH + "/" + AppConfig.CONTROL_STRATEGY_FILE;
			
			return true;
		}
		
		/**
		 * writeNetwork
		 * @param routes
		 * @param xmlFilePath
		 */
		public static void writeNetwork(IRoutes routes, String xmlFilePath)
		{
			XMLWriter xmlWriter = new XMLWriter();
			xmlWriter.setFilePath(xmlFilePath);
			xmlWriter.createStartDocument();
		
	
			xmlWriter.createStartNode(XMLNetworkConstant.NETWORK, null, 1, 0);
			
			for(IRoute route : routes.getAllRoutes())
			{
				XMLAttributeList  routeAttributes = new XMLAttributeList(xmlWriter.getEventFactory());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_ID, "" + route.getRouteId());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_AGENCY_ID, "" + route.getAgencyId());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_SHORT_NAME, "" + route.getRouteShortName());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_LONG_NAME, "" + route.getRouteLongName());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_DESC, "" + route.getRouteDesc());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_TYPE, "" + route.getRouteType());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_COLOR, "" + route.getRouteColor());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_TEXT_COLOR, "" + route.getRouteTextColor());
				routeAttributes.addAttribute(XMLNetworkConstant.ROUTE_URL, "" + route.getRouteUrl());
		
				
				xmlWriter.createStartNode(XMLNetworkConstant.ROUTE, routeAttributes, 1, 1);
				
				for(IStop stop : route.getSequenceStops().getAllStops())
				{
					XMLAttributeList  stopAttributes = new XMLAttributeList(xmlWriter.getEventFactory());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_ID, "" + stop.getStopId());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_CODE, "" + stop.getStopCode());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_NAME, "" + stop.getStopName());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_DESC, "" + stop.getStopDesc());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_LAT, "" + stop.getStopLat());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_LONG, "" + stop.getStopLon());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_LOCATION_TYPE, "" + stop.getLocationType());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_PARENT_STATION, "" + stop.getParentStation());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_URL, "" + stop.getStopUrl());
					stopAttributes.addAttribute(XMLNetworkConstant.STOP_ZONE_ID, "" + stop.getZoneId());
					
					xmlWriter.createStartNode(XMLNetworkConstant.STOP, stopAttributes, 1, 2);
					xmlWriter.createEndNode(XMLNetworkConstant.STOP, 1, 2);
				}
				
				IShapes shapes= route.getShapes();
				
				if(shapes!=null)
				{
					for(IShape shape:shapes.getAllShapes())
					{
					
						XMLAttributeList  shapeAttributes = new XMLAttributeList(xmlWriter.getEventFactory());
						shapeAttributes.addAttribute(XMLNetworkConstant.SHAPE_LAT, "" + shape.getLat());
						shapeAttributes.addAttribute(XMLNetworkConstant.SHAPE_LON, "" + shape.getLon());
						shapeAttributes.addAttribute(XMLNetworkConstant.SHAPE_IS_STOP, "" + shape.isStop());
						xmlWriter.createStartNode(XMLNetworkConstant.SHAPE, shapeAttributes, 1, 2);
						xmlWriter.createEndNode(XMLNetworkConstant.SHAPE, 1, 2);
						
					}
				}
				
				
				xmlWriter.createEndNode(XMLNetworkConstant.ROUTE, 1, 1);
			}
			
			
			xmlWriter.createEndNode(XMLNetworkConstant.NETWORK, 1, 0);
			xmlWriter.createEndDocument();
		}
		
		/**
		 * readNetwork
		 * @param filePath
		 * @return
		 */
		public static IRoutes readNetwork(String filePath) throws Exception
		{
				IRoutes routes = new Routes();
			
				
				XMLReader xmlReader = new XMLReader(filePath);
				
				
				IRoute route = null;
				IStops stops = null;
				IShapes shapes = new Shapes();
				int i = 0;
				
				while (xmlReader.hasNext()) {
					xmlReader.nextEvent();
					
					StartElement startElement = null;
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.ROUTE)) != null)
					{
						route = setRouteAttributes(startElement);
						stops = new Stops();
					
					}
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.STOP)) != null)
					{
						IStop aStop = new Stop();
						aStop = setStopAttributes(startElement);
						stops.addStop(aStop);
					
					}
					
					
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.SHAPE)) != null)
					{
						
							IShape shape = new Shape();
							shape =  setShapeAttributes(startElement);
							shapes.addShape(shape);
						
					}
					
					if(xmlReader.isEndNode(XMLNetworkConstant.ROUTE))
					{
						if(route!=null)
						{
							route.setShapes(shapes);
							route.setSequenceStops(stops);
							routes.addRoute(route);

							route = null;
							stops = null;
							shapes = new Shapes();
						}
					}
				}
			
			return routes;
		}
		
		public static TransitParameters readTransitParameter(String filePath) throws Exception
		{
				
				XMLReader xmlReader = new XMLReader(filePath);
				TransitParameters transitParameters = null;
				
				while (xmlReader.hasNext()) {
					xmlReader.nextEvent();
					
					StartElement startElement = null;
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.TRANSIT_PARAMETER)) != null)
					{
						transitParameters = new TransitParameters();
						
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.TRANSIT_ALIGHTING_FRACTION))
							{
								AppConfig.TRANSIT_PASSENGER_ALIGHTING_FRACTION = Double.parseDouble(attribute.getValue());
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.TRANSIT_AVERAGE_ALIGHTING_TIME))
							{
								AppConfig.TRANSIT_AVERAGE_ALIGHTING_TIME_EACH_PASSENGER = Integer.parseInt(attribute.getValue());
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.TRANSIT_AVERAGE_BOARDING_TIME))
							{
								AppConfig.TRANSIT_AVERAGE_BOARDING_TIME_EACH_PASSENGER = Integer.parseInt(attribute.getValue());
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.TRANSIT_DEMAND_SCALE))
							{
								AppConfig.TRANSIT_DEMAND_SCALE = Double.parseDouble(attribute.getValue());
								
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.TRANSIT_HEADWAY_STANDARD_DEVIATION))
							{
								AppConfig.TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_SECOND = Integer.parseInt(attribute.getValue());
								AppConfig.TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_MINUTE = AppConfig.TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_SECOND /60;
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.TRANSIT_SCHEDULE_HEADWAY))
							{
								AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_SECOND = Integer.parseInt(attribute.getValue());
								AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_MINUTE = AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_SECOND /60;
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.TRANSIT_DECELERATION_TIME))
							{
								AppConfig.TRANSIT_DECELERATION_TIME = Integer.parseInt(attribute.getValue());
								
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.TRANSIT_AVERAGE_VEHICLE_SPEED))
							{
								AppConfig.TRANSIT_AVERAGE_SPEED_KM_PER_HOUR = Integer.parseInt(attribute.getValue());
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.USING_STRATEGY))
							{
								if(attribute.getValue().trim().equals("yes"))
								{
									AppConfig.TRANSIT_USING_STRATEGY = true;
								}
								else
								{
									AppConfig.TRANSIT_USING_STRATEGY = false;
								}
								//TransitParameters = Integer.parseInt(attribute.getValue());
							}
							
							
							
						}
						
					
					}
					
				}
			
			return transitParameters;
		}
		
		public static TransitParameters readControlStrategy(String filePath) throws Exception
		{
				
				XMLReader xmlReader = new XMLReader(filePath);
				TransitParameters transitParameters = null;
				
				while (xmlReader.hasNext()) {
					xmlReader.nextEvent();
					
					StartElement startElement = null;
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.SETTINGS)) != null)
					{
						transitParameters = new TransitParameters();
						
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.ALGORITHM))
							{
								AppConfig.MO_ALGORITHM= attribute.getValue();
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.NUMBER_DIMENSIONS))
							{
								AppConfig.MO_NUMBER_OF_DIMENSIONS = Integer.parseInt(attribute.getValue());
							}
							
							else if(attribute.getName().toString().equals(XMLNetworkConstant.BATCH_SIZE))
							{
								AppConfig.MO_BATCH_SIZE= Integer.parseInt(attribute.getValue());
							}
							
							else if(attribute.getName().toString().equals(XMLNetworkConstant.NUMBER_OBJECTIVES))
							{
								AppConfig.MO_NUMBER_OF_OBJECTIVES= Integer.parseInt(attribute.getValue());
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.GENERATIONS))
							{
								AppConfig.MO_TOT_GENERATIONS = Integer.parseInt(attribute.getValue());
								
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.INTERATION))
							{
								AppConfig.MO_TOT_INTERATION= Integer.parseInt(attribute.getValue());
								
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.PASSENGER_WAIT_TIME_WEIGHT))
							{
								AppConfig.MO_PASSENGER_WAIT_WEIGHT = Double.parseDouble(attribute.getValue()); 
								
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.HEADWAY_WEIGHT))
							{
								AppConfig.MO_HEADWAY_WEIGHT = Double.parseDouble(attribute.getValue());
								
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.OUTPUT_DATA))
							{
								AppConfig.MO_OUTPUT_FILE = attribute.getValue();
								
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.OBJECTIVES))
							{
								AppConfig.MO_OBJECTIVES = attribute.getValue();
							}
							
							else if(attribute.getName().toString().equals(XMLNetworkConstant.SELECTED_BUS))
							{
								AppConfig.MO_SELECTED_NUMBER_BUS = Integer.parseInt(attribute.getValue());
								
							}
						}
						
					
					}
					
				}
			
			return transitParameters;
		}
		
		public static IActiveVehicles readVehicle(String filePath, IRoutes routes) throws Exception
		{
			
				XMLReader xmlReader = new XMLReader(filePath);
				IActiveVehicles vehicles = new ActiveVehicles();
				
				while (xmlReader.hasNext()) {
					xmlReader.nextEvent();
					
					StartElement startElement = null;
					if((startElement = xmlReader.isStartNode(XMLNetworkConstant.VEHICLE)) != null)
					{
						IActiveVehicle vehicle = new ActiveVehicle();
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) 
						{
							Attribute attribute = attributes.next();
							if(attribute.getName().toString().equals(XMLNetworkConstant.VEHICLE_COLOR))
							{
								vehicle.setColor(ColorHandler.getColor(attribute.getValue()));
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.VEHICLE_NAME))
							{
								vehicle.setVehicleName(attribute.getValue());
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.VEHICLE_NUMBER_TRIP))
							{
								vehicle.setTotalTrip(Integer.parseInt(attribute.getValue()));
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.VEHICLE_ROUTE_ID))
							{
								vehicle.setRoute(routes.getRoute(Integer.parseInt(attribute.getValue())-1));
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.VEHICLE_SEAT_CAPACITY))
							{
								vehicle.setSeatCapacity(Integer.parseInt(attribute.getValue()));
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.VEHICLE_STANDING_CAPACITY))
							{
								vehicle.setStandingCapacity(Integer.parseInt(attribute.getValue()));
							}
							
							else if(attribute.getName().toString().equals(XMLNetworkConstant.VEHICLE_START_STATION))
							{
								
								vehicle.setStartStopIndex(Integer.parseInt(attribute.getValue()) - 1);
								
								
							}
							else if(attribute.getName().toString().equals(XMLNetworkConstant.VEHICLE_START_TIME))
							{
								vehicle.setStartTime(attribute.getValue());
							}
							
						}
						
						vehicles.addVehicle(vehicle);
					}
				}
			
			
			return vehicles;
		}
		
		/**
		 * setStopAttributes
		 * @param startElement
		 * @return
		 */
		private static IStop setStopAttributes(StartElement startElement)
		{
			IStop stop = new Stop();
			
			Iterator<Attribute> attributes = startElement.getAttributes();
			while (attributes.hasNext()) 
			{
				Attribute attribute = attributes.next();
				if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_ID))
				{
					stop.setStopId(Integer.parseInt(attribute.getValue()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_CODE))
				{
					stop.setStopCode(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_DESC))
				{
					stop.setStopDesc(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_LAT))
				{
					stop.setStopLat(Double.parseDouble(attribute.getValue()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_LOCATION_TYPE))
				{
					stop.setLocationType(Integer.parseInt(attribute.getValue()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_LONG))
				{
					stop.setStopLon(Double.parseDouble(attribute.getValue()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_NAME))
				{
					stop.setStopName(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_PARENT_STATION))
				{
					//stop.setStopParentStation(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_URL))
				{
					stop.setStopUrl(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.STOP_ZONE_ID))
				{
					//stop.setStopZoneId(attribute.getValue());
				}
			}
			
			return stop;
		}
		
		/**
		 * setRouteAttributes
		 * @param startElement
		 * @return
		 */
		private static IRoute setRouteAttributes(StartElement startElement)
		{
			IRoute route = new Route();
			
			Iterator<Attribute> attributes = startElement.getAttributes();
			while (attributes.hasNext()) 
			{
				Attribute attribute = attributes.next();
				if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_AGENCY_ID))
				{
					route.setAgencyId(Integer.parseInt(attribute.getValue()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_COLOR))
				{
					route.setRouteColor(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_DESC))
				{
					route.setRouteDesc(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_ID))
				{
					route.setRouteId(Integer.parseInt(attribute.getValue()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_LONG_NAME))
				{
					route.setRouteLongName(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_SHORT_NAME))
				{
					route.setRouteShortName(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_TEXT_COLOR))
				{
					route.setRouteTextColor(attribute.getValue());
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_TYPE))
				{
					route.setRouteType(Integer.parseInt(attribute.getValue()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.ROUTE_URL))
				{
					route.setRouteUrl(attribute.getValue());
				}
			}
			
			return route;
		}
		
		/**
		 * setShapeAttributes
		 * @param startElement
		 * @return
		 */
		private static IShape setShapeAttributes(StartElement startElement)
		{
			IShape shape = new Shape();
			
			Iterator<Attribute> attributes = startElement.getAttributes();
			while (attributes.hasNext()) 
			{
				Attribute attribute = attributes.next();
				if(attribute.getName().toString().equals(XMLNetworkConstant.SHAPE_LAT))
				{
					shape.setLat(Double.parseDouble(attribute.getValue().toString()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.SHAPE_LON))
				{
					shape.setLon(Double.parseDouble(attribute.getValue().toString()));
				}
				else if(attribute.getName().toString().equals(XMLNetworkConstant.SHAPE_IS_STOP))
				{
					if(attribute.getValue().toString().equals("true"))
					{
						shape.setAsStop(true);
					}
					else
					{
						shape.setAsStop(false);
					}
				}
				
			}
			
			return shape;
		}
		
		
}
