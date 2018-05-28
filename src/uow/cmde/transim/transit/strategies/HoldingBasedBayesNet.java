package uow.cmde.transim.transit.strategies;

/*
import uow.cmde.transim.bayes.BayesNetwork;
import uow.cmde.transim.bayes.Node;
import uow.cmde.transim.bayes.action.DecisionAction;
import uow.cmde.transim.bayes.node.ContinuousNode;
import uow.cmde.transim.bayes.node.DecisionNode;
import uow.cmde.transim.bayes.node.NodeType;
import uow.cmde.transim.bayes.node.UtilityNode;
*/
import uow.cmde.transim.transit.controller.TransitParameters;

public class HoldingBasedBayesNet {

	/*
	private BayesNetwork bayes;
	private Node dwellTimeNode, runningTimeNode, headwayAdherenceNode, utilityNode, actionNode;
	private DecisionAction holdingAction, noAction;
	private int expectedHeadway, expectedDeviation;
	*/
	
	public HoldingBasedBayesNet(TransitParameters transitParameter)
	{
		/*
		bayes = new BayesNetwork();
		
		dwellTimeNode = new ContinuousNode("DwellTime");
		runningTimeNode = new ContinuousNode("RunningTime");
		headwayAdherenceNode = new ContinuousNode("HeadwayAdherence");
		
		utilityNode = new UtilityNode("Utility");
		
		holdingAction=new DecisionAction("holdingaction");
		noAction=new DecisionAction("noaction");
		actionNode = new DecisionNode("Action",noAction,holdingAction);
		
		
		dwellTimeNode.setType(NodeType.CONTINUOUS_NODE);
		runningTimeNode.setType(NodeType.CONTINUOUS_NODE);
		headwayAdherenceNode.setType(NodeType.CONTINUOUS_NODE);
		actionNode.setType(NodeType.DECISION_NODE);
		utilityNode.setType(NodeType.UTILITY_NODE);
		
		
		bayes.setRootNodes(dwellTimeNode, runningTimeNode);
		headwayAdherenceNode.setParents(dwellTimeNode,runningTimeNode);
		headwayAdherenceNode.setChildren(actionNode);
		utilityNode.setParents(headwayAdherenceNode,actionNode);
		
			
		expectedHeadway = TransitParameters.SCHEDULE_HEADWAY_IN_MINUTE;
		expectedDeviation = TransitParameters.STANDARD_HEADWAY_DEVIATION_IN_MINUTE;
		*/
	}
	
	public void chooseAction(int currentHeadway, int headwayAfterHolding) throws Exception
	{
		/*
		double holdingUtility = 1, noactionUtility = 1;
		noAction.setUtility(noactionUtility);
		holdingAction.setUtility(holdingUtility);
		
		
		((ContinuousNode)headwayAdherenceNode).calculateCumulativeProbability(currentHeadway, expectedHeadway, expectedDeviation);
		((DecisionNode)actionNode).setSelectedAction(noAction);
		double EUNoaction = ((UtilityNode)utilityNode).calculateExpectedUtility();
		
		((ContinuousNode)headwayAdherenceNode).calculateCumulativeProbability(headwayAfterHolding,  expectedHeadway, expectedDeviation);
		((DecisionNode)actionNode).setSelectedAction(holdingAction);
		double EUHoding =((UtilityNode)utilityNode).calculateExpectedUtility();
		
		if(EUHoding<EUNoaction)
		{
			//applying EUHolding
		}
		*/
		
	}
	
	
}
