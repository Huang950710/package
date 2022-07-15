package hdfs_monitor;

import java.io.IOException;
import java.util.Map;

public class hdfs_block {

    public static void main(String[] args) throws IOException, InterruptedException {

        //参数传递：http://10.60.79.100:50070/webhdfs/v1/huangchanghai?op=GETCONTENTSUMMARY
        //http://52.74.83.89:50070/webhdfs/v1/bitmarket?op=GETCONTENTSUMMARY
        //http://52.74.83.89:50070/webhdfs/v1/bitmarket?op=GETCONTENTSUMMARY 互金 /bitmarket
        Influxdb influxdb = new Influxdb("http://10.60.79.103:8086",
                "admin",
                "admin",
                "monitor");

        String master1 = args[0];
        String master2 = args[2];

        //创建http解析方法对象
        HttpsClient HC = new HttpsClient();
        //获取链接对应的块信息
        String message = HC.HttpsClient(master1);

        // 主节点判断
        if (Master_Json.master(message).equals("active")){
            System.out.println("52.74.83.89 为主节点");
            String url = args[1];
            System.out.println(url);

            String message1 = HC.HttpsClient(url);

            //创建 Json 解析方法对象
            Json jn = new Json();
            //调用 Json 解析方法
            jn.analysis(message1, args[4], args[5]);

            Map<String, String> tags = jn.tags;
            Map<String, Object> fields = jn.fields;

            //创建inlfuxdb客户端对象
            Infludb_client infludb_client = new Infludb_client(influxdb.getUsername(), influxdb.getPassword(), influxdb.getOpenurl(), influxdb.getDatabase());
            //调用setup方法，初始化influxdb链接
            Infludb_client setup = infludb_client.setup();

            setup.insert(tags, fields, "hdfs_block_monitor");
            setup.close();

        }else{
            System.out.println("13.213.130.248 为主节点");
            String url = args[3];
            System.out.println(url);

            String message1 = HC.HttpsClient(url);

            //创建 Json 解析方法对象
            Json jn = new Json();
            //调用 Json 解析方法
            jn.analysis(message1, args[4], args[5]);

            Map<String, String> tags = jn.tags;
            Map<String, Object> fields = jn.fields;

            //创建inlfuxdb客户端对象
            Infludb_client infludb_client = new Infludb_client(influxdb.getUsername(), influxdb.getPassword(), influxdb.getOpenurl(), influxdb.getDatabase());
            //调用setup方法，初始化influxdb链接
            Infludb_client setup = infludb_client.setup();

            setup.insert(tags, fields, "hdfs_block_monitor");
            setup.close();

        }
    }
}
