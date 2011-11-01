/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expvisualizerclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author taylor
 */
public class Main implements ActionListener {
    JTextField server;
    JTextField message;
    JButton send;
    
    public Main() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        server = new JTextField("server", 10);
        message = new JTextField("message", 5);
        send = new JButton("send");
        
        send.addActionListener(this);
        
        panel.add(server);
        panel.add(message);
        panel.add(send);
        
        frame.add(panel);
        
        frame.pack();
        frame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            DatagramSocket socket = new DatagramSocket();

            byte[] buf = message.getText().getBytes();
            InetAddress address = InetAddress.getByName(server.getText());
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            System.out.println("sent");
        
            socket.close();
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
    
    
    public static void main(String[] args) {
        new Main();
    }
}
