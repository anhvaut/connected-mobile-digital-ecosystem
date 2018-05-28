package uow.cmde.transim.osmmap;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.SwingUtilities;


/**
 * 
 * @author Vu The Tran
 * @since 15/12/2011
 */
public class MapMouseListener implements MouseListener, MouseWheelListener{
	
	int xp, yp;

	public CoordTransformerAction coordTransformerAction;
	public MapMouseListener(CoordTransformerAction coordTransformerAction)
	{
		this.coordTransformerAction = coordTransformerAction;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
		{
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		xp = e.getX();
		yp = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int xr = e.getX();
			int yr = e.getY();
			
			if (xr != xp || yr != yp)
			{
				coordTransformerAction.adjust(xr - xp, yr - yp);
			} 
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rot = e.getWheelRotation();
		int x = e.getX();
		int y = e.getY();
		float fac = ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) ? 1.1f : 1.5f;
		
		if (rot == -1) {
			if ((e.getModifiers() & KeyEvent.ALT_MASK) != 0) 
			{
				coordTransformerAction.multiplyDisplayFactorWith(fac);
			} 
			else
			{
				coordTransformerAction.zoom(fac, x, y);
			}
			
		} 
		else if (rot == 1) {
			
			if ((e.getModifiers() & KeyEvent.ALT_MASK) != 0) 
			{
				coordTransformerAction.multiplyDisplayFactorWith(1f / fac);
			} 
			else
			{
				coordTransformerAction.zoom(1 / fac, x, y);
			}
		}
	}
	
}
