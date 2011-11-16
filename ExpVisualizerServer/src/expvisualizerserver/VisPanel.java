package expvisualizerserver;

import java.awt.AlphaComposite;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author taylor
 */
public class VisPanel extends JPanel {
    private List<Activity> activities;
    public static final int WIDTH = ExpVisualizerServer.WIDTH;
    public static final int HEIGHT = ExpVisualizerServer.HEIGHT;
    public static final GradientPaint BACKGROUND_COLOR = new GradientPaint(WIDTH / 2, HEIGHT, new Color(25, 25, 25), WIDTH / 2, 0, new Color(50, 50, 50), true);

    VisPanel(List<Activity> activities) {
        this.activities = activities;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2d.setPaint(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);


        List<Activity> alivePulses = new LinkedList<Activity>();
        List<Activity> paintingActivities = new LinkedList<Activity>(activities);
        
        for (Activity activity : paintingActivities) {
            activity.drawPulse(g2d, this.getWidth(), this.getHeight(), alivePulses);
        }

        boolean alive = false;
        if(!paintingActivities.isEmpty())
            alive = paintingActivities.get(paintingActivities.size()-1).isAlive(System.currentTimeMillis());

        drawLifeline(g2d, alive);

        double percent = Math.abs(this.getBounds().getX()) / (double) WIDTH;
        g2d.setComposite(makeComposite((float) percent));
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }
    double size = 0;
    double minSize = 0;
    double maxSize = 5;
    double growSpeed = 0.15;
    double maxTime = 100*0.2*0.2/(growSpeed*growSpeed);
    int step = 0;
    
    private boolean canGrow() {
        if(step <= maxTime) {
            step++;
            return false;
        } else {
            step++;
            return true;
        }
    }
    
    private void drawLifeline(Graphics2D g, boolean alive) {
        if(alive) {
            growSpeed = growSpeed/Math.abs(growSpeed)*0.4;
        } else {
            growSpeed = growSpeed/Math.abs(growSpeed)*0.15;
        }
        maxTime = 100*0.2*0.2/(growSpeed*growSpeed);
        
        int rectWidth = (int) (this.getWidth() * 0.75 + size);

        int rectHeight = (int) (10 + size);

        if (canGrow()) {
            size += growSpeed;
            if (size > maxSize) {
                growSpeed *= -1;
                size = maxSize;
            } else if (size < minSize) {
                growSpeed *= -1;
                step = 0;
                size = minSize;
            }
        }

        int arcWidth = rectHeight;
        int arcHeight = rectHeight;


        int x = (this.getWidth() - rectWidth) / 2;
        int y = (this.getHeight() - rectHeight) / 2;

        double MULT = 6;
        g.setColor(new Color((int)(255-size*MULT), (int)(255-size*MULT), (int)(255-size*MULT)));
        g.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);

    }
}
