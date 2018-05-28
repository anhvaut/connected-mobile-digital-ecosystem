package uow.cmde.transim.osmmap;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JComponent;

import uow.cmde.transim.util.osm.*;

/**
 * 
 * @author Vu The Tran
 * @since 15/12/2011
 */
public class OsmViewComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	private MapData map;
	private MapCoordTransformer transformer;
	private MapEntityRenderer renderer;
	protected boolean isAdjusted, isMapVisible;
	
	private CoordTransformerAction coordTransformerAction;
	private TransitEntityRender transitEntityRender;

	/**
	 * OsmViewComponent
	 */
	public OsmViewComponent() {
		transformer = new MapCoordTransformer();
		transformer.setScreenResolution(Toolkit.getDefaultToolkit().getScreenResolution()); 
		renderer = new MapEntityRenderer();
		isAdjusted = false;
		isMapVisible = false;
		
		coordTransformerAction = new CoordTransformerAction(this,transformer,renderer);
		transitEntityRender = new TransitEntityRender();
		
		MapMouseListener mapMouseListener = new MapMouseListener(coordTransformerAction);
		addMouseListener(mapMouseListener);
		addMouseWheelListener(mapMouseListener);
		addKeyListener(new MapKeyListener(this,coordTransformerAction));
		this.setFocusable(true);
	}

	
	/**
	 * paint
	 */
	public void paint(Graphics g) {
		
		Graphics2D g2 = (java.awt.Graphics2D) g;
		g2.setBackground(renderer.getBackgroundColor());
		g2.clearRect(0, 0, getWidth(), getHeight());
		
		if (getWidth() > 0 && map != null) {
			
			if (!isAdjusted) {
				transformer.adjustTransformation(map.getBoundingBox(),getWidth(), getHeight());
				isAdjusted = true;
			}
			
			float latMin = transformer.lat(getHeight());
			float lonMin = transformer.lon(0);
			float latMax = transformer.lat(0);
			float lonMax = transformer.lon(getWidth());
			float scale = transformer.computeScale();
			MapBoundingBox vbox = new MapBoundingBox(latMin, lonMin, latMax, lonMax);
			
			float viewScale = scale / renderer.getDisplayFactor();
			renderer.initForRendering(g2, transformer, map);
			if(isMapVisible)
			{
				map.visitEntities(renderer, vbox, viewScale);
			}
			
			renderer.printBufferedObjects();
			
			transitEntityRender.drawEntities(transformer, g2);
			
		}
	}

	/**
	 * OsmMap
	 * @return
	 */
	public MapData getMap() {
		return map;
	}

	/**
	 * setMap
	 * @param map
	 */
	public void setMap(MapData map) {
		
		this.map = map;
		
		if (map != null) 
		{
			isAdjusted = false;
		}
	}
	
	
	
	
	/**
	 * TransitEntityRender
	 * @return
	 */
	public TransitEntityRender getTransitEntityRenderer()
	{
		return transitEntityRender;
	}
	
	/**
	 * getTransformer
	 * @return
	 */
	public MapCoordTransformer getTransformer()
	{
		return transformer;
	}
	
	/**
	 * setRouteViewOnly
	 * @param b
	 */
	public void setMapVisible(boolean b)
	{
		isMapVisible = b;
	}
	
	/**
	 * getRouteViewOnly
	 * @return
	 */
	public boolean getMapVisible()
	{
		return isMapVisible;
	}

	/**
	 * adjustToFit
	 */
	public void adjustToFit() {
		isAdjusted = false;
		repaint();
	}
	
	public void adjustToCenter()
	{
		MapPosition centerPosition = coordTransformerAction.getCenterPosition();
		coordTransformerAction.adjustToCenter(centerPosition.getLat(),centerPosition.getLon());
	}

	
}
