package expvisualizerserver;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author taylor
 */
class Pulse {
    private long birthTime;
    private Color color;       
    
    public Pulse(Color color) {
        this(color, System.currentTimeMillis());
    }
    
    public Pulse(Color color, long birthTime) {
        this.birthTime = birthTime;
        this.color = color;
    }
    
    public static Pulse deadPulse() {
        return new Pulse(Color.BLACK);
    }
    
    public void draw(Graphics g, int width, int height) {
        int age = (int)(System.currentTimeMillis() - birthTime);
        
        
        int area = age;
        int radius = (int) Math.sqrt(area/Math.PI);
        
        int rectWidth = (int)(width*0.75+radius*2);
        int rectHeight = 10+radius*2;
        int arcWidth = radius*2;
        int arcHeight = radius*2;
        
        
        int x = (width-rectWidth)/2;
        int y = (height-rectHeight)/2;
        
        g.setColor(color);
        g.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
    }
    
    
    
}
