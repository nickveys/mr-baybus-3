package com.veys.mbb3mon.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.veys.mbb3mon.events.FanEvent;
import com.veys.mbb3mon.events.FanEventListener;
import com.veys.mbb3mon.serial.SerialManager;
import com.veys.mbb3mon.serial.SerialManagerException;

/**
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class FanSpeedPanel extends JPanel implements FanEventListener {

	// TODO: Add internal handler class

	private FanPanel f0, f1, f2, f3;

	public FanSpeedPanel() {
		super();
		this.setLayout(new GridLayout(1, 4));

		f0 = new FanPanel(0);
		f1 = new FanPanel(1);
		f2 = new FanPanel(2);
		f3 = new FanPanel(3);

		this.add(f0);
		this.add(f1);
		this.add(f2);
		this.add(f3);
	}

	private class FanPanel
		extends BorderedJPanel
		implements ChangeListener, MouseListener {

		private JSlider slider;
		private int fanNum;

		public FanPanel(int fanNum) {
			super("");
			this.setLayout(new BorderLayout());

			this.fanNum = fanNum;

			slider = new JSlider(JSlider.VERTICAL, 0, 100, 0);
			slider.addChangeListener(this);
			slider.addMouseListener(this);

			this.add(slider, BorderLayout.CENTER);

			this.stateChanged(null);
		}

		public void stateChanged(ChangeEvent ce) {
			this.setTitle("Fan " + fanNum + " (" + slider.getValue() + "%)");
			this.repaint();
		}

		public void mouseClicked(MouseEvent me) {
		}

		public void mouseEntered(MouseEvent me) {
		}

		public void mouseExited(MouseEvent me) {
		}

		public void mousePressed(MouseEvent me) {
		}

		public void mouseReleased(MouseEvent me) {
			FanEvent fe = new FanEvent(this);
			fe.setNumber((byte) fanNum);
			fe.setSpeed((byte) slider.getValue());
			try {
				SerialManager.getInstance().sendEvent(fe);
			} catch (SerialManagerException e) {
				e.printStackTrace();
			}
		}
	}

	public void fanSpeedChanged(FanEvent e) {
		int n = e.getNumber();

		// TODO: Update fan display, watch for event making a re-send of packet

		switch (n) {
			case 0 :
				//				f0.setSpeed(e.getSpeed());
				break;
			case 1 :
				//				f1.setSpeed(e.getSpeed());
				break;
			case 2 :
				//				f2.setSpeed(e.getSpeed());
				break;
			case 3 :
				//				f3.setSpeed(e.getSpeed());
				break;
			default :
				System.out.println("Error, fan number invalid.");
				break;
		}
	}
}
