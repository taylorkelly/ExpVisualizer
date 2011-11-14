package expvisualizerserver;

import java.awt.AlphaComposite;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author dpflan
 *
 */
public class ColorChangePanel extends JPanel { //holder for the activity panels
    public static final int WIDTH = ExpVisualizerServer.WIDTH;
    public static final int HEIGHT = ExpVisualizerServer.HEIGHT;
    public static final GradientPaint BACKGROUND_COLOR = new GradientPaint(WIDTH / 2, HEIGHT, new Color(255, 255, 255), WIDTH / 2, 0, new Color(125, 125, 125), true);
    public List<Activity> activities;
    public List<ActivityColorPanel> activityPanels = new LinkedList<ActivityColorPanel>();
    public int heightAdjuster = 1;

    public ColorChangePanel(List<Activity> activities) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT / 3));
        this.setBackground(Color.BLACK);
        this.activities = activities;
        this.setLayout(null);
    }

    public void constructActivityColorPanels(ExpVisualizerServer frame) {
        if (this.activities.size() > 0)
            this.heightAdjuster = this.activities.size();
        int y = 0;
        int yDelta = (getHeight() / this.activities.size()) - 4; //for borders
        for (Activity activity : this.activities) {
            activityPanels.add(new ActivityColorPanel(activity, 0, y, WIDTH, yDelta, frame));
            y = y + yDelta;
        }
        for (ActivityColorPanel actPanel : activityPanels) {
            this.add(actPanel);
        }
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        g2d.setPaint(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, width, height);

        double percent = 1 - Math.abs(this.getBounds().getX()) / (double) WIDTH;
        g2d.setComposite(makeComposite((float) percent));
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }
}
