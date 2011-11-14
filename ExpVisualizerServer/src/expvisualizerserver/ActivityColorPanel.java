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
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JColorChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ActivityColorPanel extends JPanel {
  private Activity activity;
  private String activityName;
  private int heightAdjuster = 1;
  private JFrame frame;
  private int y;
  private int x;
  private int width;
  private int height;
  private ColorChangeButton colorChangeButton;
  private JButton test;
  public static final int WIDTH = ExpVisualizerServer.WIDTH;
  public static final int HEIGHT = ExpVisualizerServer.HEIGHT;
  private GradientPaint BACKGROUND_COLOR;

  public ActivityColorPanel(Activity activity, int x, int y, int width, int height, ExpVisualizerServer frame) {
    this.activity = activity;
    this.activityName = activity.parseActivityType();
    this.frame = frame;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.setBounds(x, y, width, height);
    this.BACKGROUND_COLOR = new GradientPaint(width / 2, height, new Color(50, 50, 50), width / 2, 0, new Color(75, 75, 75), true);
    this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    this.setLayout(null);    
    colorChangeButton = new ColorChangeButton(activity, width, height, frame);
    this.add(colorChangeButton);
  }

  public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, this.width, this.height);

        g2d.setFont(new Font("Futura", Font.PLAIN, this.height / 2));
        FontMetrics metrics = g.getFontMetrics();
        int stringWidth = metrics.stringWidth(this.activityName);
        int y = (int) ((this.height - (this.height / 3)) * 1.2); 

        g.setColor(Color.WHITE);
        g.drawString(activityName, 10, y);

        g2d.setPaint(colorChangeButton.getColor());
        g2d.fillRect(this.width - (this.width / 3), 0, 75, (this.getHeight()));
  }


  public Activity getActivity() {
    return this.activity;
  }

}
