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
                byte[] data_toclient = "我在响应你！".getBytes();
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
                File file = new File("Test1.txt");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(file_content);
                System.out.println("写入成功");
                fos.close();
                byte[] data_toclient = "成功上传文件！".getBytes();
                send_packet = new DatagramPacket(data_toclient, data_toclient.length,
                        packet.getAddress(), packet.getPort());
                socket.send(send_packet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //socket.close();
    }
}
