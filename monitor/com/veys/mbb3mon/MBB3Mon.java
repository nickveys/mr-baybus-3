package com.veys.mbb3mon;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import com.veys.mbb3mon.actions.ActionFactory;
import com.veys.mbb3mon.panels.FanSpeedPanel;
import com.veys.mbb3mon.panels.LightingPanel;
import com.veys.mbb3mon.panels.TemperaturePanel;
import com.veys.mbb3mon.serial.SerialManager;

/**
 * Main entry-point and UI for Mr. Baybus III Monitor.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class MBB3Mon extends JFrame {

    public static final String TITLE = "Mr. Baybus III Monitor";

    public static final String VERSION = "v0.2";

    private static MBB3Mon myself; // singleton

    private ActionFactory actionFactory = new ActionFactory(SerialManager
            .getInstance());

    /**
     * Create new instance of MBB3Mon.
     */
    private MBB3Mon() {
        super();
        this.setSize(350, 200);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setTitle(TITLE + " " + VERSION);
        this.setJMenuBar(buildMenuBar());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout());
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("Fans", new FanSpeedPanel());
        tabPane.addTab("Lighting", new LightingPanel());
        tabPane.addTab("Temperatures", new TemperaturePanel());
        this.getContentPane().add(tabPane);
    }

    /**
     * Builds the main menu bar.
     * 
     * @return Set-up and ready to use JMenuBar
     */
    public JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu setupMenu = new JMenu("System");
        setupMenu.setMnemonic('S');

        setupMenu.add(actionFactory.createConnectAction());
        setupMenu.add(actionFactory.createDisconnectAction());
        setupMenu.addSeparator();
        setupMenu.add(actionFactory.createPortAction());

        menuBar.add(setupMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        helpMenu.add(actionFactory.createAboutAction());

        menuBar.add(helpMenu);

        return menuBar;
    }

    /**
     * Get a singleton instance of the application.
     * 
     * @return Instance of MBB3Mon
     */
    public static MBB3Mon getInstance() {
        if (myself == null) {
            myself = new MBB3Mon();
        }
        return myself;
    }

    public static void main(String[] args) {
        getInstance().setVisible(true);
    }
}
