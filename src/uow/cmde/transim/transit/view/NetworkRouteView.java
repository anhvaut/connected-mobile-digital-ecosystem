package uow.cmde.transim.transit.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import uow.cmde.transim.util.osm.MapData;
import uow.cmde.transim.util.AppHandler;
import uow.cmde.transim.transit.model.impl.*;
import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.util.ColorHandler;
import uow.cmde.transim.util.constants.LocationType;
import uow.cmde.transim.util.constants.MapWayMode;
import uow.cmde.transim.util.constants.RouteType;
import uow.cmde.transim.util.osm.MapRoutingController;
import uow.cmde.transim.util.osm.OsmReader;
import uow.cmde.transim.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 11/12/2011
 */
public class NetworkRouteView extends JFrame implements ActionListener, ListSelectionListener, MouseListener{
	
	private JToolBar toolbar;
	protected JSplitPane splitter;
	protected JTabbedPane sidebarInformation;
	
	private JPanel placesPanel, routesPanel,stopsPanel, stopDetailsPanel, leftPanel,rightPanel, centrePanel;
	private DefaultListModel places, routes, stops, placesObj;
	private JList listPlaces, listRoutes, listStops;
	private JTextField tfStopIndex, tfStopId, tfStopCode, tfStopName, tfStopDesc, tfStopLat, tfStopLon, tfZoneId, tfStopUrl, tfParentStation, tfRouteIndex,tfRouteId, tfAgencyId, tfRouteShortName, tfRouteLongName, tfRouteDesc, tfRouteUrl, tfRouteColor, tfRouteTextColor,  tfProjectPath;
	private JPopupMenu popupMenuRoutes,popupMenuPlaces, popupMenuStops ;
	private JMenuItem miDeleteRoute, miAddLocationToStop, miMoveStopUp, miMoveStopDown, miDeleteStop;
	private JButton btnNewRoute,  btnNewStop, btnSave, btnSaveAsFile, btnBrowseNetworkFile, btnBrowseMapFile, btnLoad, btnCancel;
	private JComboBox cbRouteType, cbLocationType;
	private JColorChooser colorChooserRoute, colorChooserRouteText;
	private String networkFilePath, mapPath;
	
	private IRoutes routesObj;
	private int iSelectedRoute = 0;
	
	private boolean createNewRouteStatus, createNewStopStatus;
	
	

	public NetworkRouteView()
	{
		this.setLayout(new BorderLayout());
		this.setTitle("Route Design");
		
		//Init variables
		routesObj = new Routes();
		createNewRouteStatus = false;
		createNewStopStatus = false;
		
		//Init GUI
		initComponents();
		initData();
		
	}
	

	
//==============================================================
// Begin: Actions for stop and route
//==============================================================
	
	private void stopDetails(int iSelected)
	{
		if(iSelected < routesObj.getRoute(iSelectedRoute).getSequenceStops().count())
		{
			IStop aStop = routesObj.getRoute(iSelectedRoute).getSequenceStops().getStop(iSelected);
			
			tfStopIndex.setText("" + iSelected);
			tfStopId.setText("" + aStop.getStopId());
			tfStopCode.setText(aStop.getStopCode());
			tfStopName.setText(aStop.getStopName());
			tfStopDesc.setText(aStop.getStopDesc());
			tfStopLat.setText("" + aStop.getStopLat());
			tfStopLon.setText("" + aStop.getStopLon());
			cbLocationType.setSelectedIndex(aStop.getLocationType());
			tfZoneId.setText("" + aStop.getZoneId());
			tfStopUrl.setText(aStop.getStopUrl());
		}
	}
	
	//====================================================================
	private void updateStop(String action)
	{
		if(!tfStopName.getText().trim().equals("") && !tfStopLon.getText().trim().equals("") && !tfStopLat.getText().trim().equals("") )
		{
			Stop aStop = new Stop();
			aStop.setLocationType(cbLocationType.getSelectedIndex());
			aStop.setStopCode(tfStopCode.getText());
			aStop.setStopDesc(tfStopDesc.getText());
			if(!tfStopId.getText().equals(""))
			{
				aStop.setStopId(Integer.parseInt(tfStopId.getText()));
			}
			aStop.setStopLat(Double.parseDouble(tfStopLat.getText()));
			aStop.setStopLon(Double.parseDouble(tfStopLon.getText()));
			
			aStop.setStopName(tfStopName.getText());
			aStop.setStopUrl(tfStopUrl.getText());
		//	aStop.setZoneId(tfZoneId.getText());
			
			if(action.equals("new"))
			{
				stops.addElement(tfStopName.getText());
				routesObj.getRoute(iSelectedRoute).getSequenceStops().addStop(aStop);
			}
			else if(action.equals("update"))
			{
				stops.set(Integer.parseInt(tfStopIndex.getText()), aStop.getStopName());
				routesObj.getRoute(iSelectedRoute).getSequenceStops().updateStop(Integer.parseInt(tfStopIndex.getText()), aStop);
			}
			
			
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Stop name, latitude, longitude can not blank");
		}
	}
	
	//====================================================================
	private void routeDetails()
	{
		
		if(iSelectedRoute < routesObj.count())
		{
			IRoute aRoute = routesObj.getRoute(iSelectedRoute);
			tfRouteIndex.setText("" + iSelectedRoute);
			tfRouteId.setText("" + aRoute.getRouteId());
			tfAgencyId.setText("" + aRoute.getAgencyId());
			tfRouteShortName.setText(aRoute.getRouteShortName());
			tfRouteLongName.setText(aRoute.getRouteLongName());
			tfRouteDesc.setText(aRoute.getRouteDesc());
			cbRouteType.setSelectedIndex(aRoute.getRouteType());
			tfRouteUrl.setText(aRoute.getRouteUrl());
			tfRouteColor.setText(aRoute.getRouteColor());
			tfRouteTextColor.setText(aRoute.getRouteTextColor());
			
			stops.removeAllElements();
			for(IStop aStop : aRoute.getSequenceStops().getAllStops())
			{
				stops.addElement(aStop.getStopName());
			}
		}
		
	}
	
	//====================================================================
	private void updateRoute(String action)
	{
		if(!tfRouteShortName.getText().trim().equals(""))
		{
			Route aRoute = new Route();
			//aRoute.setAgencyId(agencyId);
			aRoute.setRouteColor(tfRouteColor.getText());
			aRoute.setRouteTextColor(tfRouteTextColor.getText());
			aRoute.setRouteDesc(tfRouteDesc.getText());
			if(!tfRouteId.getText().equals(""))
			{
				aRoute.setRouteId(Integer.parseInt(tfRouteId.getText()));
			}
			aRoute.setRouteShortName(tfRouteShortName.getText());
			aRoute.setRouteLongName(tfRouteLongName.getText());
			aRoute.setRouteType(cbRouteType.getSelectedIndex());
			aRoute.setRouteUrl(tfRouteUrl.getText());
			
			if(action.equals("new"))
			{
				routes.addElement(aRoute.getRouteShortName());
				routesObj.addRoute(aRoute);
			}
			else if(action.equals("update"))
			{
				routes.set(Integer.parseInt(tfRouteIndex.getText()), aRoute.getRouteShortName());
				aRoute.setSequenceStops(routesObj.getRoute(iSelectedRoute).getSequenceStops());
				routesObj.updateRoute(Integer.parseInt(tfRouteIndex.getText()), aRoute);
			}
			
			
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Route short name can not blank");
		}
		 
	}
	
	

//==============================================================
// End: Actions for stop and route
//==============================================================
	
	private void initData()
	{
		try
		{
			
			String projectFolder = tfProjectPath.getText().trim();
			
			if(AppHandler.readConfig(projectFolder))
			{
				networkFilePath =  AppConfig.FULL_NETWORK_FILE;
				mapPath =  AppConfig.FULL_MAP_FILE;
				
				loadPlacesFromMap();
				loadNetwork();
			}
			else
			{
				/*
				if(!projectFolder.endsWith("\\") || !projectFolder.endsWith("/"))
				{
					projectFolder +="/";
				}
				
				networkFilePath  = projectFolder + ConfigType.NETWORK_NAME;
				mapPath  = projectFolder + ConfigType.MAP_NAME;
				*/
			}
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Load list places from OSM file
	 */
	private void loadPlacesFromMap()
	{
	
		if(mapPath!=null)
		{
			Stops stops = OsmReader.getAllStopsFromBz2Osm(mapPath);
			
			for(IStop aStop : stops.getAllStops())
			{
				places.addElement(aStop.getStopName());
				placesObj.addElement(aStop);
				
			}
		}
	}
	
	/**
	 * loadNetwork
	 */
	private void loadNetwork()
	{
		try
		{
			IRoutes myRoutes = AppHandler.readNetwork(networkFilePath);
			
			if(routes!=null)
			{
				routesObj = myRoutes;
				routes.removeAllElements();
				stops.removeAllElements();
				for(IRoute route : routesObj.getAllRoutes())
				{
					routes.addElement(route.getRouteShortName());
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * calculateShapes
	 */
	public void calculateShapes()
	{
		for(IRoute route:routesObj.getAllRoutes())
		{
			IShapes shapes = route.getShapes();
			System.out.println(shapes);
			if(shapes==null)
			{
				MapData map = OsmReader.getOsmMap(mapPath);
				for(IStop stop : route.getSequenceStops().getAllStops())
				{
					map.addMarker((float)stop.getStopLat(), (float)stop.getStopLon());
				}
				
				//To calculate shape frome last stop to first stop
				
				if(route.getSequenceStops().getAllStops().size()>0)
				{
					IStop firstStop = route.getSequenceStops().getAllStops().get(0);
					map.addMarker((float)firstStop.getStopLat(), (float)firstStop.getStopLon());
				}
				
				MapRoutingController mapRouting = new MapRoutingController();
				mapRouting.calculateRoute(map.getMarkers(), map, MapWayMode.FOR_VEHICLE);
				route.setShapes(mapRouting.getShape());
				
				//mapRouting.getRouteStreetInfos();
				//  routes.getRoute(0).setShapes(mapRouting.getShape());
				
			}
		}
		
		
	}

//==============================================================
// Begin: Action Listener
//==============================================================
	public void actionPerformed(ActionEvent ae) {
		
		  if (ae.getSource() == miAddLocationToStop) {
			  stops.addElement(listPlaces.getSelectedValue().toString()); 
			  routesObj.getRoute(iSelectedRoute).getSequenceStops().addStop((Stop)placesObj.getElementAt(listPlaces.getSelectedIndex()));
		  }
		  else if(ae.getSource() == miDeleteStop)
		  {
			  int iSelected = listStops.getSelectedIndex();
			  stops.remove(iSelected);
			  routesObj.getRoute(iSelectedRoute).getSequenceStops().removeStop(iSelected);
		  }
		  else if(ae.getSource() == miMoveStopUp)
		  {
			  int iSelected = listStops.getSelectedIndex();
			 
			  if(iSelected>0)
			  {
				  String tmp = stops.get(iSelected-1).toString();
				  stops.setElementAt(listStops.getSelectedValue().toString(), iSelected-1);
				  stops.setElementAt(tmp, iSelected);
				  listStops.setSelectedIndex(iSelected - 1);
				 
			  }
			  
			  routesObj.getRoute(iSelectedRoute).getSequenceStops().moveUp(iSelected);
		  }
		  else if(ae.getSource() == miMoveStopDown)
		  {
			  int iSelected = listStops.getSelectedIndex();
			  
			  if(iSelected < stops.size() - 1)
			  {
				  String tmp = stops.get(iSelected + 1).toString();
				  stops.setElementAt(listStops.getSelectedValue().toString(), iSelected + 1);
				  stops.setElementAt(tmp, iSelected);
				  listStops.setSelectedIndex(iSelected + 1);
				  listStops.setSelectedIndex(iSelected + 1);
			  }
			 
			  
			  routesObj.getRoute(iSelectedRoute).getSequenceStops().moveDown(iSelected);
		  }
		  else if(ae.getSource() == miDeleteRoute)
		  {
			  int iSelected = listRoutes.getSelectedIndex();
			  routes.remove(iSelected);
			  routesObj.removeRoute(iSelected);
		  }
		  else if(ae.getSource() == btnNewRoute)
		  {
			  if(!createNewRouteStatus)
			  {
				  btnNewRoute.setText("Submit");
				  
				  resetRouteFields();
				  resetStopFields();
				  setStopFieldEnabled(false,false,false,false,false,false,false,false,false);
				  setRouteFieldEnabled(true,true,true,true,true,true,true,true,true,true);
				  setButtonVisible(true,false,false,false,true);
				  createNewRouteStatus = true;
			  }
			  else
			  {
				  btnNewRoute.setText("New route");
				  createNewRouteStatus = false;
				  setButtonVisible(true,true,true,true,false);
				  resetRouteFields();
					
				  updateRoute("new"); 
			  }
			  
		  }
		  else if(ae.getSource() == btnNewStop)
		  {
			  if(listRoutes.isSelectionEmpty())
			  {
				  JOptionPane.showMessageDialog(this,"Please select a route");
			  }
			  else if(!createNewStopStatus)
			  {
				  btnNewStop.setText("Submit");
				  
				  resetStopFields();
				  setStopFieldEnabled(true,true,true,true,true,true,true,true,true);
				  setButtonVisible(false,true,false,false,true);
				  createNewStopStatus = true;
			  }
			  else
			  {
				  btnNewStop.setText("New stop");
				  setButtonVisible(true,true,true,true,false);
				  createNewStopStatus = false;
				  resetStopFields();
					
				  updateStop("new");
				  
			  }
		  }
		  else if(ae.getSource() == btnSave)
		  {
			  updateRoute("update");
			  updateStop("update");
			  
		  }
		  else if(ae.getSource() == btnSaveAsFile)
		  {
			   calculateShapes();
			   AppHandler.writeNetwork(routesObj, networkFilePath);
			   
			  
		  }
		  else if(ae.getSource() == btnCancel)
		  {
			  setButtonVisible(true,true,true,true,false);
			  btnNewStop.setText("New stop");
			  btnNewRoute.setText("New route");
			  createNewRouteStatus = false;
			  createNewStopStatus = false;
			  
		  }
		 
		  
	}
	
	public void valueChanged(ListSelectionEvent e) 
	{
		try
		{
		
		  if(e.getSource() == listStops)
		  {
			  int iSelected = listStops.getSelectedIndex();
			  if(iSelected!=-1)
			  {
				  stopDetails(iSelected);
				  setStopFieldEnabled(true,true,true,true,true,true,true,true,true);
			  }
		  }
		  else if(e.getSource() == listRoutes)
		  {
			  iSelectedRoute = listRoutes.getSelectedIndex();
			  if(iSelectedRoute!=-1)
			  {
				  routeDetails();
				  
				  if(!stops.isEmpty())
				  {
					  listStops.setSelectedIndex(0);
				  }
				  
				  setComponentStatus(true,true,true);
			  }
		  }
		}catch(Exception ex)
		{
			
			System.out.println("remove route cause:" + ex.getMessage());
		}
	}
	
	

	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			
			if(e.getSource() == tfRouteColor)
			{
				 Color color = colorChooserRoute.showDialog(this, "Route color", Color.red);
				 if(color!=null)
				 {
					 
					 tfRouteColor.setText(ColorHandler.getRGBString(color));

				 }
			}
			else if(e.getSource() == tfRouteTextColor)
			{
				 Color color = colorChooserRouteText.showDialog(this, "Route text color", Color.red);
				 if(color!=null)
				 {
					 tfRouteTextColor.setText(ColorHandler.getRGBString(color));
				 }
			}
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) { }
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	
	

//===================================================================
// GUI Design
//====================================================================

		private void initComponents()
		{
			toolbar = new JToolBar();
			splitter = new JSplitPane();
			sidebarInformation = new JTabbedPane();
			JPanel contentPanel = new JPanel();
			
			placesPanel = new JPanel();
			routesPanel = new JPanel();
			stopsPanel = new JPanel();
			stopDetailsPanel = new JPanel();
			leftPanel = new JPanel();
			rightPanel = new JPanel();
			centrePanel = new JPanel();
			
			places = new DefaultListModel();
			stops = new DefaultListModel();
			routes = new DefaultListModel();
			placesObj = new DefaultListModel();

			
			listPlaces = new JList(places);
			listRoutes = new JList(routes);
			listStops = new JList(stops);
			
			tfStopIndex = new JTextField(20);
			tfStopId = new JTextField(20);
			tfStopCode = new JTextField(20);
			tfStopName = new JTextField(20);
			tfStopDesc = new JTextField(20);
			tfStopLat = new JTextField(20);
			tfStopLon = new JTextField(20);
			tfZoneId = new JTextField(20);
			tfStopUrl = new JTextField(20);
			tfParentStation = new JTextField(20);
			cbLocationType = new JComboBox(LocationType.DISPLAY);
			tfStopIndex.setVisible(false);
			//tfStopId.setEnabled(false);
			
			tfRouteIndex = new JTextField(20);
			tfRouteId = new JTextField(20);
			tfAgencyId = new JTextField(20);
			tfRouteShortName = new JTextField(20);
			tfRouteLongName = new JTextField(20);
			tfRouteDesc = new JTextField(20);
			cbRouteType  = new JComboBox(RouteType.DISPLAY);
			tfRouteUrl = new JTextField(20);
			tfRouteColor = new JTextField(20);
			tfRouteTextColor = new JTextField(20);
			tfRouteIndex.setVisible(false);
			//tfRouteId.setEnabled(false);
			
			
			tfProjectPath = new JTextField(30);
			tfProjectPath .setText("D:\\My Research\\My Project\\MyTranSim\\projects\\uow");
			
			btnSave = new JButton("Save");
			btnSaveAsFile = new JButton("Save as file");
			btnNewRoute = new JButton("New route");
			btnNewStop = new JButton("New stop");
			btnBrowseNetworkFile = new JButton("Browse");
			btnBrowseMapFile = new JButton("Browse");
			btnLoad = new JButton("Load");
			btnCancel = new JButton("Cancel");
			
			colorChooserRoute = new JColorChooser();
			colorChooserRouteText = new JColorChooser();
			
			popupMenuRoutes = new JPopupMenu();
			popupMenuPlaces = new JPopupMenu();
			popupMenuStops = new JPopupMenu();
			
			miDeleteRoute = new JMenuItem("Remove");
			miAddLocationToStop = new JMenuItem("Add as stop");
			miMoveStopUp = new JMenuItem("Move up");
			miMoveStopDown = new JMenuItem("Move down");
			miDeleteStop = new JMenuItem("Remove");
			
			popupMenuRoutes.add(miDeleteRoute);
			popupMenuPlaces.add(miAddLocationToStop);
			popupMenuStops.add(miMoveStopUp );
			popupMenuStops.add(miMoveStopDown );
			popupMenuStops.add(miDeleteStop );
			
			initPlacesLayout();
			initRouteLayout();
			initStopLayout();
			initRouteDetailsLayout();
			initStopDetailsLayout();
			initToolbar();
			
			miDeleteRoute.addActionListener(this);
			miAddLocationToStop.addActionListener(this);
			miMoveStopUp.addActionListener(this);
			miMoveStopDown.addActionListener(this);
			miDeleteStop.addActionListener(this);
			btnSave.addActionListener(this);
			btnSaveAsFile.addActionListener(this);
			btnNewRoute.addActionListener(this);
			btnNewStop.addActionListener(this);
			btnCancel.addActionListener(this);
			btnBrowseNetworkFile.addActionListener(this);
			btnBrowseMapFile.addActionListener(this);
			tfRouteColor.addMouseListener(this);
			tfRouteTextColor.addMouseListener(this);
			
			listRoutes.addListSelectionListener(this);
			listPlaces.addListSelectionListener(this);
			listStops.addListSelectionListener(this);
			
			leftPanel.setLayout(new GridLayout(2,1));
			leftPanel.add(routesPanel);
			leftPanel.add(placesPanel);
			
			rightPanel.setLayout(new BorderLayout());
			rightPanel.add(stopDetailsPanel, BorderLayout.NORTH);
			
			
			//centrePanel.setLayout(new GridLayout(2,1));
			//centrePanel.add(stopsPanel);
			//centrePanel.add(new JScrollPane(lineMapPanel));
			
			
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(toolbar, BorderLayout.NORTH);
			contentPanel.add(splitter, BorderLayout.CENTER);
			//splitter.add(leftPanel, JSplitPane.LEFT);
			//splitter.add(sidebarInformation, JSplitPane.LEFT);
			
			contentPanel.add(leftPanel, BorderLayout.WEST);
			contentPanel.add(stopsPanel, BorderLayout.CENTER);
			contentPanel.add(rightPanel, BorderLayout.EAST);
			
			setComponentStatus(true,false,false);
			setStopFieldEnabled(false,false,false,false,false,false,false,false,false);
			setRouteFieldEnabled(true,true,true,true,true,true,true,true,true,true);
			setButtonVisible(true,true,true,true,false);
		}
		
		private void initToolbar()
		{
			toolbar.setLayout(new BorderLayout());
			toolbar.setFloatable(false);
			
			JPanel rightPanel = new JPanel();
			JPanel leftPanel = new JPanel();
			
			rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			leftPanel.add(new JLabel("Project path:"));
			leftPanel.add(tfProjectPath);
			
			//toolbar.add(btnLoad);
			rightPanel.add(btnNewRoute);
			rightPanel.add(btnNewStop);
			rightPanel.add(btnSave);
			rightPanel.add(btnSaveAsFile);
			rightPanel.add(btnCancel);
			
			toolbar.add(leftPanel,BorderLayout.WEST);
			toolbar.add(rightPanel,BorderLayout.EAST);
		}
		
		//==================================================================================
		private void initPlacesLayout()
		{
			placesPanel.setLayout(new BorderLayout());
			loadPlacesFromMap();
			placesPanel.add(new JLabel("Places from OSM:"), BorderLayout.NORTH);
			placesPanel.add(new JScrollPane(listPlaces), BorderLayout.CENTER);
			
			listPlaces.addMouseListener(new MouseAdapter() {
			     public void mouseClicked(MouseEvent me) {
			       
			       if (SwingUtilities.isRightMouseButton(me)
			             && !listPlaces.isSelectionEmpty()
			             && listPlaces.locationToIndex(me.getPoint())
			                == listPlaces.getSelectedIndex()) {
			    	   
			    	   		popupMenuPlaces.show(listPlaces, me.getX(), me.getY());
			        }
			     }
			 });
		}
		
		//==================================================================================
		private void initRouteLayout()
		{
			routesPanel.setLayout(new BorderLayout());
			routesPanel.add(new JLabel("Routes:"), BorderLayout.NORTH);
			routesPanel.add(new JScrollPane(listRoutes), BorderLayout.CENTER);
			
			listRoutes.addMouseListener(new MouseAdapter() {
			     public void mouseClicked(MouseEvent me) {
			       
			       if (SwingUtilities.isRightMouseButton(me)
				             && !listRoutes.isSelectionEmpty()
				             && listRoutes.locationToIndex(me.getPoint())
				                == listRoutes.getSelectedIndex()) {
			    	   
			    	   			popupMenuRoutes.show(listRoutes, me.getX(), me.getY());
			       }
			     }
			});
			
			
		}
		
		//==================================================================================
		private void initStopLayout()
		{
			
			stopsPanel.setLayout(new BorderLayout());
			stopsPanel.add(new JLabel("Stops:"), BorderLayout.NORTH);
			stopsPanel.add(new JScrollPane(listStops), BorderLayout.CENTER);
			
			listStops.addMouseListener(new MouseAdapter() {
			     public void mouseClicked(MouseEvent me) {
			       
			       if (SwingUtilities.isRightMouseButton(me)
				             && !listStops.isSelectionEmpty()
				             && listStops.locationToIndex(me.getPoint())
				                == listStops.getSelectedIndex()) {
			    	   
			    	   			popupMenuStops.show(listStops, me.getX(), me.getY());
			       }
			     }
			});
		}
		
		//==================================================================================
		private void initRouteDetailsLayout()
		{
			
			stopDetailsPanel.setLayout(new GridLayout(24,2));
			
			stopDetailsPanel.add(new JLabel(""));
			stopDetailsPanel.add(new JLabel("Route Information"));
			stopDetailsPanel.add(new JLabel(" Route Id:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfRouteId);
			stopDetailsPanel.add(new JLabel(" Agency Id:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfAgencyId);
			stopDetailsPanel.add(new JLabel(" Route Short Name:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfRouteShortName);
			stopDetailsPanel.add(new JLabel(" Route Long Name:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfRouteLongName);
			stopDetailsPanel.add(new JLabel(" Route Desc:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfRouteDesc);
			stopDetailsPanel.add(new JLabel(" Route Type:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(cbRouteType);
			stopDetailsPanel.add(new JLabel(" Route Url:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfRouteUrl);
			stopDetailsPanel.add(new JLabel(" Route Color:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfRouteColor);
			stopDetailsPanel.add(new JLabel(" Route Text Color:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfRouteTextColor);
			stopDetailsPanel.add(new JLabel("", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfRouteIndex);
			
		}
		
		//==================================================================================
		private void initStopDetailsLayout()
		{
			
			
			stopDetailsPanel.add(new JLabel(""));
			stopDetailsPanel.add(new JLabel("Stop Information"));
			stopDetailsPanel.add(new JLabel(" Stop Id:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfStopId);
			stopDetailsPanel.add(new JLabel(" Stop Code:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfStopCode);
			stopDetailsPanel.add(new JLabel(" Stop Name:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfStopName);
			stopDetailsPanel.add(new JLabel(" Stop Description:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfStopDesc);
			stopDetailsPanel.add(new JLabel(" Stop Latitude:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfStopLat);
			stopDetailsPanel.add(new JLabel(" Stop Longitude:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfStopLon);
			stopDetailsPanel.add(new JLabel(" Location Type:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(cbLocationType);
			stopDetailsPanel.add(new JLabel(" Zone Id:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfZoneId);
			stopDetailsPanel.add(new JLabel(" Stop Url:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfStopUrl);
			stopDetailsPanel.add(new JLabel(" Parent Station:  ", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfParentStation);
			stopDetailsPanel.add(new JLabel("", SwingConstants.RIGHT));
			stopDetailsPanel.add(tfStopIndex);
			
		}
		
		//==================================================================================
		private void setComponentStatus(Boolean bListRoutes, Boolean bListPlaces, Boolean bListStop)
		{
			listRoutes.setEnabled(bListRoutes);
			listPlaces.setEnabled(bListPlaces);
			listStops.setEnabled(bListStop);
			
		}
		
		//==================================================================================
		private void setButtonVisible(Boolean bBtnNewRoute, Boolean bBtnNewStop, Boolean bBtnSave, Boolean bBtnSaveAsFile, Boolean bBtnCancel)
		{
			btnNewRoute.setVisible(bBtnNewRoute);
			btnNewStop.setVisible(bBtnNewStop);
			btnSave.setVisible(bBtnSave);
			btnSaveAsFile.setVisible(bBtnSaveAsFile);
			btnCancel.setVisible(bBtnCancel);
			
		}
		
		//==================================================================================
		private void setStopFieldEnabled( Boolean bTfStopCode,Boolean bTfStopName,Boolean bTfStopDesc,Boolean bTfStopLat,Boolean bTfStopLon,Boolean bTfZoneId,Boolean bTfStopUrl,Boolean bTfParentStation,Boolean bTfLocationType)
		{
			tfStopCode.setEnabled(bTfStopCode);
			tfStopName.setEnabled(bTfStopName);
			tfStopDesc.setEnabled(bTfStopDesc);
			tfStopLat.setEnabled(bTfStopLat);
			tfStopLon.setEnabled(bTfStopLon);
			tfZoneId.setEnabled(bTfZoneId);
			tfStopUrl.setEnabled(bTfStopUrl);
			tfParentStation.setEnabled(bTfParentStation);
			cbLocationType.setEnabled(bTfLocationType);
		}
		
		//==================================================================================
		private void setRouteFieldEnabled( Boolean bTfRouteIndex,Boolean bTfRouteId,Boolean bTfAgencyId,Boolean bTfRouteShortName,Boolean bTfRouteLongName,Boolean bTfRouteDesc,Boolean bTfRouteType,Boolean bTfRouteUrl,Boolean bTfRouteColor, Boolean bTfRouteTextColor)
		{
			tfRouteIndex.setEnabled(bTfRouteIndex);
			tfRouteId.setEnabled(bTfRouteId);
			tfAgencyId.setEnabled(bTfAgencyId);
			tfRouteShortName.setEnabled(bTfRouteShortName);
			tfRouteLongName.setEnabled(bTfRouteLongName);
			tfRouteDesc.setEnabled(bTfRouteDesc);
			cbRouteType.setEnabled(bTfRouteType);
			tfRouteUrl.setEnabled(bTfRouteUrl);
			tfRouteColor.setEnabled(bTfRouteColor);
			tfRouteTextColor.setEnabled(bTfRouteTextColor);
		}
		
		
		//==================================================================================
		private void resetRouteFields()
		{
			tfRouteIndex.setText("");
			tfRouteId.setText("" + (routes.getSize() + 1));
			tfAgencyId.setText("1");
			tfRouteShortName.setText("");
			tfRouteLongName.setText("");
			tfRouteDesc.setText("");
			cbRouteType.setSelectedIndex(3);
			tfRouteUrl.setText("");
			tfRouteColor.setText("");
			tfRouteTextColor.setText("");
			
		}
		
		//==================================================================================
		private void resetStopFields()
		{
			tfStopIndex.setText("");
			tfStopId.setText("" + (stops.getSize() + 1));
			tfStopCode.setText("");
			tfStopName.setText("");
			tfStopDesc.setText("");
			tfStopLat.setText("");
			tfStopLon.setText("");
			tfZoneId.setText("");
			tfStopUrl.setText("");
			tfParentStation.setText("");
			//cbLocationType.setText("");
			
		}

		
//===================================================================
// GUI Design
//====================================================================	
	
}
