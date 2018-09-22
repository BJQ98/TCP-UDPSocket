package com.mysocket;

import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.DataInputStream;
import java.net.*;


public class TCPServerGUI extends JFrame implements ActionListener{
    public static JTextArea outlook;
    public static JTextArea client_count;
    public static int count;
    private JLabel mylabel;
    private static ServerSocket welcomeSocket;
    public TCPServerGUI()throws Exception{
        super("TCP服务端");
        welcomeSocket = new ServerSocket(8899);
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
        mylabel = new JLabel("当前在线人数");
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
    public static void main(String argv[]) throws Exception {
        new TCPServerGUI();
        while(true) {
            Socket server = welcomeSocket.accept();//准备接受客户端的数据
            System.out.println("新的客服建立了");
            System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
            System.out.println("当前所处的服务器地址为" + server.getLocalSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());//获取输入数据的容器
            String info_str = in.readUTF();
            JSONObject info = JSONObject.fromObject(info_str);
            String user_id = info.getString("ID");
            String user_password = info.getString("password");
            if ((user_id.equals("admin1") && user_password.equals("123")) || (user_id.equals("admin2") && user_password.equals("123"))) {
                outlook.append(user_id + "成功登陆！" + '\n');
                count++;
                client_count.setText(String.valueOf(count));
                TCPServerthread mythread = new TCPServerthread(server, user_id);
                mythread.start();
            }
            else {
                server.close();
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){ //
        try {
            if(e.getActionCommand().equals("关闭服务")){
                Socket empty = new Socket("127.0.0.1",8899);
                welcomeSocket.close();
                client_count.setText("0");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
