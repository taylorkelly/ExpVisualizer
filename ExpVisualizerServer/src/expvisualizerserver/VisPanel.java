
package expvisualizerserver;

import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author taylor
 */
public class VisPanel extends JPanel {
    private List<Pulse> pulses;
    
    public VisPanel(List<Pulse> pulses) {
        this.pulses = pulses;
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.BLACK);
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        for(Pulse pulse: pulses) {
            pulse.draw(g, this.getWidth(), this.getHeight());
        }
        
        drawLifeline(g2d);
    }

    private void drawLifeline(Graphics2D g) {
        int rectWidth = (int)(this.getWidth()*0.75);
        int rectHeight = 10;
        int arcWidth = 10;
        int arcHeight = 10;
        
        
        int x = (this.getWidth()-rectWidth)/2;
        int y = (this.getHeight()-rectHeight)/2;
        
        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
        
    }
    
    
}
