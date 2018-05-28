package uow.cmde.transim.transit.model.impl;

/*
import uow.cmde.transim.bayes.BayesNetwork;
import uow.cmde.transim.bayes.Node;
import uow.cmde.transim.bayes.action.DecisionAction;
import uow.cmde.transim.bayes.node.ContinuousNode;
import uow.cmde.transim.bayes.node.DecisionNode;
import uow.cmde.transim.bayes.node.UtilityNode;
*/
import uow.cmde.transim.util.constants.*;

/**
 * 
 * @author Vu The Tran
 * @since 15/08/2012
 *
 *
 */
public class BusNetwork {

	/*
	private BayesNetwork bayes;
	private Node vehicleSpeedNode, vehiclePositionNode, runningTimeNode, passengerAlightingNode, passengerBoardingNode, dwellTimeNode, invehicleLoadNode, 
	             passengerWaitTimeNode, headwayAdherenceNode, passengerComfortNode, actionImpactNode, utilityNode, actionNode;
	private DecisionAction holdingAction, noAction;
    */	

	
	public BusNetwork()
	{
		/*
		bayes = new BayesNetwork();
		
		vehicleSpeedNode = new ContinuousNode(BusNetworkConstants.VEHICLE_SPEED);
		vehiclePositionNode = new ContinuousNode(BusNetworkConstants.VEHICLE_POSITION);
		runningTimeNode = new ContinuousNode(BusNetworkConstants.RUNNING_TIME);
		passengerAlightingNode = new ContinuousNode(BusNetworkConstants.PASSENGER_ALIGHTING);
		passengerBoardingNode = new ContinuousNode(BusNetworkConstants.PASSENGER_BOARDING);
		dwellTimeNode = new ContinuousNode(BusNetworkConstants.DWELL_TIME);
		invehicleLoadNode = new ContinuousNode(BusNetworkConstants.INVEHICLE_LOAD);
		passengerWaitTimeNode = new ContinuousNode(BusNetworkConstants.PASSENGER_WAIT_TIME);
		passengerComfortNode = new ContinuousNode(BusNetworkConstants.PASSENGER_COMFORT);
		headwayAdherenceNode = new ContinuousNode(BusNetworkConstants.HEADWAY_ADHERENCE);
		actionImpactNode = new ContinuousNode(BusNetworkConstants.ACTION_IMPACT);
		
		utilityNode = new UtilityNode("Utility");

		holdingAction=new DecisionAction("holdingaction");
		noAction=new DecisionAction("noaction");
		actionNode = new DecisionNode("Action",noAction,holdingAction);
		
		bayes.setRootNodes(vehicleSpeedNode, vehiclePositionNode, passengerAlightingNode, passengerBoardingNode);
		runningTimeNode.setParents(vehicleSpeedNode, vehiclePositionNode );
		dwellTimeNode.setParents(passengerAlightingNode, passengerBoardingNode );
		invehicleLoadNode.setParents(passengerAlightingNode, passengerBoardingNode);
		passengerWaitTimeNode.setParents(runningTimeNode, dwellTimeNode);
		passengerComfortNode.setParents(invehicleLoadNode);
		headwayAdherenceNode .setParents(runningTimeNode, dwellTimeNode);
		utilityNode.setParents(actionImpactNode,passengerWaitTimeNode,passengerComfortNode,headwayAdherenceNode  );
		*/
		
	}
	
	/*
	public BayesNetwork getBayesNetwork()
	{
		return bayes;
	}*/
	
	public static void main(String[] args)
	{
		/*
		BusNetwork b = new BusNetwork();
		Node n =b.getBayesNetwork().getNodeByName(BusNetworkConstants.PASSENGER_COMFORT);
		
		if(n!=null)
		{
			System.out.println(n.getName());
		}
		else
		{
			System.out.println("khong tim thay");
		}
		*/
	}
	
	
}
