package com.mysocket;

import java.io.*;
import java.net.*;
import net.sf.json.*;


public class Server {
    public static void main(String argv[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6799);
        while(true) {
            Socket server = welcomeSocket.accept();//准备接受客户端的数据
            System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
            System.out.println("当前所处的服务器地址为" + server.getLocalSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());//获取输入数据的容器

            String info_str = in.readUTF();
            JSONObject info = JSONObject.fromObject(info_str);
            String user_id = info.getString("ID");
            String user_password = info.getString("password");

            DataOutputStream out = new DataOutputStream(server.getOutputStream());//发送给客户端的数据的容器
            out.writeUTF("谢谢连接我：" + server.getLocalSocketAddress() + "\nGoodbye!");




            DataInputStream dis = new DataInputStream(server.getInputStream());

            // 文件名和长度
            File file = new File("test_rec.txt");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int length = 0;
            while((length = dis.read(bytes, 0, bytes.length)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
            }
            server.close();
        }
    }
}
