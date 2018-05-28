package uow.cmde.transim.osmmap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class MapKeyListener  implements KeyListener{
	
	public CoordTransformerAction coordTransformerAction;
	private OsmViewComponent component;
	
	public MapKeyListener(OsmViewComponent component, CoordTransformerAction coordTransformerAction)
	{
		this.coordTransformerAction = coordTransformerAction;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		float zfac = ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) ? 1.1f : 1.5f;
		float afac = ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) ? 0.1f : 0.3f;
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_PLUS:
				if ((e.getModifiers() & KeyEvent.ALT_MASK) != 0)
				{
					coordTransformerAction.multiplyDisplayFactorWith(zfac);
				}
				else
				{
					coordTransformerAction.zoom(zfac, component.getWidth() / 2, component.getHeight() / 2);
				}
				
				break;
				
			case KeyEvent.VK_MINUS:
				
				if ((e.getModifiers() & KeyEvent.ALT_MASK) != 0)
				{
					coordTransformerAction.multiplyDisplayFactorWith(1f / zfac);
				}
				else
				{
					coordTransformerAction.zoom(1 / zfac, component.getWidth() / 2, component.getHeight() / 2);
				}
				break;
				
			case KeyEvent.VK_SPACE:
				
				if ((e.getModifiers() & KeyEvent.CTRL_MASK) == 0)
				{
					coordTransformerAction.zoom(zfac, component.getWidth() / 2, component.getHeight() / 2);
				}
				else
				{
					coordTransformerAction.zoom(1 / zfac, component.getWidth() / 2, component.getHeight() / 2);
				}
				break;
				
			case KeyEvent.VK_LEFT:
				coordTransformerAction.adjust((int) (afac * component.getWidth()), 0);
				break;
				
			case KeyEvent.VK_RIGHT:
				coordTransformerAction.adjust((int) (-afac * component.getWidth()), 0);
				break;
				
			case KeyEvent.VK_UP:
				coordTransformerAction.adjust(0, (int) (afac * component.getHeight()));
				break;
				
			case KeyEvent.VK_DOWN:
				coordTransformerAction.adjust(0, (int) (-afac * component.getHeight()));
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
