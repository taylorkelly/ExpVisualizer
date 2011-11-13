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
    private List<Pulse> pulses;
    private JPanel colorChangePanel;
    public static final int WIDTH = ExpVisualizerServer.WIDTH;
    public static final int HEIGHT = ExpVisualizerServer.HEIGHT;
    public static final GradientPaint BACKGROUND_COLOR = new GradientPaint(WIDTH / 2, HEIGHT, new Color(25, 25, 25), WIDTH / 2, 0, new Color(50, 50, 50), true);

    public VisPanel(List<Pulse> pulses) {
        this.pulses = pulses;
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


        List<Pulse> alivePulses = new LinkedList<Pulse>();
        for (Pulse pulse : pulses) {
            pulse.draw(g2d, this.getWidth(), this.getHeight(), alivePulses);
        }

        drawLifeline(g2d);

        double percent = Math.abs(this.getBounds().getX()) / (double)WIDTH;
        g2d.setComposite(makeComposite((float) percent));
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    private void drawLifeline(Graphics2D g) {
        int rectWidth = (int) (this.getWidth() * 0.75);
        int rectHeight = 10;
        int arcWidth = 10;
        int arcHeight = 10;


        int x = (this.getWidth() - rectWidth) / 2;
        int y = (this.getHeight() - rectHeight) / 2;

        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);

    }
}
