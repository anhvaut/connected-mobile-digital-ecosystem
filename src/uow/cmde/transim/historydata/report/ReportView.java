package uow.cmde.transim.historydata.report;


import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;

import uow.cmde.transim.transit.outputanalysis.*;
import uow.cmde.transim.historydata.*;

/**
 * 
 * @author Vu The Tran
 * @since 21/12/2011
 */
public class ReportView extends JFrame implements  ActionListener, ListSelectionListener{

	private static final long serialVersionUID = 1L;
	private JToolBar toolbar;
	private JSplitPane splitter;
	private JTabbedPane sidebarInformation;
	private JPanel reportViewPane;
	private ChartPanel chartPanel = null;
	
	private JFreeChart chart;
	private JList	listReport;
	private DefaultListModel  reportTypes;
	
	public ReportView()
	{
		this.setTitle("Report");
		
		reportTypes = new DefaultListModel();
		listReport = new JList(reportTypes);
		
		toolbar = new JToolBar();
		splitter = new JSplitPane();
		sidebarInformation = new JTabbedPane();
		JPanel contentPanel = new JPanel();
		reportViewPane = new JPanel();
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(toolbar, BorderLayout.NORTH);
		contentPanel.add(splitter, BorderLayout.CENTER);

		reportViewPane.setLayout(new BorderLayout());
		splitter.add(reportViewPane, JSplitPane.RIGHT);
		splitter.add(sidebarInformation, JSplitPane.LEFT);
		sidebarInformation.setTabPlacement(JTabbedPane.BOTTOM);
		
		initSideBar();
		initReportType();
		listReport.addListSelectionListener(this);
		
	}
	
	private void initSideBar()
	{
		JPanel mainPane = new JPanel();
		JPanel filterPanel = new JPanel();
		filterPanel.add( new JTextField(20));
		
		mainPane.setLayout(new BorderLayout());
		mainPane.add(filterPanel,BorderLayout.NORTH);
		mainPane.add(listReport);
		
		sidebarInformation.addTab("Filter", mainPane);
	}
	
	private void initReportType()
	{
		
		reportTypes.addElement("Passengers by time and date");
		reportTypes.addElement("Passenger on/off by stop");
		reportTypes.addElement("Passenger per day");
	}
	
	public void valueChanged(ListSelectionEvent e) 
	{
		try
		{
			if(e.getSource() == listReport)
			{
				 int iSelected = listReport.getSelectedIndex();
				  if(iSelected == 0)
				  {
					  if(chartPanel!=null)
					  {
						  reportViewPane.remove(chartPanel);
					  }
					  chart =ChartHandler.generatePassengerByTimeAndDateChart(HistoryDataQueryer.getPassengerByTimeAndDate());
					  chartPanel = new ChartPanel(chart);
					  reportViewPane.add(chartPanel, BorderLayout.CENTER);
					 
				  }
				  else if(iSelected == 1)
				  {
					  if(chartPanel!=null)
					  {
						  reportViewPane.remove(chartPanel);
					  }
					  
					  chart =ChartHandler.generatePassengerOnOffAtEachBusStopChart(HistoryDataQueryer.getPassengerOnOffAtEachBusStop());
					  chartPanel = new ChartPanel(chart);
					  reportViewPane.add(chartPanel, BorderLayout.CENTER);
					  
				  }
				  else if(iSelected == 2)
				  {
					  if(chartPanel!=null)
					  {
						  reportViewPane.remove(chartPanel);
					  }
					  chart =ChartHandler.generatePassengerPerDayChart(HistoryDataQueryer.getPassengerPerDay());
					  chartPanel = new ChartPanel(chart);
					  reportViewPane.add(chartPanel, BorderLayout.CENTER);
				  }
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent e) {
	
	}

  
}
