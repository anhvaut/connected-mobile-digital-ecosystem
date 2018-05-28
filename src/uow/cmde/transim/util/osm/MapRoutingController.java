package uow.cmde.transim.util.osm;

/**
 * 
 * @author Vu The Tran
 * @since 15/12/2011
 */

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.informed.AStarSearch;
import aima.core.util.CancelableThread;
import aimax.osm.data.MapWayFilter;
import aimax.osm.data.OsmMap;
import aimax.osm.data.Position;
import aimax.osm.data.entities.MapNode;
import aimax.osm.routing.*;
import uow.cmde.transim.transit.model.impl.*;

public class MapRoutingController extends  RouteCalculator{
	

	private Shapes shapes;
	
	public MapRoutingController()
	{
		shapes = new Shapes();
		
	}
	
	/**
	 * calculateRoute
	 */
	public List<Position> calculateRoute(List<MapNode> locs, OsmMap mapData,int waySelection) {
		
		List<Position> result = new ArrayList<Position>();
		
		try {
			MapWayFilter wayFilter = createMapWayFilter(mapData, waySelection);
			boolean ignoreOneways = (waySelection == 0);
			MapNode fromRNode = mapData.getNearestWayNode(new Position(locs.get(0)), wayFilter);
			result.add(new Position(fromRNode.getLat(), fromRNode.getLon()));
			shapes.addShape(fromRNode.getLat(), fromRNode.getLon(), true);
			
			
			for (int i = 1; i < locs.size() && !CancelableThread.currIsCanceled(); i++) 
			{
				
				MapNode toRNode = mapData.getNearestWayNode(new Position(locs.get(i)), wayFilter);
				HeuristicFunction hf = createHeuristicFunction(toRNode,waySelection);
				Problem problem = createProblem(fromRNode, toRNode, mapData,wayFilter, ignoreOneways, waySelection);
				
				Search search = new AStarSearch(new GraphSearch(), hf);
				List<Action> actions = search.search(problem);
				if (actions.isEmpty())
				{
					break;
				}
				
				Position position = null;
				for (Object action : actions) 
				{
					if (action instanceof OsmMoveAction) 
					{
						OsmMoveAction a = (OsmMoveAction) action;
						
						for (MapNode node : a.getNodes())
						{
							if (!node.equals(a.getFrom()))
							{
								position = new Position(node.getLat(), node.getLon());
								result.add(position);
								shapes.addShape(node.getLat(),node.getLon(),false);
								
								
							}
						}
						
					}
				}
				
				if(position !=null)
				{
					shapes.getLastShape().setAsStop(true);
				}
				fromRNode = toRNode;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Shapes getShape()
	{
		return shapes;
	}

}
