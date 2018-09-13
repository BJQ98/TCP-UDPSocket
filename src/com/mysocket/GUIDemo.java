package com.mysocket;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIDemo extends JFrame implements ActionListener{
    private JTextField user_id;
    private JTextField user_psw;
    public static void main(String[] argv) {
         new GUIDemo();
    }
    public GUIDemo(){
        Init();
    }
    public void Init() {
        //JFrame frame = new JFrame("QQ登录");
        this.setTitle("test");
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


        this.add(jb1);
        this.add(jb2);
        this.add(user_id);
        this.add(user_psw);
        jb1.setBounds(280, 20, 60, 20);
        jb2.setBounds(280, 50, 60, 20);
        user_id.setBounds(10, 20, 200, 20);
        user_psw.setBounds(10, 50, 200, 20);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) { //
        System.out.println(e.getActionCommand());
        String get_user_id = user_id.getText().trim();
        System.out.println(get_user_id);
    }
}

