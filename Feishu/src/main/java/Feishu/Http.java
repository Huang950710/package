package Feishu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Http {

    /**
     * args[0] : Webhook 连接地址
     * args[1] : 告警来源（KafkaEagle、Grafana）
     */
    public static void main(String[] args) throws IOException {

        /**
         * 创建服务器端Socket对象（Socket）
         * ServerSocket(int port) 创建绑定到指定端口的服务器套接字
         */
        ServerSocket serverSocket  = new ServerSocket(654);

        //用线程池来做socket操作，这样就不用每次new Thread，会复用创建的Thread
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        while (true) {

            Socket socket = serverSocket.accept();//阻塞到有连接访问，拿到socket
            System.out.println("get socket from:"+socket.hashCode());
            cachedThreadPool.execute(() -> {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = socket.getInputStream();//拿到in out putStream，就想干啥干啥了
                    outputStream = socket.getOutputStream();

/*
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line=reader.readLine()) != null) {
                        if (line.startsWith("{")) {
                            Grafana_To_Feishu.send(line);
                            System.out.println(line);
                        }
                    }
*/

                    byte[] bytes = new byte[inputStream.available()];
                    int result = inputStream.read(bytes);//读取请求的所有内容，实质是好几行String，里面存有http信息
                    if (result != -1){
                        System.out.println(new String(bytes));
                        String messages = new String(bytes);
                        String substring = messages.substring(0, messages.indexOf("{"));

                        /**
                         * 判断传入的参数是 Grafana 还是 KafkaEagle
                         */
                        if (args[1].equals("Grafana"))
                        {
                            Grafana_To_Feishu.send(messages.substring(substring.length(),messages.length()),args[0]);
                        } else if (args[1].equals("KafkaEagle")){
                            LiveMe_Monitor_To_Feishu.send(messages.substring(substring.length(),messages.length()),args[0]);
                        } else {
                            System.out.println("传入参数不存在");
                        }
                    }
                    //也可用bufferreader readline，主要目的是拿到请求方法是GET POST PATCH还是其他http请求方式
                    //拿到path进行路由，看server端决定给他返回什么，这边可以封装一个router,携带一个由解析intputStream的来的自定义httprequest

                    String body = "<h1>Request received</h1>";//可以是html文件，读文本文件进来就行了
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: " + body.getBytes().length + "\r\n" +
                            "Content-Type: text/html; charset-utf-8\r\n" +
                            "\r\n" +
                            body + "\r\n";
                    outputStream.write(response.getBytes());//按照协议，将返回请求由outputStream写入
                    outputStream.flush();

                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();//异常捕获
                }
            });
        }
    }
}
