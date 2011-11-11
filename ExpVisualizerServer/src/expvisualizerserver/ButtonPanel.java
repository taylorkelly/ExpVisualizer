package expvisualizerserver;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author taylor
 */
class ButtonPanel extends JPanel implements ActionListener {
    private List<Pulse> pulses;
    private JButton dead;
    private JButton green;
    private JButton red;
    private JButton blue;

    public ButtonPanel(List<Pulse> pulses) {
        this.pulses = pulses;
        
        dead = new JButton("Dead");
        green = new JButton("Green");
        red = new JButton("Red");
        blue = new JButton("Blue");
        
        dead.addActionListener(this);
        green.addActionListener(this);
        red.addActionListener(this);
        blue.addActionListener(this);
        
        this.add(dead);
        this.add(green);
        this.add(red);
        this.add(blue);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == dead) {
        } else if (ae.getSource() == green) {
            pulses.add(new Pulse(Color.GREEN));
        } else if (ae.getSource() == red) {
            pulses.add(new Pulse(Color.RED));
        } else if (ae.getSource() == blue) {
            pulses.add(new Pulse(Color.BLUE));
        }
    }
}
