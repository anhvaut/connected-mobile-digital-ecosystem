package uow.cmde.transim.offline.tools;

import java.awt.Color;

import smile.Network;
import smile.SMILEException;
import smile.learning.DataMatch;
import smile.learning.DataSet;
import smile.learning.EM;

public class ServiceReliabilityDiagram {

	public String diagramFilePath = "D:/transimOutput/service_reliability.xdsl";
	
	public ServiceReliabilityDiagram()
	{
		
	}
	
	public void buildInitialNetwork() {
		 try {
			 
		   Network net = new Network();
		   
		   /***
		    * Definition of Nodes
		    */
		   net.addNode(Network.NodeType.Cpt, "VehicleSpeed");
		   net.addOutcome("VehicleSpeed", "Slow");
		   net.addOutcome("VehicleSpeed", "Normal");
		   net.addOutcome("VehicleSpeed", "Fast");
		   net.deleteOutcome("VehicleSpeed", 0);
		   net.deleteOutcome("VehicleSpeed", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "VehiclePosition");
		   net.addOutcome("VehiclePosition", "OnSchedule");
		   net.addOutcome("VehiclePosition", "OffSchedule");
		   net.deleteOutcome("VehiclePosition", 0);
		   net.deleteOutcome("VehiclePosition", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "PassengerAlighting");
		   net.addOutcome("PassengerAlighting", "Low");
		   net.addOutcome("PassengerAlighting", "Normal");
		   net.addOutcome("PassengerAlighting", "High");
		   net.deleteOutcome("PassengerAlighting", 0);
		   net.deleteOutcome("PassengerAlighting", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "PassengerBoarding");
		   net.addOutcome("PassengerBoarding", "Low");
		   net.addOutcome("PassengerBoarding", "Normal");
		   net.addOutcome("PassengerBoarding", "High");
		   net.deleteOutcome("PassengerBoarding", 0);
		   net.deleteOutcome("PassengerBoarding", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "RunningTime");
		   net.addOutcome("RunningTime", "OnTime");
		   net.addOutcome("RunningTime", "LessThan5MinLate");
		   net.addOutcome("RunningTime", "MajorLate");
		   net.deleteOutcome("RunningTime", 0);
		   net.deleteOutcome("RunningTime", 0);
		   
		   
		   net.addNode(Network.NodeType.Cpt, "DwellTime");
		   net.addOutcome("DwellTime", "Negligible");
		   net.addOutcome("DwellTime", "Minor");
		   net.addOutcome("DwellTime", "Major");
		   net.deleteOutcome("DwellTime", 0);
		   net.deleteOutcome("DwellTime", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "InvehicleLoad");
		   net.addOutcome("InvehicleLoad", "Normal");
		   net.addOutcome("InvehicleLoad", "Excessive");
		   net.addOutcome("InvehicleLoad", "Unaccepted");
		   net.deleteOutcome("InvehicleLoad", 0);
		   net.deleteOutcome("InvehicleLoad", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "HeadwayAdherence");
		   net.addOutcome("HeadwayAdherence", "Negligible");
		   net.addOutcome("HeadwayAdherence", "Minor");
		   net.addOutcome("HeadwayAdherence", "Major");
		   net.deleteOutcome("HeadwayAdherence", 0);
		   net.deleteOutcome("HeadwayAdherence", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "PassengerWaitTime");
		   net.addOutcome("PassengerWaitTime", "Negligible");
		   net.addOutcome("PassengerWaitTime", "Minor");
		   net.addOutcome("PassengerWaitTime", "Major");
		   net.deleteOutcome("PassengerWaitTime", 0);
		   net.deleteOutcome("PassengerWaitTime", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "PassengerComfort");
		   net.addOutcome("PassengerComfort", "Good");
		   net.addOutcome("PassengerComfort", "Accepted");
		   net.addOutcome("PassengerComfort", "Unaccepted");
		   net.deleteOutcome("PassengerComfort", 0);
		   net.deleteOutcome("PassengerComfort", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "ServiceReliability");
		   net.addOutcome("ServiceReliability", "Yes");
		   net.addOutcome("ServiceReliability", "No");
		   net.deleteOutcome("ServiceReliability", 0);
		   net.deleteOutcome("ServiceReliability", 0);
		   
		   /**
		    * Definition of relationship of nodes
		    */
		   net.addArc("VehicleSpeed", "RunningTime");
		   net.addArc("VehiclePosition", "RunningTime");
		   
		   net.addArc("RunningTime", "HeadwayAdherence");
		   net.addArc("RunningTime", "PassengerWaitTime");
		   
		   net.addArc("PassengerAlighting", "DwellTime");
		   net.addArc("PassengerAlighting", "InvehicleLoad");
		   
		   net.addArc("PassengerBoarding", "DwellTime");
		   net.addArc("PassengerBoarding", "InvehicleLoad");
		   
		   net.addArc("DwellTime", "HeadwayAdherence");
		   net.addArc("DwellTime", "PassengerWaitTime");
		   
		   net.addArc("InvehicleLoad", "PassengerComfort");
		   
		   net.addArc("HeadwayAdherence", "ServiceReliability");
		   net.addArc("PassengerWaitTime", "ServiceReliability");
		   net.addArc("PassengerComfort", "ServiceReliability");
		   
		   /**
		    * Filling probabilities
		    */
		   
		   // Filling in the conditional distribution for node "VehicleSpeed". The 
		   // probabilities are:
		   // P("VehicleSpeed" = Slow) = 0.2
		   // P("VehicleSpeed" = Normal) = 0.5
		   // P("VehicleSpeed" = Fast) = 0.3
		   double[] aVehicleSpeedDef = {0.2, 0.5, 0.3};
		   net.setNodeDefinition("VehicleSpeed",aVehicleSpeedDef);
		   
		   // Filling in the conditional distribution for node "VehiclePosition". The 
		   // probabilities are:
		   // P("VehiclePosition" = OnSchedule) = 0.5
		   // P("VehiclePosition" = OffSchedule) = 0.5
		   double[] aVehiclePositionDef = {0.5, 0.5};
		   net.setNodeDefinition("VehiclePosition",aVehiclePositionDef);
		   
		   // Filling in the conditional distribution for node "PassengerAlighting". The 
		   // probabilities are:
		   // P("PassengerAlighting" = Low) = 0.2
		   // P("PassengerAlighting" = Normal) = 0.5
		   // P("PassengerAlighting" = High) = 0.3
		   double[] aPassengerAlightingDef = {0.2, 0.5, 0.3};
		   net.setNodeDefinition("PassengerAlighting",aPassengerAlightingDef);
		   
		   // Filling in the conditional distribution for node "PassengerBoarding". The 
		   // probabilities are:
		   // P("PassengerBoarding" = Low) = 0.2
		   // P("PassengerBoarding" = Normal) = 0.5
		   // P("PassengerBoarding" = High) = 0.3
		   double[] aPassengerBoardingDef = {0.2, 0.5, 0.3};
		   net.setNodeDefinition("PassengerBoarding",aPassengerBoardingDef);
		   
		   // Filling in the conditional distribution for node "RunningTime". The 
		   // probabilities are: (18 probabilities)
		   // P("RunningTime" = OnTime | "VehicleSpeed" = Slow, "VehiclePosition" = OnSchedule) = 0.2
		   // P("RunningTime" = LessThan5MinLate | "VehicleSpeed" = Normal, "VehiclePosition" = OnSchedule ) = 0.5
		   // P("RunningTime" = MajorLate | "VehicleSpeed" = Fast, "VehiclePosition" = OnSchedule) = 0.3
		   // P("RunningTime" = OnTime | "VehicleSpeed" = Slow, "VehiclePosition" = OffSchedule) = 0.2
		   // P("RunningTime" = LessThan5MinLate | "VehicleSpeed" = Normal, "VehiclePosition" = OffSchedule ) = 0.5
		   // P("RunningTime" = MajorLate | "VehicleSpeed" = Fast, "VehiclePosition" = OffSchedule) = 0.3
		   // P("RunningTime" = OnTime | "VehicleSpeed" = Slow, "VehiclePosition" = OffSchedule) = 0.2
		   // P("RunningTime" = LessThan5MinLate | "VehicleSpeed" = Normal, "VehiclePosition" = OffSchedule ) = 0.5
		   // P("RunningTime" = MajorLate | "VehicleSpeed" = Fast, "VehiclePosition" = OffSchedule) = 0.3
		   // P("RunningTime" = OnTime | "VehicleSpeed" = Slow, "VehiclePosition" = OffSchedule) = 0.2
		   // P("RunningTime" = LessThan5MinLate | "VehicleSpeed" = Normal, "VehiclePosition" = OffSchedule ) = 0.5
		   // P("RunningTime" = MajorLate | "VehicleSpeed" = Fast, "VehiclePosition" = OffSchedule) = 0.3
		   // P("RunningTime" = OnTime | "VehicleSpeed" = Slow, "VehiclePosition" = OffSchedule) = 0.2
		   // P("RunningTime" = LessThan5MinLate | "VehicleSpeed" = Normal, "VehiclePosition" = OffSchedule ) = 0.5
		   // P("RunningTime" = MajorLate | "VehicleSpeed" = Fast, "VehiclePosition" = OffSchedule) = 0.3
		   // P("RunningTime" = OnTime | "VehicleSpeed" = Slow, "VehiclePosition" = OffSchedule) = 0.2
		   // P("RunningTime" = LessThan5MinLate | "VehicleSpeed" = Normal, "VehiclePosition" = OffSchedule ) = 0.5
		   // P("RunningTime" = MajorLate | "VehicleSpeed" = Fast, "VehiclePosition" = OffSchedule) = 0.3
		   double[] aRunningTimeDef = {0.2, 0.5, 0.3, 0.2, 0.5, 0.3,0.2, 0.5, 0.3, 0.2, 0.5, 0.3,0.2, 0.5, 0.3, 0.2, 0.5, 0.3};
		   net.setNodeDefinition("RunningTime",aRunningTimeDef);
		   
		   // Filling in the conditional distribution for node "DwellTime". The 
		   // probabilities are: 27 probabilities
		   // P("DwellTime" = Low) = 0.2
		   // P("DwellTime" = Normal) = 0.5
		   // P("DwellTime" = High) = 0.3
		   double[] aDwellTimeDef = {0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3};
		   net.setNodeDefinition("DwellTime",aDwellTimeDef);
		   
		   // Filling in the conditional distribution for node "InvehicleLoad". The 
		   // probabilities are: 27 probabilities
		   // P("InvehicleLoad" = Low) = 0.2
		   // P("InvehicleLoad" = Normal) = 0.5
		   // P("InvehicleLoad" = High) = 0.3
		   double[] aInvehicleLoadDef = {0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3};
		   net.setNodeDefinition("InvehicleLoad",aInvehicleLoadDef);
		   
		   // Filling in the conditional distribution for node "PassengerWaitTime". The 
		   // probabilities are: 27 probabilities
		   // P("PassengerWaitTime" = Low) = 0.2
		   // P("PassengerWaitTime" = Normal) = 0.5
		   // P("PassengerWaitTime" = High) = 0.3
		   double[] aPassengerWaitTimeDef = {0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3};
		   net.setNodeDefinition("PassengerWaitTime",aPassengerWaitTimeDef);
		   
		   // Filling in the conditional distribution for node "HeadwayAdherence". The 
		   // probabilities are: 27 probabilities
		   // P("HeadwayAdherence" = Low) = 0.2
		   // P("HeadwayAdherence" = Normal) = 0.5
		   // P("HeadwayAdherence" = High) = 0.3
		   double[] aHeadwayAdherenceDef = {0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3};
		   net.setNodeDefinition("HeadwayAdherence",aHeadwayAdherenceDef);
		   
		   
		   // Filling in the conditional distribution for node "PassengerComfort". The 
		   // probabilities are: 9 probabilities
		   // P("PassengerComfort" = Low) = 0.2
		   // P("PassengerComfort" = Normal) = 0.5
		   // P("PassengerComfort" = High) = 0.3
		   double[] aPassengerComfortDef = {0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3 };
		   net.setNodeDefinition("PassengerComfort",aPassengerComfortDef);
		   
		   
		   // Filling in the conditional distribution for node "ServiceReliability". The 
		   // probabilities are: 54 probabilities
		   // P("ServiceReliability" = Low) = 0.2
		   // P("ServiceReliability" = Normal) = 0.5
		   // P("ServiceReliability" = High) = 0.3
		   double[] aServiceReliabilityDef = {0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3,0.2, 0.5, 0.3 };
		   net.setNodeDefinition("ServiceReliability",aServiceReliabilityDef);
		  
		   
		   /**
		    * Define node visual
		    */
		   net.setNodePosition("VehicleSpeed", 50, 96, 130, 50);
		   net.setNodePosition("VehiclePosition", 168, 96, 130, 50);
		   net.setNodePosition("RunningTime", 115, 248, 130, 50);
		   net.setNodePosition("PassengerAlighting", 313, 108, 130, 50);
		   net.setNodePosition("PassengerBoarding", 459, 106, 130, 50);
		   net.setNodePosition("DwellTime", 336, 252, 130, 50);
		   net.setNodePosition("InvehicleLoad", 477, 197, 130, 50);
		   net.setNodePosition("HeadwayAdherence", 286, 353, 130, 50);
		   net.setNodePosition("PassengerWaitTime", 139, 350, 130, 50);
		   net.setNodePosition("PassengerComfort", 437, 362, 130, 50);
		   net.setNodePosition("ServiceReliability", 299, 443, 130, 50);
		   
		
		   
		   
		   net.writeFile(diagramFilePath);
		 }
		 catch (SMILEException e) {
		   System.out.println(e.getMessage());
		 }
	}
	
	public void learning()
    {
    	Network net = new Network();
    	net.readFile(diagramFilePath);

    	DataSet ds = new DataSet();
    	ds.readFile("ds_tut_6.txt" );

    	int varA = 0;
    	int varB = 1;
    	int varC = 2;

    	DataMatch[] matching = new DataMatch[9];
    	matching[0] = new DataMatch(0, varA, 0);
    	matching[1] = new DataMatch(1, varA, 1);
    	matching[2] = new DataMatch(2, varA, 2);
    	matching[3] = new DataMatch(3, varB, 0);
    	matching[4] = new DataMatch(4, varB, 1);
    	matching[5] = new DataMatch(5, varB, 2);
    	matching[6] = new DataMatch(6, varC, 0);
    	matching[7] = new DataMatch(7, varC, 1);
    	matching[8] = new DataMatch(8, varC, 2);

    	final EM em = new EM();
    	em.learn(ds, net, matching);
    	
    }
	
	public static void main(String[] args)
	{
		ServiceReliabilityDiagram srd = new ServiceReliabilityDiagram();
		srd.buildInitialNetwork();
		//InfereceWithBayesianNetwork();
		//UpgradeToInfluenceDiagram();
	}
	
}
