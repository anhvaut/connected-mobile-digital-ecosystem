package uow.cmde.transim.offline.tools;

import java.awt.Color;

import smile.Network;
import smile.SMILEException;
import smile.ValueOfInfo;
import smile.learning.DataMatch;
import smile.learning.DataSet;
import smile.learning.EM;

public class Tutorial {
	public static String tutorial_a = "D:/transimOutput/tutorial_a.xdsl";
	public static String tutorial_b = "D:/transimOutput/tutorial_b.xdsl";
	
	public static void main(String[] args)
	{
		CreateNetwork();
		//InfereceWithBayesianNetwork();
		//UpgradeToInfluenceDiagram();
	}
	
	public static void CreateNetwork() {
		 try {
		   Network net = new Network();
		   
		   // Creating node "Success" and setting/adding outcomes:
		   net.addNode(Network.NodeType.Cpt, "Success");
		   net.setOutcomeId("Success", 0, "Success");
		   net.setOutcomeId("Success", 1, "Failure");
		   
		   // Creating node "Forecast" and setting/adding outcomes:
		   net.addNode(Network.NodeType.Cpt, "Forecast");  
		   net.addOutcome("Forecast", "Good");
		   net.addOutcome("Forecast", "Moderate");
		   net.addOutcome("Forecast", "Poor");
		   net.deleteOutcome("Forecast", 0);
		   net.deleteOutcome("Forecast", 0);
		   
		   
		   
		   // Adding an arc from "Success" to "Forecast":
		   net.addArc("Success", "Forecast");
		   
		   // Filling in the conditional distribution for node "Success". The 
		   // probabilities are:
		   // P("Success" = Success) = 0.2
		   // P("Success" = Failure) = 0.8
		   double[] aSuccessDef = {0.2, 0.8};
		   net.setNodeDefinition("Success", aSuccessDef);
		   
		   // Filling in the conditional distribution for node "Forecast". The 
		   // probabilities are:
		   // P("Forecast" = Good | "Success" = Success) = 0.4
		   // P("Forecast" = Moderate | "Success" = Success) = 0.4
		   // P("Forecast" = Poor | "Success" = Success) = 0.2
		   // P("Forecast" = Good | "Success" = Failure) = 0.1
		   // P("Forecast" = Moderate | "Success" = Failure) = 0.3
		   // P("Forecast" = Poor | "Success" = Failure) = 0.6
		   double[] aForecastDef = {0.4, 0.4, 0.2, 0.1, 0.3, 0.6}; 
		   net.setNodeDefinition("Forecast", aForecastDef);
		   
		   // Changing the nodes' spacial and visual attributes:
		   net.setNodePosition("Success", 20, 20, 80, 30);
		   net.setNodeBgColor("Success", Color.red);
		   net.setNodeTextColor("Success", Color.white);
		   net.setNodeBorderColor("Success", Color.black);
		   net.setNodeBorderWidth("Success", 2);
		   net.setNodePosition("Forecast", 30, 100, 60, 30);
		   
		   // Writting the network to a file:
		   net.writeFile(tutorial_a);
		 }
		 catch (SMILEException e) {
		   System.out.println(e.getMessage());
		 }
		}
	
	   public static void InfereceWithBayesianNetwork() {
		 try {
		   Network net = new Network();
		   net.readFile(tutorial_a); 
		   
		       
		   // ---- We want to compute P("Forecast" = Moderate) ----
		   // Updating the network:
		   net.updateBeliefs();
		   
		   // Getting the handle of the node "Forecast":
		   net.getNode("Forecast");
		   
		   // Getting the index of the "Moderate" outcome:
		   String[] aForecastOutcomeIds = net.getOutcomeIds("Forecast");
		   int outcomeIndex;
		   for (outcomeIndex = 0; outcomeIndex < aForecastOutcomeIds.length; outcomeIndex++)
		     if ("Moderate".equals(aForecastOutcomeIds[outcomeIndex]))
		       break;
		   
		   // Getting the value of the probability:
		   double[] aValues = net.getNodeValue("Forecast");
		   double P_ForecastIsModerate = aValues[outcomeIndex];
		   
		   System.out.println("P(\"Forecast\" = Moderate) = "      + P_ForecastIsModerate);
		   
		   
		   // ---- We want to compute P("Success" = Failure | "Forecast" = Good) ----
		   // Introducing the evidence in node "Forecast":
		   net.setEvidence("Forecast", "Good");
		   
		   // Updating the network:
		   net.updateBeliefs();
		   
		   // Getting the handle of the node "Success":
		   net.getNode("Success");
		   
		   // Getting the index of the "Failure" outcome:
		   String[] aSuccessOutcomeIds = net.getOutcomeIds("Success");
		   for (outcomeIndex = 0; outcomeIndex < aSuccessOutcomeIds.length; outcomeIndex++)
		     if ("Failure".equals(aSuccessOutcomeIds[outcomeIndex]))
		       break;
		   
		   // Getting the value of the probability:
		   aValues = net.getNodeValue("Success");
		   double P_SuccIsFailGivenForeIsGood = aValues[outcomeIndex];
		   
		   System.out.println("P(\"Success\" = Failure | \"Forecast\" = Good) = " + P_SuccIsFailGivenForeIsGood);
		   
		   // ---- We want to compute P("Success" = Success | "Forecast" = Poor) ----
		   // Clearing the evidence in node "Forecast":
		   net.clearEvidence("Forecast");
		   
		   // Introducing the evidence in node "Forecast":
		   net.setEvidence("Forecast", "Good");
		   
		   // Updating the network:
		   net.updateBeliefs();
		   
		   // Getting the index of the "Failure" outcome:
		   aSuccessOutcomeIds = net.getOutcomeIds("Success");
		   for (outcomeIndex = 0; outcomeIndex < aSuccessOutcomeIds.length; outcomeIndex++)
		     if ("Failure".equals(aSuccessOutcomeIds[outcomeIndex]))
		       break;
		   
		   // Getting the value of the probability:
		   aValues = net.getNodeValue("Success");
		   double P_SuccIsSuccGivenForeIsPoor = aValues[outcomeIndex];
		   
		   System.out.println("P(\"Success\" = Success | \"Forecast\" = Poor) = " + P_SuccIsSuccGivenForeIsPoor);
		 }
		 catch (SMILEException e) {
		   System.out.println(e.getMessage());
		 }
		}
	   
	   
	   public static void UpgradeToInfluenceDiagram() {
		   try {
		     Network net = new Network();
		     net.readFile(tutorial_a);
		     
		     // Creating node "Invest" and setting/adding outcomes:
		     net.addNode(Network.NodeType.List, "Invest");
		     net.setOutcomeId("Invest", 0, "Invest");
		     net.setOutcomeId("Invest", 1, "DoNotInvest");
		     
		     // Creating the value node "Gain":
		     net.addNode(Network.NodeType.Table, "Gain");
		     
		     // Adding an arc from "Invest" to "Gain":
		     net.addArc("Invest", "Gain");
		     
		     // Adding an arc from "Success" to "Gain":
		     net.getNode("Success");
		     net.addArc("Success", "Gain");
		     
		     // Filling in the utilities for the node "Gain". The utilities are:
		     // U("Invest" = Invest, "Success" = Success) = 10000
		     // U("Invest" = Invest, "Success" = Failure) = -5000
		     // U("Invest" = DoNotInvest, "Success" = Success) = 500
		     // U("Invest" = DoNotInvest, "Success" = Failure) = 500
		     double[] aGainDef = {10000, -5000, 500, 500};
		     net.setNodeDefinition("Gain", aGainDef);
		     
		     net.writeFile(tutorial_b);
		   }
		   catch (SMILEException e) {
		     System.out.println(e.getMessage());
		   }
		  }
	   
	   public static void InferenceWithInfluenceDiagram() {
		   try {
		     // Loading and updating the influence diagram: 
		     Network net = new Network();
		     net.readFile("tutorial_b.xdsl");
		     net.updateBeliefs();
		     
		     // Getting the utility node's handle:
		     net.getNode("Gain");
		     
		     // Getting the handle and the name of value indexing parent (decision node):
		     int[] aValueIndexingParents = net.getValueIndexingParents("Gain");
		     int nodeDecision = aValueIndexingParents[0];
		     String decisionName = net.getNodeName(nodeDecision);
		     
		     // Displaying the possible expected values:
		     System.out.println("These are the expected utilities:");
		     for (int i = 0; i < net.getOutcomeCount(nodeDecision); i++) {
		       String parentOutcomeId = net.getOutcomeId(nodeDecision, i);
		       double expectedUtility = net.getNodeValue("Gain")[i];
		       
		       System.out.print("  - \"" + decisionName + "\" = " + parentOutcomeId + ": ");
		       System.out.println("Expected Utility = " + expectedUtility);
		     }
		   }
		   catch (SMILEException e) {
		     System.out.println(e.getMessage());
		   }
		  }
	   
	   
	    public static void ComputeValueOfInformation() {
		   try {
		     Network net = new Network();
		     net.readFile("tutorial_b.xdsl");
		     
		     ValueOfInfo voi = new ValueOfInfo(net);
		     
		     // Getting the handles of nodes "Forecast" and "Invest":
		     net.getNode("Forecast");
		     net.getNode("Invest");
		     
		     voi.addNode("Forecast");
		     voi.setDecision("Invest");
		     voi.update();
		     
		     double[] results = voi.getValues();
		     double EVIForecast = results[0];
		     
		     System.out.println("Expected Value of Information (\"Forecast\") = " + EVIForecast);
		   }
		   catch (SMILEException e) {
		     System.out.println(e.getMessage());
		   }
		  }
	    
	    public static void learning()
	    {
	    	Network net = new Network();
	    	net.readFile("Net_tut_6.xdsl");

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
}
