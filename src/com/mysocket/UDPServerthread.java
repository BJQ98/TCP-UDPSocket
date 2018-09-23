package com.mysocket;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPServerthread extends Thread{
    DatagramSocket socket = null;
    DatagramPacket packet = null;
    public UDPServerthread(DatagramSocket socket,DatagramPacket packet) {
        this.socket = socket;
        this.packet = packet;
    }
    @Override
    public void run() {
        String info = null;
        DatagramPacket send_packet = null;
        try {
            String cmd = new String(packet.getData(),0,6);
            System.out.println(cmd);
            if(cmd.equals("消息")) {
                info = new String(packet.getData(), 6, packet.getLength() - 6);
                System.out.println(info);
                UDPServerGUI.outlook.append(packet.getSocketAddress()+":"+info+'\n');
                byte[] data_toclient = "收到消息！".getBytes();
                send_packet = new DatagramPacket(data_toclient, data_toclient.length,
                        packet.getAddress(), packet.getPort());
                socket.send(send_packet);
            }
            else if(cmd.equals("文件")){
                int filename_length = packet.getData()[6];
                String filename = new String(packet.getData(),7,filename_length);
                System.out.println(filename);
                System.out.println(packet.getLength());
                byte[] file_content = Arrays.copyOfRange(packet.getData(), 7+filename_length, packet.getLength());
                File file = new File(filename);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(file_content);
                UDPServerGUI.outlook.append("成功接收"+packet.getSocketAddress()+"的"+filename+'\n');
                fos.close();
                byte[] data_toclient = ("成功上传文件:"+filename).getBytes();
                send_packet = new DatagramPacket(data_toclient, data_toclient.length,
                        packet.getAddress(), packet.getPort());
                socket.send(send_packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
