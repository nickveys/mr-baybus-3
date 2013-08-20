package com.veys.mbb3mon.panels;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class BorderedJPanel extends JPanel {

	public BorderedJPanel(String title) {
		super();
		this.setBorder(BorderFactory.createTitledBorder(title));
	}

	protected void setTitle(String title) {
		TitledBorder b = (TitledBorder) this.getBorder();
		b.setTitle(title);
	}
}
