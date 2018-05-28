package uow.cmde.transim.util.osm;

import aimax.osm.data.BoundingBox;
import aimax.osm.data.Position;

/**
 * 
 * @author Vu The Tran
 * @since 12/01/2012
 */
public class MapBoundingBox extends BoundingBox{

	public MapBoundingBox(float latMin, float lonMin, float latMax, float lonMax)
	{
		super(latMin,lonMin,latMax,lonMax);
	}
	
	public MapBoundingBox(Position center, int radiusKM) {
		super(center,radiusKM);
	}

	
}
