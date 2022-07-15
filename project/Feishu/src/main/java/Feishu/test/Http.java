package Feishu.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Http {

    public static void main(String[] args) throws IOException {

        /**
         * 创建服务器端Socket对象（Socket）
         * ServerSocket(int port) 创建绑定到指定端口的服务器套接字
         */
        ServerSocket ss = new ServerSocket(654);


        while (true) {
            /**
             * Socket accept() 侦听要连接到此套接字并接受它
             */
            Socket s = ss.accept();

            /**
             * 接收数据
             */
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

            while (br.readLine() != null) {
                String message = br.readLine();
                if (message.startsWith("{")) {
                    ChatbotSend.send(message);
                    System.out.println(message);
                }
            }
        }
    }
}
