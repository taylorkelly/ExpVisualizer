package expvisualizerserver;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.*;

public class PacketListener extends Thread {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected List<Activity> activities;

    public PacketListener(List<Activity> activities) throws IOException {
	this("PacketListener", activities);
    }

    public PacketListener(String name, List<Activity> activities) throws IOException {
        super(name);
        this.activities = activities;
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
                
                if(pieces.length == 2) {
                    activities.add(new Activity(pieces[0], Integer.parseInt(pieces[1])));
                } else if (pieces.length == 3) {
                    activities.add(new Activity(pieces[0], Integer.parseInt(pieces[1]), pieces[2]));                    
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}