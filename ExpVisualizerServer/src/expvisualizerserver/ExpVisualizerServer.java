package expvisualizerserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class ExpVisualizerServer extends JFrame implements ActionListener, MouseMotionListener, MouseListener {
    private VisPanel basicPanel;
    private MapPanel mapPanel;
    private List<Pulse> pulses;
    private Timer timer;
    private PacketListener listener;

    public ExpVisualizerServer() throws IOException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setLayout(null);

        pulses = Collections.synchronizedList(new ArrayList<Pulse>());
        pulses.add(new Pulse(Color.RED));

        try {
            listener = new PacketListener(pulses);
        } catch (IOException ex) {
            System.out.println("packet listener failed initialization");
        }

        timer = new Timer(1000 / 60, this);

        basicPanel = new VisPanel(pulses);
        basicPanel.addMouseMotionListener(this);
        basicPanel.addMouseListener(this);

        mapPanel = new MapPanel(pulses);
        mapPanel.addMouseMotionListener(this);
        basicPanel.addMouseListener(this);


        this.add(basicPanel);
        basicPanel.setBounds(0, 0, basicPanel.getPreferredSize().width, basicPanel.getPreferredSize().height);
        //this.add(new ButtonPanel(pulses), BorderLayout.SOUTH);
        this.add(mapPanel);
        mapPanel.setBounds(600, 0, basicPanel.getPreferredSize().width, basicPanel.getPreferredSize().height);



        this.pack();
        this.setSize(new Dimension(600, 600));
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
        basicPanel.repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ExpVisualizerServer server = new ExpVisualizerServer();
            server.setVisible(true);
        } catch (IOException ex) {
        }
        
    }
    int oldX = Integer.MAX_VALUE;

    @Override
    public void mouseDragged(MouseEvent me) {
        int newX = me.getXOnScreen();
        if (oldX != Integer.MAX_VALUE) {
            int deltaX = newX - oldX;
            deltaX *= 1.5;
            
            Rectangle bounds = basicPanel.getBounds();
            Point origin = bounds.getLocation();

            if (origin.getX() + deltaX >= 0) {
                origin.setLocation(0, origin.getY());
                bounds.setLocation(origin);
                basicPanel.setBounds(bounds);

                origin.setLocation(origin.getX() + 600, origin.getY());
                bounds.setLocation(origin);
                mapPanel.setBounds(bounds);
            } else if (origin.getX() + deltaX <= -600) {
                origin.setLocation(-600, origin.getY());
                bounds.setLocation(origin);
                basicPanel.setBounds(bounds);

                origin.setLocation(origin.getX() + 600, origin.getY());
                bounds.setLocation(origin);
                mapPanel.setBounds(bounds);
            } else {
                origin.setLocation(origin.getX() + deltaX, origin.getY());
                bounds.setLocation(origin);
                basicPanel.setBounds(bounds);

                origin.setLocation(origin.getX() + 600, origin.getY());
                bounds.setLocation(origin);
                mapPanel.setBounds(bounds);
            }
        }
        oldX = newX;
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        oldX = Integer.MAX_VALUE;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        oldX = Integer.MAX_VALUE;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
