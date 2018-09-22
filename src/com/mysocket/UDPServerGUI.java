package com.mysocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class UDPServerGUI extends JFrame implements ActionListener{
    public static JTextArea outlook;
    public static JTextArea client_count;
    public static int count;
    private JLabel mylabel;
    private static DatagramSocket socket;
    public UDPServerGUI()throws Exception{
        super("UDP服务端");
        socket = new DatagramSocket(8900);
        count = 0;
        this.setLocation(300, 300);
        this.setSize(400, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(null);//修改布局管理
        JButton jb1 = new JButton("关闭服务");
        jb1.addActionListener(this);
        client_count = new JTextArea();
        outlook = new JTextArea();
        mylabel = new JLabel("服务器连接次数");
        this.add(jb1);
        this.add(client_count);
        this.add(mylabel);
        JScrollPane sp = new JScrollPane();
        getContentPane().add(sp);
        sp.setViewportView(outlook);
        sp.setBounds(35,40,190,290);
        jb1.setBounds(250, 150, 100, 60);
        mylabel.setBounds(260,50,100,30);
        client_count.setBounds(270,80,60,30);
        client_count.setText("0");
        client_count.setFont(new Font("黑体",Font.BOLD,26));
        outlook.setBounds(35,80,190,250);
        outlook.setEditable(false);
        outlook.setLineWrap(true);
        client_count.setEditable(false);
        this.setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new UDPServerGUI();
        DatagramPacket packet = null;
        byte[] data = null;
        System.out.println("***服务器端启动，等待发送数据***");
        while(true){
            data = new byte[1024];//创建字节数组，指定接收的数据包的大小
            packet = new DatagramPacket(data, data.length);
            socket.receive(packet);//此方法在接收到数据报之前会一直阻塞
            Thread thread = new Thread(new UDPServerthread(socket, packet));
            thread.start();
            count++;
            client_count.setText(String.valueOf(count));
            InetAddress address = packet.getAddress();
            System.out.println("当前客户端的IP为："+address.getHostAddress());
        }

    }
    @Override
    public void actionPerformed(ActionEvent e){ //
        //try {
            if(e.getActionCommand().equals("关闭服务")){

            }
//        }catch (Exception ex){
//            System.out.println(ex.getMessage());
//        }
    }
}
