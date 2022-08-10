package Feishu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Http {

    /**
     * args[0] : Webhook 连接地址
     * args[1] : 告警来源（KafkaEagle、Grafana）
     */
    public static void main(String[] args) throws IOException {

        /**
         * 参数判断
         * 1.参数个数不等于3的，直接退出程序
         * 2.发送模块不存在的，直接退出程序
         */
        // **********************************************************************************
        if (args.length != 3) {
            System.out.println("参数个数错误");
            System.exit(1);
        }

        HashSet<String> module_set = new HashSet<>();
        module_set.add("Grafana");
        module_set.add("KafkaEagle");

        String webhook = args[0];
        String module = args[1];
        int port = Integer.parseInt(args[2]);

        if (!module_set.contains(module)){
            System.out.println(module + " 模块不存在");
            System.out.println("模块：" + module_set);
            System.exit(1);
        }
        // **********************************************************************************


        /**
         * 创建服务器端Socket对象（Socket）
         * ServerSocket(int port) 创建绑定到指定端口的服务器套接字
         */

        ServerSocket serverSocket  = new ServerSocket(port);

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
                        if (module.equals("Grafana"))
                        {
//                            Grafana_To_Feishu.send(messages.substring(substring.length(),messages.length()),args[0]);
                            Grafana_To_Feishu.send(messages.substring(substring.length(),messages.length()),webhook);
                        } else if (module.equals("KafkaEagle")){
//                            LiveMe_Monitor_To_Feishu.send(messages.substring(substring.length(),messages.length()),args[0]);
                            LiveMe_Monitor_To_Feishu.send(messages.substring(substring.length(),messages.length()),webhook);
                        } else {
                            System.out.println("传入参数不存在");
                            System.exit(1);
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
