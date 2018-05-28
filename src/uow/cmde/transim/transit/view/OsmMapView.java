package uow.cmde.transim.transit.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.io.*;

import uow.cmde.transim.osmmap.OsmViewComponent;
import uow.cmde.transim.transit.controller.TransitHandler;
import uow.cmde.transim.util.*;
import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.util.TimeConverter;
import uow.cmde.transim.util.osm.OsmReader;
import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.util.osm.MapData;
import uow.cmde.transim.util.gui.*;
import uow.cmde.transim.util.AppConfig;


/**
 * 
 * @author Vu The Tran
 * @since 15/12/2011
 */

public class OsmMapView extends JFrame implements ActionListener, Runnable{

	private static final long serialVersionUID = 1L;
	
	private OsmViewComponent osmView;
	private MapData map;
	
	private JComboBox cbRoute;
	private JTextArea	taStopInfo, taVehicleInfo, taSimulationStatus;
	private JToolBar toolbar;
	private JSplitPane splitter;
	private JTabbedPane sidebarInformation;
	
	private JTextField tfProjectPath, tfDate, tfTime;
	private JButton btnLoad, btnStart;
	private JCheckBox cbMapViewMode;
	private JLabel lbDateTime;
	private String networkPath, mapPath, transportPlan, outputFolder;
	
	private boolean runStatus = false;
	private IRoutes routes;
	private Thread myThread;
	private IActiveVehicles vehicles;
	private Calendar runningDate;
	private TransitParameters transitParameters;
	
	public OsmMapView()
	{
		this.setTitle("Map");
		
		initComponents();
		initData();
		initThread();
		
		
	}


//===================================================================
	private void initData()
	{
		try
		{
		
			if(AppHandler.readConfig(tfProjectPath.getText().trim()))
			{
				networkPath = AppConfig.FULL_NETWORK_FILE;
				mapPath = AppConfig.FULL_MAP_FILE;
				transportPlan = AppConfig.FULL_TRANSPORT_PLAN_FILE;
				outputFolder = AppConfig.OUTPUT_PATH;
				
				AppHandler.readTransitParameter(transportPlan);
				AppHandler.readControlStrategy(AppConfig.FULL_CONTROL_STRATEGY_FILE);
				TransitHandler.initalizeTransitNetwork(outputFolder,transportPlan);
				
				loadMap();
				
				taSimulationStatus.append("initalizing data... \n");
				taSimulationStatus.append("..." + AppConfig.CONFIG_FILE + " is loaded \n");
				taSimulationStatus.append("..." +AppConfig.MAP_FILE + " is loaded \n");
				taSimulationStatus.append("..." +AppConfig.NETWORK_FILE + " is loaded \n");
				taSimulationStatus.append("..." +AppConfig.TRANSPORT_PLAN_FILE + " is loaded \n");
				taSimulationStatus.append("..." +AppConfig.CONTROL_STRATEGY_FILE + " is loaded \n");
				taSimulationStatus.append("\n");
				taSimulationStatus.append("Output file: " + AppConfig.OUTPUT_FILE + " \n");
				taSimulationStatus.append("Overwrite output: " + AppConfig.OVERWRITE_OUTPUT_FILE + " \n");
				
				taSimulationStatus.append("\n");
				taSimulationStatus.append("reading parameters... \n");
				taSimulationStatus.append("...Demand scale: " +AppConfig.TRANSIT_DEMAND_SCALE + "\n");
				taSimulationStatus.append("...Aligting fraction: " +AppConfig.TRANSIT_PASSENGER_ALIGHTING_FRACTION + "\n");
				taSimulationStatus.append("...Average aligting time: " +AppConfig.TRANSIT_AVERAGE_ALIGHTING_TIME_EACH_PASSENGER + "\n");
				taSimulationStatus.append("...Average boarding time: " +AppConfig.TRANSIT_AVERAGE_BOARDING_TIME_EACH_PASSENGER + "\n");
				taSimulationStatus.append("...Average speed:" +AppConfig.TRANSIT_AVERAGE_SPEED_KM_PER_HOUR + " km/h\n");
				taSimulationStatus.append("...Deceleration time: " +AppConfig.TRANSIT_DECELERATION_TIME + "\n");
				taSimulationStatus.append("...Scheduled headway: " +AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_MINUTE + " minutes\n");
				taSimulationStatus.append("...Headway deviation: " +AppConfig.TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_MINUTE + " minutes\n");
				taSimulationStatus.append("...Using strategy:" +AppConfig.TRANSIT_USING_STRATEGY + "\n");
				
				if(AppConfig.TRANSIT_USING_STRATEGY)	
				{
					taSimulationStatus.append("\n");
					taSimulationStatus.append("control strategy:\n");
					
					if(AppConfig.MO_ALGORITHM!=null)
					{
						taSimulationStatus.append("...algorithm:" +AppConfig.MO_ALGORITHM + "\n");
					}
					
					if(AppConfig.MO_NUMBER_OF_DIMENSIONS!=0)
					{
						taSimulationStatus.append("...dimensions:" +AppConfig.MO_NUMBER_OF_DIMENSIONS+ "\n");
					}
					
					if(AppConfig.MO_NUMBER_OF_OBJECTIVES !=0)
					{
						taSimulationStatus.append("...objectives:" +AppConfig.MO_NUMBER_OF_OBJECTIVES+ "\n");
					}
					
					if(AppConfig.MO_OBJECTIVES!= null)
					{
						StringTokenizer stringTokenizer = new  StringTokenizer(AppConfig.MO_OBJECTIVES,",");
						
						int i = 1;
						while(stringTokenizer.hasMoreTokens()){
							  taSimulationStatus.append( ".... (" + i++ + ")" + stringTokenizer.nextToken().trim() + "\n");
							  
						}
					}
					
					if(AppConfig.MO_OUTPUT_FILE != null)
					{
						taSimulationStatus.append("...output file:" +AppConfig.MO_OUTPUT_FILE+ "\n");
					}
					
					if(AppConfig.MO_TOT_GENERATIONS !=0)
					{
						taSimulationStatus.append("...total generations:" +AppConfig.MO_TOT_GENERATIONS+ "\n");
					}
					
					if(AppConfig.MO_TOT_INTERATION !=0)
					{
						taSimulationStatus.append("...total interation:" +AppConfig.MO_TOT_INTERATION+ "\n");
					}
					
					if(AppConfig.MO_BATCH_SIZE !=0)
					{
						taSimulationStatus.append("...batch size:" +AppConfig.MO_BATCH_SIZE+ "\n");
					}
					
					if(AppConfig.MO_PASSENGER_WAIT_WEIGHT !=0)
					{
						taSimulationStatus.append("...passenger wait weight:" +AppConfig.MO_PASSENGER_WAIT_WEIGHT+ "\n");
					}
					
					if(AppConfig.MO_HEADWAY_WEIGHT !=0)
					{
						taSimulationStatus.append("...headway weight:" +AppConfig.MO_HEADWAY_WEIGHT+ "\n");
					}
					
					if(AppConfig.MO_SELECTED_NUMBER_BUS !=0)
					{
						taSimulationStatus.append("...selected number bus:" +AppConfig.MO_SELECTED_NUMBER_BUS+ "\n");
					}
					
					
					
				}
				
				
				for(IRoute route: routes.getAllRoutes())
				{
					cbRoute.addItem(route.getRouteShortName());
				}
				
				if(AppConfig.OVERWRITE_OUTPUT_FILE)
				{
				   	File outputFile=new File(AppConfig.OUTPUT_PATH + "/" + AppConfig.OUTPUT_FILE);
				   	File moOutputFile=new File(AppConfig.OUTPUT_PATH + "/" + AppConfig.MO_OUTPUT_FILE);
				   	if(outputFile.exists())
				   	{
				   		outputFile.delete();
				   	}
				    	
				   	if(moOutputFile.exists())
				   	{
				   		moOutputFile.delete();
				   	}
				    
				    
				}
				   
				
			}
			
			
		}
		catch(Exception ex)
		{
			System.out.println("init data" + ex.getMessage());
		}
	}
//====================================================================
	public void loadMap()
	{
		 if(!mapPath.equals("") && !networkPath.equals(""))
		  {
			 try
			 {
				  map = OsmReader.getOsmMap(mapPath);
				  routes = AppHandler.readNetwork(networkPath);
					
				  /*
				  for(Route r:routes.getAllRoutes())
				  {
					 System.out.println( r.getShapes().getStopDistanceFromFirstStop(0))
				  }*/
				  osmView.setMap(map);
				  osmView.getTransitEntityRenderer().setRoutes(routes);
				  osmView.repaint();
				  
			 }catch(Exception ex)
			 {
				 System.out.println(ex.getMessage());
			 }
		  }
	}

//==================================================================
	public void createVehicle()
	{
		
		vehicles = TransitHandler.initalizeRunningVehicle(routes);
		osmView.getTransitEntityRenderer().setVehicles(vehicles);
		osmView.getTransitEntityRenderer().setRunStatus(runStatus);
		osmView.repaint();
		
	}
	
	
//====================================================================	
	public void actionPerformed(ActionEvent ae) {
		
		  if (ae.getSource() == btnLoad) 
		  {
			  loadMap();
		  }
		  else if (ae.getSource() == btnStart) {
			  
			   
			  	
			    if(!runStatus)
			    {
			    	
					btnStart.setText("Stop");
					runStatus = true;
					handleStartDate();
			    }
			    else
			    {
			    	btnStart.setText("Start");
			    	runStatus = false;
			    }
			    
			    osmView.getTransitEntityRenderer().setRunStatus(runStatus);
			    
			    tfTime.setEditable(false);
			   
		  }
		
		  else if (ae.getSource() == cbMapViewMode) {
			  if(cbMapViewMode.isSelected())
			  {
				  osmView.setMapVisible(false);
				  osmView.repaint();
			  }
			  else
			  {
				  osmView.setMapVisible(true);
				  osmView.repaint();
			  }
		  }
	}

//====================================================================

	public void initThread()
	{
		  try
			{   
			    taSimulationStatus.append("\n");
			    taSimulationStatus.append("loading demand database... \n");
				taSimulationStatus.append("... host: " + AppConfig.DATABASE_HOST + "\n");
				taSimulationStatus.append("... database: " + AppConfig.DATABASE_SOURCE + "\n");
				taSimulationStatus.append("... username: " + AppConfig.DATABASE_USERNAME + "\n");
				taSimulationStatus.append("\n");
				taSimulationStatus.append("initalizing Passenger Demand .. \n");
				
				TransitHandler.initalizePassengerDemand(routes);
				
	
			}
			catch(Exception ex)
			{
				System.out.println("Error:" + ex.getMessage());
				taSimulationStatus.append("Connection to database failed \n");
			}
			
		  	createVehicle();
			myThread =  new Thread(this);
			myThread.start();
	}
	
	
	public void handleStartDate()
	{
		String date = tfDate.getText();
		StringTokenizer timeTokenizer = new  StringTokenizer(date,"/");
		int dd = Integer.parseInt(timeTokenizer.nextToken());
		int mm = Integer.parseInt(timeTokenizer.nextToken());
		int yyyy = Integer.parseInt(timeTokenizer.nextToken());
		
		runningDate.set(yyyy,mm-1,dd);
	}
	
//====================================================================	
	public void run()
	{
		
		//int i= 7*3600;
		int i= 16*3600;
		while (true)
		{
				
				try{
					int weekday = runningDate.get(Calendar.DAY_OF_WEEK);
					if(runStatus && (weekday == 2 || weekday == 3 ||weekday == 4 || weekday == 5 || weekday == 6))
					{
						
						
						if(i <= (24*3600))
						{
							String time = TimeConverter.convertSecondToTime(i);
							tfTime.setText(time);
							
							
							String dayOfWeek = "";
							if (weekday == 2) dayOfWeek = "Monday";
							else if (weekday == 3) dayOfWeek = "Tuesday";
							else if (weekday == 4) dayOfWeek = "Wednesday";
							else if (weekday == 5) dayOfWeek = "Thursday";
							else if (weekday == 6) dayOfWeek = "Friday";
								
							
							TransitHandler.updatePassengerDemand(routes,dayOfWeek, time);
							taStopInfo.setText(TransitHandler.printPassengerDemandInfo(routes));
							taVehicleInfo.setText(TransitHandler.printRunningVehicleInfo(vehicles, time));
							
							TransitHandler.actionPerformedAtStop(routes,vehicles,tfDate.getText(), dayOfWeek, time);
							
							for(IActiveVehicle vehicle:vehicles.getAllVehicles())
							{
								
								if(vehicle!=null)
								{
									
									if(vehicle.getStartTime().equals(time))
									{
										vehicle.setStartRunning(true);
									}
								}
							}
							
							i++;
						}
						else
						{
							//Start new date
							//i= 7*3600;
							i = 16*3600;
							runningDate.add(Calendar.DAY_OF_MONTH, 1);
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							tfDate.setText("" + formatter.format(runningDate.getTime()));
							
						}
						
						osmView.repaint();	
					}
			
					Thread.sleep(1);
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
		}
	}
	
	
	
	
	
//===================================================================
// GUI Design
//====================================================================	
		private void initComponents()
		{
			toolbar = new JToolBar();
			splitter = new JSplitPane();
			sidebarInformation = new JTabbedPane();
			JPanel contentPanel = new JPanel();
			
			cbRoute = new JComboBox();
			taStopInfo = new JTextArea(10,22);
			taVehicleInfo = new JTextArea(20,22);
			taSimulationStatus = new JTextArea(10,22);
			
			osmView = new OsmViewComponent();
			
			tfProjectPath = new JTextField(30);
			tfDate = new JTextField(10);
			tfTime = new JTextField(5);
			tfProjectPath.setText(AppConfig.PROJECT_PATH);
			tfTime.setEditable(true);
			tfTime.setText(TimeConverter.convertSecondToTime(TimeConverter.DEAULT_SECONDS));
			
			runningDate = Calendar.getInstance();
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			tfDate.setText("" + formatter.format(runningDate.getTime()));
			
			lbDateTime = new JLabel();
			btnLoad = new JButton("load");
			btnStart = new JButton("Start");
			btnLoad.addActionListener(this);
			btnStart.addActionListener(this);
			
			cbMapViewMode = new JCheckBox("Display Map",true);
			cbMapViewMode.addActionListener(this);
			
			
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(toolbar, BorderLayout.NORTH);
			contentPanel.add(splitter, BorderLayout.CENTER);

			
			splitter.add(osmView, JSplitPane.RIGHT);
			splitter.add(sidebarInformation, JSplitPane.LEFT);
			sidebarInformation.setTabPlacement(JTabbedPane.BOTTOM);
			
			initToolbar();
			initSideBar();
		}
	
		//=======================================================================
		
		private void initToolbar()
		{
			toolbar.setLayout(new BorderLayout());
			toolbar.setFloatable(false);
			
			JPanel rightPanel = new JPanel();
			JPanel leftPanel = new JPanel();
			
			rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			leftPanel.add(btnLoad);
			leftPanel.add(tfProjectPath);
			leftPanel.add(cbMapViewMode);
			leftPanel.add(lbDateTime);
			
			rightPanel.add(new JLabel("Date:"));
			rightPanel.add(tfDate);
			rightPanel.add(new JLabel("Time:"));
			rightPanel.add(tfTime);
			rightPanel.add(btnStart);
			
			toolbar.add(leftPanel,BorderLayout.WEST);
			toolbar.add(rightPanel,BorderLayout.EAST);
		}

		
	//===================================================================
		private void initSideBar()
		{
			
			
			JScrollPane generalPane = new JScrollPane(taSimulationStatus);
			generalPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			JPanel busNetworkPane = new JPanel();
			JGridBagPanel busNetworkInfo = new JGridBagPanel();
			busNetworkInfo.addComponent(new JLabel(""), 0,0,1,1);
			busNetworkInfo.addComponent(cbRoute, 0,1,1,1);
			
			busNetworkInfo.addComponent(new JLabel("Station info:  "), 1,0,1,2);
			busNetworkInfo.addComponent(taStopInfo, 2,0,1,2);
			
			busNetworkInfo.addComponent(new JLabel("Vehicle info:  "), 3,0,1,2);
			busNetworkInfo.addComponent(new JScrollPane(taVehicleInfo), 4,0,1,2);
			
			
			
			busNetworkPane.add( busNetworkInfo);
			
			sidebarInformation.addTab("General", generalPane);
			sidebarInformation.addTab("Passenger", busNetworkPane);
			sidebarInformation.setPreferredSize(new Dimension(250,250));
			
		}
//===========================================================================
// End GUI Design
//===========================================================================
	
}
