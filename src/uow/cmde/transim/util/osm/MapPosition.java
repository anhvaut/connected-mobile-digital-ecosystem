package uow.cmde.transim.util.osm;

import aimax.osm.data.Position;
import aimax.osm.data.entities.MapNode;

/**
 * 
 * @author Vu The Tran
 * @since 12/01/2012
 */
public class MapPosition extends Position {

	public MapPosition(float lat, float lon)
	{
		super(lat,lon);
	}
	
	public MapPosition(MapNode node)
	{
		super(node);
	}
	
}
