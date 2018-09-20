package com.mysocket;

import java.awt.event.*;
import java.io.EOFException;
import javax.swing.*;

public class TCPClientGUI extends JFrame implements ActionListener{
    private TCPClient my_TCP_Client;
    private JTextField user_id;
    private JTextField user_psw;
    private JTextArea outlook;
    private JTextField my_message;
    public static void main(String[] argv){
         new TCPClientGUI();
    }
    public TCPClientGUI(){
        super("TCP客户端");
        this.setLocation(300, 300);
        this.setSize(400, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(null);//修改布局管理
        JButton jb1 = new JButton("连接");
        jb1.addActionListener(this);
        JButton jb2 = new JButton("断开");
        jb2.addActionListener(this);
        JButton jb3 = new JButton("发送文件");
        jb3.addActionListener(this);
        JButton jb4 = new JButton("发送消息");
        jb4.addActionListener(this);
        user_id = new JTextField(20);
        user_psw = new JTextField(20);
        my_message = new JTextField();
        outlook = new JTextArea();
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);
        this.add(user_id);
        this.add(user_psw);
        this.add(my_message);
        JScrollPane sp = new JScrollPane();
        getContentPane().add(sp);
        sp.setViewportView(outlook);
        sp.setBounds(35,80,190,250);
        jb1.setBounds(280, 20, 60, 20);
        jb2.setBounds(280, 50, 60, 20);
        jb3.setBounds(280,80,60,20);
        user_id.setBounds(30, 20, 200, 20);
        user_psw.setBounds(30, 50, 200, 20);
        my_message.setBounds(260,110,100,40);
        jb4.setBounds(280,160,60,20);
        outlook.setBounds(35,80,190,250);
        outlook.setEditable(false);
        outlook.setLineWrap(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){ //
        try {
            if (e.getActionCommand().equals("连接")) {
                if(my_TCP_Client ==null|| my_TCP_Client.myClient.isClosed()) {
                    my_TCP_Client = new TCPClient("127.0.0.1", 8899);
                    String get_user_id = user_id.getText().trim();
                    String get_user_pwd = user_psw.getText().trim();
                    my_TCP_Client.set_user_info(get_user_id, get_user_pwd);
                    String rec = my_TCP_Client.login();
                    outlook.append(rec + '\n');
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "已经存在连接或服务已关闭",
                            "提示消息",JOptionPane.PLAIN_MESSAGE);
                }
            }
            else if(e.getActionCommand().equals("断开")){
                my_TCP_Client.unConnet();
                JOptionPane.showMessageDialog(null,"成功断开连接",
                        "提示消息",JOptionPane.PLAIN_MESSAGE);
            }
            else if(e.getActionCommand().equals("发送文件")) {
                if(my_TCP_Client ==null|| my_TCP_Client.myClient.isClosed()) {
                    JOptionPane.showMessageDialog(null,"未连接",
                            "提示消息", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    String filepath,filename;
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    int returnVal = fileChooser.showOpenDialog(fileChooser);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        filepath = fileChooser.getSelectedFile().getAbsolutePath();
                        filename = fileChooser.getSelectedFile().getName();
                        my_TCP_Client.sendFile(filename,filepath);
                        outlook.append("成功发送文件" + filename + '\n');
                    }

                }
            }
            else if(e.getActionCommand().equals("发送消息")){
                if(my_TCP_Client ==null|| my_TCP_Client.myClient.isClosed()) {
                    JOptionPane.showMessageDialog(null,"未连接",
                            "提示消息", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    String out = my_message.getText().trim();
                    String rec = my_TCP_Client.message(out);
                    outlook.append(rec + '\n');
                }
            }
        }catch (EOFException ex1){
            JOptionPane.showMessageDialog(null,"登陆消息不正确",
                    "提示消息", JOptionPane.PLAIN_MESSAGE);
        }catch (Exception ex2){
            JOptionPane.showMessageDialog(null,"未连接",
                    "提示消息", JOptionPane.PLAIN_MESSAGE);
        }
    }
}

