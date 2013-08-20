package com.veys.mbb3mon.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.veys.mbb3mon.events.TemperatureEvent;
import com.veys.mbb3mon.events.TemperatureEventListener;

/**
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class TemperaturePanel
	extends JPanel
	implements TemperatureEventListener {

	// TODO: Add internal handler class
	
	private TemperatureSubPanel t0, t1, t2;

	public TemperaturePanel() {
		super();
		this.setLayout(new GridLayout(1, 3));

		t0 = new TemperatureSubPanel(TemperatureSubPanel.FAHRENHEIT, 0);
		t1 = new TemperatureSubPanel(TemperatureSubPanel.CELCIUS, 1);
		t2 = new TemperatureSubPanel(TemperatureSubPanel.CELCIUS, 2);

		this.add(t0);
		this.add(t1);
		this.add(t2);
	}

	private class TemperatureSubPanel extends BorderedJPanel {

		public static final int FAHRENHEIT = 0;
		public static final int CELCIUS = 1;

		private int mode, sensorNum;
		private JLabel temperature;
		private DecimalFormat df = new DecimalFormat("0.00");

		public TemperatureSubPanel(int mode, int sensorNum) {
			super("Sensor " + sensorNum);
			this.setLayout(new BorderLayout());
			this.mode = mode;
			this.sensorNum = sensorNum;

			temperature = new JLabel("", JLabel.CENTER);

			this.add(temperature, BorderLayout.CENTER);

			setTemperature(0);
		}

		public void setMode(int mode) {
			this.mode = mode;
		}

		public void setTemperature(double temp) {
			temperature.setText(
				df.format(temp) + " " + ((mode == CELCIUS) ? "C" : "F"));
		}
	}

	public void temperatureChanged(TemperatureEvent e) {
		switch (e.getSensorNumber()) {
			case 0 :
				t0.setTemperature(e.getTemperature());
				break;
			case 1 :
				t1.setTemperature(e.getTemperature());
				break;
			case 2 :
				t2.setTemperature(e.getTemperature());
				break;
		}
	}
}
