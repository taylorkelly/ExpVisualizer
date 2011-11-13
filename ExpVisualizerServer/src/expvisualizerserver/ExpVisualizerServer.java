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
    private ColorChangePanel colorChangePanel;
    private List<Activity> activities;
    private Timer timer;
    private PacketListener listener;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;

    public ExpVisualizerServer() throws IOException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setLayout(null);

        activities = Collections.synchronizedList(new ArrayList<Activity>());
        activities.add(new Activity(ActivityType.PHOTO, 5000));

        try {
            listener = new PacketListener(activities);
        } catch (IOException ex) {
            System.out.println("packet listener failed initialization");
        }

        timer = new Timer(1000 / 60, this);

        colorChangePanel = new ColorChangePanel();
        colorChangePanel.addMouseListener(this);

        basicPanel = new VisPanel(activities);
        basicPanel.addMouseMotionListener(this);
        basicPanel.addMouseListener(this);

        mapPanel = new MapPanel(activities);
        mapPanel.addMouseMotionListener(this);
        mapPanel.addMouseListener(this);

        this.add(colorChangePanel);
        colorChangePanel.setBounds(0, HEIGHT + (HEIGHT / 4), colorChangePanel.getPreferredSize().width, colorChangePanel.getPreferredSize().height);
        this.add(basicPanel);
        basicPanel.setBounds(0, 0, basicPanel.getPreferredSize().width, basicPanel.getPreferredSize().height);
        //this.add(new ButtonPanel(pulses), BorderLayout.SOUTH);
        this.add(mapPanel);
        mapPanel.setBounds(WIDTH, 0, basicPanel.getPreferredSize().width, basicPanel.getPreferredSize().height);



        this.pack();
        this.setSize(new Dimension(WIDTH, HEIGHT));
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
        mapPanel.update();
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

            if (Math.abs(deltaX) > 100) {
                oldX = newX;
                return;
            }

            Rectangle bounds = basicPanel.getBounds();
            Point origin = bounds.getLocation();

            if (origin.getX() + deltaX >= 0) {
                origin.setLocation(0, origin.getY());
                bounds.setLocation(origin);
                basicPanel.setBounds(bounds);

                origin.setLocation(origin.getX() + WIDTH, origin.getY());
                bounds.setLocation(origin);
                mapPanel.setBounds(bounds);
            } else if (origin.getX() + deltaX <= -WIDTH) {
                origin.setLocation(-WIDTH, origin.getY());
                bounds.setLocation(origin);
                basicPanel.setBounds(bounds);

                origin.setLocation(origin.getX() + WIDTH, origin.getY());
                bounds.setLocation(origin);
                mapPanel.setBounds(bounds);
            } else {
                origin.setLocation(origin.getX() + deltaX, origin.getY());
                bounds.setLocation(origin);
                basicPanel.setBounds(bounds);

                origin.setLocation(origin.getX() + WIDTH, origin.getY());
                bounds.setLocation(origin);
                mapPanel.setBounds(bounds);
            }
        }
        oldX = newX;
    }
    private Timer slideTimer;

    private void slideTo(int goalPosition) {
        slideTimer = new Timer(1000 / 45, new SlideListener(goalPosition, 15));
        slideTimer.start();
    }

    private class SlideListener implements ActionListener {
        private int goalPosition;
        private int currTicks;
        private int numTicks;
        private int stepSize;

        public SlideListener(int goalPosition, int numTicks) {
            this.goalPosition = goalPosition;
            this.currTicks = 0;
            this.numTicks = numTicks;
            Rectangle bounds = basicPanel.getBounds();
            Point origin = bounds.getLocation();
            int x = (int) origin.getX();
            this.stepSize = (goalPosition - x) / numTicks;
        }

        public void actionPerformed(ActionEvent e) {
            Rectangle bounds = basicPanel.getBounds();
            Point origin = bounds.getLocation();
            int x = (int) origin.getX();

            if (currTicks >= numTicks) {
                slideTimer.stop();

                origin.setLocation(goalPosition, origin.getY());
                bounds.setLocation(origin);
                basicPanel.setBounds(bounds);

                origin.setLocation(goalPosition+WIDTH, origin.getY());
                bounds.setLocation(origin);
                mapPanel.setBounds(bounds);
            } else {
                int deltaX = stepSize;
                origin.setLocation(origin.getX() + deltaX, origin.getY());
                bounds.setLocation(origin);
                basicPanel.setBounds(bounds);

                origin.setLocation(origin.getX() + WIDTH, origin.getY());
                bounds.setLocation(origin);
                mapPanel.setBounds(bounds);

                currTicks++;
            }

        }
    }

    private void finishSliding() {
        Rectangle bounds = basicPanel.getBounds();
        Point origin = bounds.getLocation();
        int x = (int) origin.getX();
        if (x > -WIDTH / 2) {
            slideTo(0);
        } else {
            slideTo(-WIDTH);
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
      //upon click the vis panel will resize
      //resize the vispanel
      if (basicPanel.getBounds().height < HEIGHT) {
        basicPanel.setBounds(0, 0, basicPanel.getPreferredSize().width, basicPanel.getPreferredSize().height);
      }

      else if (basicPanel.getBounds().height == HEIGHT) {
        int height = basicPanel.getPreferredSize().height;
        basicPanel.setBounds(0, 0, basicPanel.getPreferredSize().width, height - (height / 3));
        colorChangePanel.setBounds(0, HEIGHT - ( HEIGHT / 3), colorChangePanel.getPreferredSize().width, colorChangePanel.getPreferredSize().height);
      }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //oldX = Integer.MAX_VALUE;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        oldX = Integer.MAX_VALUE;
        finishSliding();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
