package expvisualizerserver;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.*;

public class PacketListener extends Thread {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected List<Pulse> pulses;

    public PacketListener(List<Pulse> pulses) throws IOException {
	this("PacketListener", pulses);
    }

    public PacketListener(String name, List<Pulse> pulses) throws IOException {
        super(name);
        this.pulses = pulses;
        socket = new DatagramSocket(4445);
    }

    public void run() {
        while (true) {
            try {
                byte[] buf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
                
                String[] pieces = received.split(":");
                String activity = pieces[0];
                int length = Integer.parseInt(pieces[1]);
                
                if(received.equalsIgnoreCase("blue")) {
                    pulses.add(new Pulse(Color.BLUE));
                } else if(received.equalsIgnoreCase("red")) {
                    pulses.add(new Pulse(Color.RED));  
                } else if(received.equalsIgnoreCase("green")) {
                    pulses.add(new Pulse(Color.GREEN, 1000*2));  
                } else if(received.equalsIgnoreCase("white")) {
                    pulses.add(new Pulse(Color.WHITE));  
                } else if(received.equalsIgnoreCase("yellow")) {
                    pulses.add(new Pulse(Color.YELLOW));  
                } else if(received.equalsIgnoreCase("pink")) {
                    pulses.add(new Pulse(Color.PINK));  
                } else if(received.equalsIgnoreCase("clear")) {
                    pulses.add(new Pulse(VisPanel.BACKGROUND_COLOR));  
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}