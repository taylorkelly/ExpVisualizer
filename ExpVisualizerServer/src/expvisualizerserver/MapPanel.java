package expvisualizerserver;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author taylor
 */
public class MapPanel extends JPanel {
    private List<Activity> activities;
    private BufferedImage img;
    private LinkedList<BufferedImage> images;
    private TreeMap<Integer, Calendar> dateMap;
    private Map<Integer, String> textMap;
    public static final int WIDTH = ExpVisualizerServer.WIDTH;
    public static final int HEIGHT = ExpVisualizerServer.HEIGHT;

    public MapPanel(List<Activity> activities) throws IOException {
        this.activities = activities;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        img = ImageIO.read(getMapURL());
        images = new LinkedList<BufferedImage>();
        loadImages();

        dateMap = new TreeMap<Integer, Calendar>();
        textMap = new HashMap<Integer, String>();
        
        Timer timer = new Timer(1000*60, new MinuteRefresher());
        timer.start();
    }
    
    
    private class MinuteRefresher implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        } 
    }
    
    public void update() {
        int currSize = dateMap.size();
        int newSize = activities.size();
        
        for(int i = currSize; i < newSize; i++) {
            Activity currActivity = activities.get(i);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(currActivity.birthTime());
            dateMap.put(i, cal);
            
            textMap.put(i, currActivity.toString());
        }
        
        if(currSize != newSize) {
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        drawTitle(g2d, "Detailed View", 35);
        drawPhotos(g2d, WIDTH / 2 + 5, 60, WIDTH / 2, (int) (0.4 * HEIGHT), 10);
        drawTexts(g2d, 0, 60, WIDTH / 2, (int) (0.4 * HEIGHT));

        //Map
        g2d.drawImage(img, 0, (int) (0.4 * HEIGHT), WIDTH, (int) (0.6 * HEIGHT), this);

        int ovalHeight = 20;
        int x = (getWidth() - ovalHeight) / 2;
        int y = (HEIGHT - img.getHeight()) + (img.getHeight() - ovalHeight) / 2;


        GradientPaint gradient = new GradientPaint(x + ovalHeight / 2, y, new Color(122, 188, 255), x + ovalHeight / 2, y + ovalHeight, new Color(64, 150, 238), true);
        g2d.setPaint(gradient);
        g2d.fillOval(x, y, ovalHeight, ovalHeight);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x, y, ovalHeight, ovalHeight);


        // Overlay shading
        double percent = Math.abs(this.getBounds().getX()) / (double) WIDTH;
        g2d.setComposite(makeComposite((float) percent));
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawTitle(Graphics2D g, String title, int size) {

        g.setFont(new Font("Futura", Font.PLAIN, size));
        FontMetrics metrics = g.getFontMetrics();
        int stringWidth = metrics.stringWidth(title);
        int width = this.getWidth();
        int y = (int) (size * 1.2);

        GradientPaint gradient = new GradientPaint(width / 2, y + 10, new Color(25, 25, 25), width / 2, 0, new Color(50, 50, 50), true);
        g.setPaint(gradient);
        g.fillRect(0, 0, width, y + 10);


        g.setColor(Color.WHITE);
        g.drawString(title, (width - stringWidth) / 2, y);

        g.setColor(new Color(50, 50, 50));
        //g.drawLine((width - stringWidth) / 2, y + 5, (width + stringWidth) / 2, y + 5);
        g.drawLine((width) / 2, y + 10, (width) / 2, (int) (HEIGHT * 0.4));
    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    public URL getMapURL() throws MalformedURLException {
        StringBuilder builder = new StringBuilder();
        builder.append("http://maps.googleapis.com/maps/api/staticmap?center=");
        builder.append("47+22'+8+32'");
        builder.append("&zoom=13&size=");

        int width = WIDTH;
        int height = (int) (0.6 * HEIGHT);
        double maxSize = 640.0;
        if (WIDTH > maxSize) {
            double ratio = maxSize / WIDTH;
            width *= ratio;
            height *= ratio;
            if (height > maxSize) {
                ratio = maxSize / height;
                width *= ratio;
                height *= ratio;
            }
        } else if ((0.6 * HEIGHT) > maxSize) {
            double ratio = maxSize / (0.6 * HEIGHT);
            width *= ratio;
            height *= ratio;
        }

        builder.append(width);
        builder.append("x");
        builder.append(height);
        builder.append("&sensor=false");
        System.out.println(builder.toString());
        return new URL(builder.toString());
    }

    private void drawPhotos(Graphics2D g, int x, int y, int width, int height, int padding) {
        int numImagesAcross = width / 75;
        int numImagesDown = height / 75;
        int imgIndex = 0;

        for (int i = 0; i < numImagesDown; i++) {
            for (int j = 0; j < numImagesAcross; j++) {
                int imageX = x + j * 75;
                int imageY = y + i * 75;

                if (images.size() > imgIndex) {
                    g.drawImage(images.get(imgIndex), imageX, imageY, 66, 66, this);
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
            "http://farm4.static.flickr.com/3333/5834884484_a13724a597_s.jpg",
            "http://farm4.static.flickr.com/3238/5834314825_50e52abcec_s.jpg",
            "http://farm3.static.flickr.com/2431/5834316171_9439834e0e_s.jpg",
            "http://farm6.static.flickr.com/5085/5228149325_54f1b36397_s.jpg",
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

    private void drawTexts(Graphics2D g2d, int x, int y, int width, int height) {
        int lineHeight = 25;
        int initialY = 15;
        int maxLines = height / lineHeight;


        ArrayList<Integer> lines = new ArrayList<Integer>();
        lines.addAll(dateMap.descendingKeySet());
        for (int i = 0; i < maxLines; i++) {
            if (lines.size() > 0) {
                Integer firstLineIndex = lines.remove(0);
                String first = textMap.get(firstLineIndex);
                Calendar match = dateMap.get(firstLineIndex);

                int lineX = x + 10;
                int lineY = y + initialY + lineHeight * i;
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Georgia", Font.PLAIN, 20));
                g2d.drawString(first, lineX, lineY);

                if (match != null) {
                    g2d.setColor(Color.GRAY);
                    g2d.setFont(new Font("Georgia", Font.PLAIN, 16));
                    FontMetrics metrics = g2d.getFontMetrics();

                    String dateString = calString(match);
                    int stringWidth = metrics.stringWidth(dateString);

                    g2d.drawString(dateString, width + x - (stringWidth + 10), lineY);
                }
            }
        }
    }

    private String calString(Calendar cal) {
        StringBuilder builder = new StringBuilder();

        long currTime = System.currentTimeMillis();
        int diff = (int) (currTime - cal.getTimeInMillis());

        int diffSeconds = diff / 1000;
        int diffMinutes = diffSeconds / 60;
        int diffHours = diffMinutes / 60;
        int diffDays = diffHours / 24;

        if (diffSeconds <= 59) {
            builder.append("Just now");
        } else if (diffMinutes <= 59) {
            builder.append(diffMinutes);
            builder.append(" min ago");
        } else if (diffHours <= 23) {
            builder.append(diffHours);
            builder.append(" hours ago");
        } else {
            builder.append(diffDays);
            builder.append(" days ago");
        }


        return builder.toString();
    }
}
