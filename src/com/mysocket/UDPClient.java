package com.mysocket;

import org.apache.commons.lang.ArrayUtils;

import java.io.*;
import java.net.*;

public class UDPClient {
    private InetAddress address;
    private int port;
    public UDPClient()throws Exception{
        address = InetAddress.getByName("127.0.0.1");
        port = 8900;
    }
    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        int i = 0;
        for (byte bt : bt1) {
            bt3[i] = bt;
            i++;
        }
        for(byte bt: bt2){
            bt3[i]=bt;
            i++;
        }
        return bt3;
    }
    String sendmessage(String _message)throws Exception{
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(100);//防止未连接时阻塞
        byte[] data = _message.getBytes();
        byte[] header = "消息".getBytes();
        byte[] data_send = byteMerger(header,data);
        DatagramPacket packet = new DatagramPacket(data_send, data_send.length, address, port);
        socket.send(packet);
        byte[] data_rec = new byte[1024];
        DatagramPacket packet2 = new DatagramPacket(data_rec, data_rec.length);
        socket.receive(packet2);
        String info = new String(data_rec, 0, packet2.getLength());
        socket.close();
        return info;
    }
    String sendfile(String filename, String filepath)throws Exception{
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(100);
        byte[] header = "文件".getBytes();
        int filename_length = filename.getBytes().length;
        byte[] name = filename.getBytes();
        byte bytename_length =(byte)filename_length;
        byte[] temp = ArrayUtils.add(header, bytename_length);
        byte[] data_send = byteMerger(temp, name);
        FileInputStream fis = new FileInputStream(new File(filepath));
        byte []bs;//缓冲区
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        byte[] b = new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        bs = bos.toByteArray();//转成二进制数组
        data_send = byteMerger(data_send, bs);
        System.out.println(data_send.length);
        DatagramPacket packet = new DatagramPacket(data_send, data_send.length, address, port);
        socket.send(packet);
        byte[] data_rec = new byte[1024];
        DatagramPacket packet2 = new DatagramPacket(data_rec, data_rec.length);
        socket.receive(packet2);
        String info = new String(data_rec, 0, packet2.getLength());
        socket.close();
        return info;
    }
    public static void main(String[] args) throws Exception {
        UDPClient client = new UDPClient();
    }
}
