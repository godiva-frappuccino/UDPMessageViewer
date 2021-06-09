package com.example.udpmessageviewer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class Utils {
    public static String getIPAddress() throws IOException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while(interfaces.hasMoreElements()){
            NetworkInterface network = interfaces.nextElement();
            Enumeration<InetAddress> addresses = network.getInetAddresses();
            while(addresses.hasMoreElements()){
                String address = addresses.nextElement().getHostAddress();
                if(!"127.0.0.1".equals(address) && !"0.0.0.0".equals(address) && isIPv4(address)){
                    return address;
                }
            }
        }
        return "127.0.0.1";
    }
    private static boolean isIPv4(String str) {
        return Pattern.matches("((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])([.](?!$)|$)){4}", str);
    }

}
