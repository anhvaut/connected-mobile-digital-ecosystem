package uow.cmde.transim.util.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

/**
 * 
 * @author Vu The Tran
 * @since 15/01/2012
 */
public class JGridBagPanel extends JPanel{

	private GridBagLayout gb;
	private GridBagConstraints gbc;
	
	public JGridBagPanel()
	{
		
		super();
		
		gb = new GridBagLayout();
		gbc = new GridBagConstraints();
		setLayout(gb);
	}
	
	public void addComponent(Component comp, int row, int col, int   nrow, int ncol)
	{
		//gbc.anchor = GridBagConstraints.NORTH;
	
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = col;
		gbc.gridy = row;
		
		gbc.gridwidth = ncol;
		gbc.gridheight = nrow;
		
		gb.setConstraints(comp,gbc);
		this.add(comp);
	}
	
}
