package expvisualizerserver;

import java.awt.AlphaComposite;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author taylor
 */
public class MapPanel extends JPanel {
    private List<Pulse> pulses;
    private BufferedImage img;

    public MapPanel(List<Pulse> pulses) throws IOException {
        this.pulses = pulses;
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.BLACK);
        img = ImageIO.read(getMapURL());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Futura", Font.PLAIN, 30));
        g2d.drawString("Details", 150, 38);
        g2d.drawImage(img, 0, 600 - img.getHeight(), this);

        double percent = Math.abs(this.getBounds().getX()) / 600.0;
        g2d.setComposite(makeComposite((float) percent));
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    public URL getMapURL() throws MalformedURLException {
        StringBuilder builder = new StringBuilder();
        builder.append("http://maps.googleapis.com/maps/api/staticmap?center=Berkeley,CA&zoom=13&size=600x350&sensor=false");
        return new URL(builder.toString());
    }
}
