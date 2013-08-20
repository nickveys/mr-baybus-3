package com.veys.mbb3mon.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.veys.mbb3mon.events.LightEvent;
import com.veys.mbb3mon.events.LightEventListener;
import com.veys.mbb3mon.serial.SerialManager;
import com.veys.mbb3mon.serial.SerialManagerException;

/**
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class LightingPanel extends JPanel implements LightEventListener {

	// TODO: Add internal handler class

	private LightingSubPanel l0, l1;

	public LightingPanel() {
		super();
		this.setLayout(new GridLayout(1, 2));

		l0 = new LightingSubPanel(0);
		l1 = new LightingSubPanel(1);

		this.add(l0);
		this.add(l1);
	}

	private class LightingSubPanel
		extends BorderedJPanel
		implements ActionListener {

		private static final String ON = "On";
		private static final String OFF = "Off";
		private static final String STROBE = "Strobe";
		private static final String HDD = "HDD Activity";
		private static final String HDD_INV = "HDD Activity Inverted";

		private int lightNum;
		private JComboBox combo;

		public LightingSubPanel(int lightNum) {
			super("Light " + lightNum);

			this.lightNum = lightNum;

			combo =
				new JComboBox(new String[] { ON, OFF, STROBE, HDD, HDD_INV });
			combo.addActionListener(this);

			this.add(combo);
		}

		public void actionPerformed(ActionEvent ae) {
			LightEvent le = new LightEvent(this);

			le.setNumber(lightNum);
			if (combo.getSelectedItem() == ON) {
				le.setMode(LightEvent.ON);
			} else if (combo.getSelectedItem() == OFF) {
				le.setMode(LightEvent.OFF);
			} else if (combo.getSelectedItem() == STROBE) {
				le.setMode(LightEvent.STROBE);
			} else if (combo.getSelectedItem() == HDD) {
				le.setMode(LightEvent.HDD);
			} else if (combo.getSelectedItem() == HDD_INV) {
				le.setMode(LightEvent.HDD_INV);
			}

			try {
				SerialManager.getInstance().sendEvent(le);
			} catch (SerialManagerException e) {
				e.printStackTrace();
			}
		}
	}

	public void lightSettingChanged(LightEvent e) {
		int n = e.getNumber();

		// TODO: Check number, set display value, test for packet re-sending
	}
}
