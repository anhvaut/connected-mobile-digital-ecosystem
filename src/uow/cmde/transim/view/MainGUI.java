package uow.cmde.transim.view;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
import javax.swing.*;

import uow.cmde.transim.transit.view.NetworkRouteView;
import uow.cmde.transim.transit.view.OsmMapView;
import uow.cmde.transim.historydata.report.*;
import java.awt.event.*;


public class MainGUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	 private JMenuBar menubar;
	 private JMenu mnuFile;
	 private JMenuItem miDesignRoute, miViewMap, miReport, miAPCDashBoard;
		
	 private JComponent mainPanel;
	 JTabbedPane mainTab = new JTabbedPane();
		
	 public MainGUI(){
		 	            
		 	  setTitle("CMDE TRANSIM");  
		 	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 	   
		 	  menubar = new JMenuBar();
		 	  mnuFile = new JMenu("File");
		 	  miDesignRoute = new JMenuItem("Route designer");
		 	  miViewMap = new JMenuItem("Simulation");
		 	  miReport = new JMenuItem("Report");
		 	  miAPCDashBoard = new JMenuItem("APC Dashboard");
		 	  
		 	  add(mainTab);
		 	 
		 	  initMenu();
		 	  
	 }
	 
	 private void initMenu()
	 {
		   
	 	    setJMenuBar(menubar);
			menubar.add(mnuFile);
			
			mnuFile.add(miDesignRoute);
			mnuFile.add(miViewMap);
			//mnuFile.add(miReport);
			//mnuFile.add(miAPCDashBoard);
		
			
			miDesignRoute.addActionListener(this);
			miViewMap.addActionListener(this);
			miReport.addActionListener(this);
			miAPCDashBoard.addActionListener(this);
	 }
	 
	
	 
	public static void main(String[] args) {
		MainGUI gui = new MainGUI();
		gui.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
		gui.setVisible(true); 
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == miDesignRoute)
		{
			createView(new NetworkRouteView());
		}
		else if(ae.getSource() == miViewMap)
		{
			createView(new OsmMapView());
		}
		else if(ae.getSource() == miReport)
		{
			createView(new ReportView());
		}
		
		else if(ae.getSource() == miAPCDashBoard)
		{
			createView(new APCReportView());
		}	
		
	}
	
	private void createView(JFrame myFrame)
	{
		try {
			if (mainPanel != null)
			{
				getContentPane().remove(mainPanel);
			}
			
			mainPanel = (JComponent) myFrame.getContentPane().getComponent(0);
			myFrame.getContentPane().remove(mainPanel);
			mainTab.add(myFrame.getTitle(), mainPanel);
			validate();
			
		} catch (Exception ex) {
			
			System.out.println(ex.getMessage());
			
		}
	}
	

}
