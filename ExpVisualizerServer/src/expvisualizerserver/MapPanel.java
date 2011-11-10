package expvisualizerserver;

import java.awt.AlphaComposite;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author taylor
 */
public class MapPanel extends JPanel {
    private List<Pulse> pulses;
    private BufferedImage img;
    private LinkedList<BufferedImage> images;

    public MapPanel(List<Pulse> pulses) throws IOException {
        this.pulses = pulses;
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.BLACK);
        img = ImageIO.read(getMapURL());
        images = new LinkedList<BufferedImage>();
        loadImages();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        drawTitle(g2d, "Detailed View", 35);

        drawPhotos(g2d, 300, 80, 300, 250, 10);


        g2d.drawImage(img, 0, 600 - img.getHeight(), this);



        double percent = Math.abs(this.getBounds().getX()) / 600.0;
        g2d.setComposite(makeComposite((float) percent));
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawTitle(Graphics2D g, String title, int size) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Futura", Font.PLAIN, size));
        FontMetrics metrics = g.getFontMetrics();

        int stringWidth = metrics.stringWidth(title);
        int width = this.getWidth();
        int y = (int) (size * 1.2);

        g.drawString(title, (width - stringWidth) / 2, y);
    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    public URL getMapURL() throws MalformedURLException {
        StringBuilder builder = new StringBuilder();
        builder.append("http://maps.googleapis.com/maps/api/staticmap?center=Zurich&zoom=13&size=600x350&sensor=false");
        return new URL(builder.toString());
    }

    private void drawPhotos(Graphics2D g, int x, int y, int width, int height, int padding) {
        int numImagesAcross = width/75;
        int numImagesDown = height/75;
        int imgIndex = 0;
        
        for(int i = 0; i < numImagesDown; i++) {
            for(int j = 0; j < numImagesAcross; j++) {
                int imageX = x + j*75;
                int imageY = y + i*75;
                
                if(images.size() > imgIndex) {
                    g.drawImage(images.get(imgIndex), imageX, imageY, this);
                }
                imgIndex++;
            }
        }
    
    }

    private void loadImages() throws IOException {
        String[] imageSquares = new String[]{
            "http://farm4.static.flickr.com/3082/5834904468_dea5ed9986_s.jpg",
            "http://farm1.static.flickr.com/22/171113269_a9c6ac51a5_s.jpg",
            "http://farm3.static.flickr.com/2445/5834339617_f214068007_s.jpg",
            "http://farm4.static.flickr.com/3333/5834884484_a13724a597_s.jpg",
            "http://farm4.static.flickr.com/3238/5834314825_50e52abcec_s.jpg",
            "http://farm3.static.flickr.com/2431/5834316171_9439834e0e_s.jpg",
            "http://farm6.static.flickr.com/5085/5228149325_54f1b36397_s.jpg",
            "http://farm3.static.flickr.com/2392/2043229565_b96e338c42_s.jpg"
        };
        for (String url : imageSquares) {
            BufferedImage squareImage = ImageIO.read(new URL(url));
            images.add(squareImage);
        }
    }
}
