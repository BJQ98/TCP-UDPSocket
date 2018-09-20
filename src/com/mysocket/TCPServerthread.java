package com.mysocket;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.*;

public class TCPServerthread extends Thread{
    Socket server = null;
    private String myuserid;

    public TCPServerthread(Socket socket, String userid) {
        this.server = socket;
        myuserid = userid;
    }
    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(server.getOutputStream());//发送给客户端的数据的容器
            out.writeUTF("成功连接" + server.getLocalSocketAddress() + "\n");
            BufferedReader ioin = new BufferedReader(new InputStreamReader(server.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            String cmd;
            while ((cmd = ioin.readLine()) != null) {
                if (cmd.equals("消息")) {
                    String server_message = ioin.readLine();
                    writer.write("已收到消息" + '\n');
                    TCPServerGUI.outlook.append(myuserid+':'+server_message+'\n');
                    writer.flush();
                } else if (cmd.equals("发送文件")) {
                    String filename = ioin.readLine();
                    System.out.println(filename);
                    TCPServerGUI.outlook.append("成功接收" + myuserid+"的"+filename + '\n');
                    DataInputStream dis = new DataInputStream(server.getInputStream());
                    File file = new File(filename);
                    OutputStream fos = new FileOutputStream(file);
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    while ((length = dis.read(bytes, 0, bytes.length)) != -1) {//
                        System.out.println(454);
                        fos.write(bytes, 0, length);
                    }
                }
            }
            if(TCPServerGUI.count>0) {
                TCPServerGUI.client_count.setText(String.valueOf(--TCPServerGUI.count));
            }
            TCPServerGUI.outlook.append(myuserid + "已经退出" + '\n');
            server.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
