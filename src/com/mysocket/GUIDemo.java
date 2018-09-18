package com.mysocket;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIDemo extends JFrame implements ActionListener{
    private Client my_Client;
    private JTextField user_id;
    private JTextField user_psw;
    private JTextArea outlook;
    public static void main(String[] argv) throws Exception{
         new GUIDemo();
    }
    public GUIDemo() throws Exception{
        super("客户端");
        this.setLocation(300, 300);
        this.setSize(400, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(null);//修改布局管理
        JButton jb1 = new JButton("连接");
        jb1.addActionListener(this);
        JButton jb2 = new JButton("断开");
        jb2.addActionListener(this);
        user_id = new JTextField(20);
        user_psw = new JTextField(20);
        outlook = new JTextArea();
        this.add(jb1);
        this.add(jb2);
        this.add(user_id);
        this.add(user_psw);
        JScrollPane sp = new JScrollPane();
        getContentPane().add(sp);
        sp.setViewportView(outlook);
        sp.setBounds(35,80,190,250);
        jb1.setBounds(280, 20, 60, 20);
        jb2.setBounds(280, 50, 60, 20);
        user_id.setBounds(30, 20, 200, 20);
        user_psw.setBounds(30, 50, 200, 20);
        outlook.setBounds(35,80,190,250);
        outlook.setEditable(false);
        outlook.setLineWrap(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){ //
        try {
            if (e.getActionCommand().equals("连接")) {
                if(my_Client==null||my_Client.myClient.isClosed()) {
                    my_Client = new Client("127.0.0.1", 8799);
                    String get_user_id = user_id.getText().trim();
                    String get_user_pwd = user_psw.getText().trim();
                    my_Client.set_user_info(get_user_id, get_user_pwd);
                    String rec = my_Client.login();
                    outlook.append(rec + '\n');
                }
                else {
                    JOptionPane.showMessageDialog(null,"已经存在连接，请先断开",
                            "提示消息",JOptionPane.PLAIN_MESSAGE);
                }

            }
            else if(e.getActionCommand().equals("断开")){
                my_Client.unConnet();
                JOptionPane.showMessageDialog(null,"成功断开连接",
                        "提示消息",JOptionPane.PLAIN_MESSAGE);
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }
}

