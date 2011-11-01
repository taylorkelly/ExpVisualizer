package expvisualizerserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author taylor
 */
public class ExpVisualizerServer extends JFrame implements ActionListener, MouseMotionListener{
    private VisPanel panel;
    private List<Pulse> pulses;
    private Timer timer;
    private PacketListener listener;

    public ExpVisualizerServer() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);

        pulses = Collections.synchronizedList(new ArrayList<Pulse>());
        pulses.add(new Pulse(Color.RED));

        try {
            listener = new PacketListener(pulses);
        } catch (IOException ex) {
            System.out.println("packet listener failed initialization");
        }

        timer = new Timer(1000 / 60, this);

        panel = new VisPanel(pulses);
        panel.addMouseMotionListener(this);

        this.add(panel);
        //this.add(new ButtonPanel(pulses), BorderLayout.SOUTH);

        this.pack();
        this.centerWindow();

        
        timer.start();
        listener.start();
    }

    public void centerWindow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the window
        this.setLocation(x, y);
    }

    public void actionPerformed(ActionEvent event) {
        panel.repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExpVisualizerServer server = new ExpVisualizerServer();
        server.setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        System.out.println("slide");
    }

    @Override
    public void mouseMoved(MouseEvent me) {}
}
