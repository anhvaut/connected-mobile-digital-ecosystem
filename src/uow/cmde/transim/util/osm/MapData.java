package uow.cmde.transim.util.osm;

import java.util.List;
import java.util.ArrayList;

import aimax.osm.data.impl.DefaultMap;
import aimax.osm.data.entities.MapNode;
/**
 * 
 * @author Vu The Tran
 * @since 12/01/2012
 */
public class MapData extends DefaultMap {

	public List<MapNode> getAllNames() {
		List<MapNode> results = new ArrayList<MapNode>();
		
		for (MapNode node : this.getPois(this.getBoundingBox())) {
			if (node.getName() != null)
				results.add(node);
		}
		return results;
	}
}
