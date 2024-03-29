package expvisualizerserver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.util.List;

/**
 *
 * @author taylor
 */
class Pulse {
    private long birthTime;
    private int lifeTime;
    private Paint color;
    private Paint deadColor;

    public Pulse(Paint color) {
        this(color, System.currentTimeMillis(), 1000 * 10);
    }

    public Pulse(Paint color, int lifeTime) {
        this(color, System.currentTimeMillis(), lifeTime);
    }

    public Pulse(Paint color, long birthTime, int lifeTime) {
        this.birthTime = birthTime;
        this.color = color;
        this.lifeTime = lifeTime;
    }

    public void draw(Graphics2D g, int width, int height, List<Pulse> alivePulses) {
        int age = (int) (System.currentTimeMillis() - birthTime);
        int area = age;
        int radius = (int) Math.sqrt(area / Math.PI);

        int rectWidth = (int) (width * 0.75 + radius * 2);
        int rectHeight = 10 + radius * 2;
        int arcWidth = radius * 2;
        int arcHeight = radius * 2;


        int x = (width - rectWidth) / 2;
        int y = (height - rectHeight) / 2;

        g.setPaint(color);
        g.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);


        if (age > lifeTime) {
            int deadArea = age - lifeTime;
            int deadRadius = (int) Math.sqrt(deadArea / Math.PI);


            int deadRectWidth = (int) (width * 0.75 + deadRadius * 2);
            int deadRectHeight = 10 + deadRadius * 2;
            int deadArcWidth = deadRadius * 2;
            int deadArcHeight = deadRadius * 2;


            int deadX = (width - deadRectWidth) / 2;
            int deadY = (height - deadRectHeight) / 2;

            if (deadColor == null) {
                if (alivePulses.isEmpty()) {
                    deadColor = VisPanel.BACKGROUND_COLOR;
                } else {
                    deadColor = alivePulses.get(0).color;
                }
            }
            g.setPaint(deadColor);
            g.fillRoundRect(deadX, deadY, deadRectWidth, deadRectHeight, deadArcWidth, deadArcHeight);
        } else {
            alivePulses.add(0, this);
        }

    }
}
