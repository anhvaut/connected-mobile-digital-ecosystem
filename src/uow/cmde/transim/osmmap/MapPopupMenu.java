package uow.cmde.transim.osmmap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;
import javax.swing.*;


/**
 * 
 * @author Vu The Tran
 * @since 15/12/2011
 */
public class MapPopupMenu extends JPopupMenu implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JMenuItem mniPassengerInfo;
	
	public MapPopupMenu()
	{
		mniPassengerInfo = new JMenuItem("Passenger on/off");
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == mniPassengerInfo)
		{
			
		}
	}
}
