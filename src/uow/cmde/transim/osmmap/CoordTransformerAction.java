package uow.cmde.transim.osmmap;

import java.awt.Toolkit;

import uow.cmde.transim.util.osm.*;

/**
 * 
 * @author Vu The Tran
 * @since 15/12/2011
 */
public class CoordTransformerAction {
	
	private MapCoordTransformer transformer;
	private OsmViewComponent component;
	private MapEntityRenderer renderer;
	
	/**
	 * CoordTransformerEvent
	 * @param component
	 * @param transformer
	 * @param renderer
	 * @param eventListeners
	 */
	public CoordTransformerAction(OsmViewComponent component, MapCoordTransformer transformer, MapEntityRenderer renderer)
	{
		this.component = component;
		this.transformer = transformer;
		this.renderer = renderer;
		
	}
	
	/**
	 * setScreenWidthInCentimeter
	 * @param cm
	 */
	public void setScreenWidthInCentimeter(double cm) {
		double dotsPerCm = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/ cm;
		transformer.setScreenResolution((int) (dotsPerCm * 2.54));
	}
	
	/**
	 * setScreenSizeInInch
	 * @param inch
	 */
	public void setScreenSizeInInch(double inch) {
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		double dotsPerInch = Math.sqrt(width * width + height * height) / inch;
		transformer.setScreenResolution((int) dotsPerInch);
	}
	
	/**
	 * zoom
	 * @param factor
	 * @param focusX
	 * @param focusY
	 */
	public void zoom(float factor, int focusX, int focusY) {
		transformer.zoom(factor, focusX, focusY);
		component.repaint();
	}

	/**
	 * multiplyDisplayFactorWith
	 * @param fac
	 */
	public void multiplyDisplayFactorWith(float fac) {
		renderer.setDisplayFactor(renderer.getDisplayFactor() * fac);
		component.repaint();
	}
	
	
	/**
	 * adjust
	 * @param dx
	 * @param dy
	 */
	public void adjust(int dx, int dy) {
		transformer.adjust(dx, dy);
		component.repaint();
	}

	/**
	 * adjustToCenter
	 * @param lat
	 * @param lon
	 */
	public void adjustToCenter(double lat, double lon) {
		int dx = component.getWidth() / 2 - transformer.x(lon);
		int dy = component.getHeight() / 2 - transformer.y(lat);
		adjust(dx, dy);
	}

	/**
	 * getCenterPosition
	 * @return
	 */
	public MapPosition getCenterPosition() {
		float lat = transformer.lat(component.getHeight() / 2);
		float lon = transformer.lon(component.getWidth() / 2);
		return new MapPosition(lat, lon);
	}

	/**
	 * getBoundingBox
	 * @return
	 */
	public MapBoundingBox getBoundingBox() {
		float latMin = transformer.lat(component.getHeight());
		float lonMin = transformer.lon(0);
		float latMax = transformer.lat(0);
		float lonMax = transformer.lon(component.getWidth());
		return new MapBoundingBox(latMin, lonMin, latMax, lonMax);
	}
	

	/**
	 * AbstractEntityRenderer
	 * @return
	 */
	public MapEntityRenderer getRenderer() {
		return renderer;
	}

	/**
	 * setRenderer
	 * @param renderer
	 */
	public void setRenderer(MapEntityRenderer renderer) {
		this.renderer = renderer;
		component.repaint();
	}

	/**
	 * getTransformer
	 * @return
	 */
	public MapCoordTransformer getTransformer() {
		return transformer;
	}

	
}
