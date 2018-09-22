package com.mysocket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import net.sf.json.*;
import javax.swing.*;

public class UDPClientGUI extends JFrame implements ActionListener {
    private UDPClient my_UDP_Client;
    private JTextArea outlook;
    private JTextField my_message;
    public static void main(String[] argv)throws Exception{
        new UDPClientGUI();
    }
    public UDPClientGUI() throws Exception{
        super("UDP客户端");
        my_UDP_Client = new UDPClient();
        this.setLocation(300, 300);
        this.setSize(400, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(null);//修改布局管理
        JButton jb3 = new JButton("发送消息");
        jb3.addActionListener(this);
        JButton jb4 = new JButton("发送文件");
        jb4.addActionListener(this);
        my_message = new JTextField();
        outlook = new JTextArea();
        this.add(jb3);
        this.add(jb4);
        this.add(my_message);
        JScrollPane sp = new JScrollPane();
        getContentPane().add(sp);
        sp.setViewportView(outlook);
        sp.setBounds(35, 20, 190, 310);
        jb3.setBounds(260, 90, 100, 50);
        my_message.setBounds(240, 20, 140, 60);
        jb4.setBounds(260, 150, 100, 50);
        outlook.setBounds(35, 20, 190, 310);
        outlook.setEditable(false);
        outlook.setLineWrap(true);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){ //
       // try {
            if (e.getActionCommand().equals("发送消息")) {

            }
            else if(e.getActionCommand().equals("发送文件")){

            }

//        }catch (EOFException ex1){
//            JOptionPane.showMessageDialog(null,"登陆消息不正确",
//                    "提示消息", JOptionPane.PLAIN_MESSAGE);
//        }catch (Exception ex2){
//            JOptionPane.showMessageDialog(null,"未连接",
//                    "提示消息", JOptionPane.PLAIN_MESSAGE);
//        }
    }
}
