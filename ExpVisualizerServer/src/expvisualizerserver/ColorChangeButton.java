package expvisualizerserver;

import java.awt.AlphaComposite;
import java.util.List;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;


/**
*
* @author dpflan
*
*/

public class ColorChangeButton extends JButton {
  private Activity activity;
  private String activityName;
  private Paint color;

  public ColorChangeButton(Activity activity, int width, int height, ExpVisualizerServer frame) {
    super("Edit Color");
    this.activity = activity;
    this.color = ActivityColorMap.colorForActivity(activity.getActivityType());
    this.activityName = activity.parseActivityType();
    this.setPreferredSize(new Dimension(115, height));
    this.setBounds(width - 120, 0 , 115, height);
    this.addActionListener(frame);

  }

  public void editColor() {
    Color c = JColorChooser.showDialog(null, "Select a Color", Color.BLACK);
    if (c != null) {
      this.color = c;
      this.activity.setColor(c);
    }
  }

  public Paint getColor() {
    return this.color;
  }
}
