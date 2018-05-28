package uow.cmde.transim.util.osm;



import java.io.File;
import java.util.List;

import uow.cmde.transim.transit.model.impl.Stop;
import uow.cmde.transim.transit.model.impl.Stops;

import aimax.osm.data.EntityClassifier;
import aimax.osm.data.MapBuilder;
import aimax.osm.data.OsmMap;
import aimax.osm.data.entities.EntityViewInfo;
import aimax.osm.data.entities.MapNode;
import aimax.osm.reader.Bz2OsmReader;
import aimax.osm.reader.MapReader;
import aimax.osm.viewer.MapStyleFactory;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class OsmReader {
	
	/**
	 * getAllStopsFromBz2Osm
	 * @param bz2OsmPath
	 * @return
	 */
	public static Stops getAllStopsFromBz2Osm(String bz2OsmPath)
	{
		
		
		OsmMap osm = getOsmMap(bz2OsmPath);
		
		List<MapNode> list=((MapData)osm).getAllNames();
		Stops stops= new Stops();
		
		
		for (MapNode node : list) {
			Stop aStop = new Stop();
			aStop.setStopName(node.getName());
			aStop.setStopLat(node.getLat());
			aStop.setStopLon(node.getLon());
			stops.addStop(aStop);
			
			//System.out.println(node.getName());
		}
		
		return stops;
	}
	
	/**
	 * getOsmMap
	 * @param bz2OsmPath
	 * @return
	 */
	public static MapData getOsmMap(String bz2OsmPath)
	{
		MapReader mapReader = new Bz2OsmReader();
		EntityClassifier<EntityViewInfo> classifier = new MapStyleFactory().createDefaultClassifier();
		
		 
		MapBuilder builder = (new MapData()).getBuilder();
		builder.setEntityClassifier(classifier);
		mapReader.readMap(new File(bz2OsmPath), builder);
		MapData osm = (MapData)builder.buildMap();
		
		return osm;
	}
	
}
