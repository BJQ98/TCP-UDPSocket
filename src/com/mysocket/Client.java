package com.mysocket;

import java.io.*;
import java.net.*;
import net.sf.json.*;


public class Client {
    public Socket myClient;
    private String user_ID;
    private String user_PWD;
    Client(String host, int port) throws Exception{
        myClient = new Socket(host, port);
    }
    public void set_user_info(String ID, String PWD){
        user_ID = ID;
        user_PWD = PWD;
    }
    public String login()throws Exception{
        OutputStream outToServer = myClient.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        JSONObject info = new JSONObject();
        info.put("ID", user_ID);
        info.put("password", user_PWD);
        out.writeUTF(info.toString());//传送给服务端的数据
        InputStream inFromServer = myClient.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);//服务器响应数据容器
        return in.readUTF();
    }
    public void unConnet()throws Exception{
        myClient.close();
    }
    public static void main(String argv[]) throws Exception {
        Client client = new Client("127.0.0.1", 8799);
        client.set_user_info("bjq","123");
        client.login();
        int length = 0;
        long progress = 0;
        File file = new File("test.txt");
        FileInputStream fis = new FileInputStream(file);
        DataOutputStream dos = new DataOutputStream(client.myClient.getOutputStream());
        byte[] sendBytes = new byte[1024];
        while((length = fis.read(sendBytes, 0, sendBytes.length)) != -1) {
            dos.write(sendBytes, 0, length);
            dos.flush();
            progress += length;
            System.out.print("| " + (100*progress/file.length()) + "% |");
        }
        client.myClient.close();
    }

}
