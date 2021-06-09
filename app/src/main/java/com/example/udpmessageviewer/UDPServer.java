package com.example.udpmessageviewer;

import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class UDPServer {
    private DatagramChannel channel;
    private TextView toWrite = null;
    private ArrayList<String> allMessages = new ArrayList<String>();
    public UDPServer() throws IOException {
        channel = DatagramChannel.open();
    }
    public void start(Integer portNumber, TextView editText) throws IOException {
        String IPAddress = Utils.getIPAddress();
        System.out.println("IP:" + IPAddress + ",PORT:" + portNumber);
        channel.socket().bind(new InetSocketAddress(IPAddress, portNumber));
        toWrite = editText;
    }

    public void onMessage() throws IOException {
        while(!channel.socket().isClosed()){
            ByteBuffer buf = ByteBuffer.allocate(453);
            buf.clear();
            channel.receive(buf);
            buf.flip();
            byte[] data = new byte[buf.limit()];
            buf.get(data);
            String message = new String(data);
            System.out.println("get:" + message);
            allMessages.add(0, message + "\n");
            if(allMessages.size() > 10){
                allMessages.remove(9);
            }
            String viewMessage = "";
            for(String s: allMessages){
                viewMessage += s;
            }
            toWrite.setText(viewMessage);

        }
    }


}
