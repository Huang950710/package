package hdfs_rpc;

import java.io.IOException;
import java.util.Map;

public class hdfs_rpc {

    public static void main(String[] args) throws IOException, InterruptedException {
        while(true){
            //参数传递：http://10.60.79.100:50070/webhdfs/v1/huangchanghai?op=GETCONTENTSUMMARY
            Influxdb influxdb = new Influxdb("http://10.60.79.103:8086",
                    "admin",
                    "admin",
                    "monitor");

            //创建http解析方法对象
            HttpsClient HC = new HttpsClient();
            //获取链接对应的块信息
            String message = HC.HttpsClient(args[0]);

            if (Master_Json.master(message).equals("active")){

                String url = args[0];

                // 获取jmx数据
                String message1 = HC.HttpsClient(url);

                //创建 Json 解析方法对象
                Json jn = new Json();
                //调用 Json 解析方法
                jn.rpc_analysis(message1,args[2]);

                Map<String, String> tags = jn.tags;
                Map<String, Object> fields = jn.fields;

                //创建inlfuxdb客户端对象
                Infludb_client infludb_client = new Infludb_client(influxdb.getUsername(), influxdb.getPassword(), influxdb.getOpenurl(), influxdb.getDatabase());
                //调用setup方法，初始化influxdb链接
                Infludb_client setup = infludb_client.setup();

                setup.insert(tags, fields, "hdfs_rpc_monitor");
                setup.close();

            }else{

                String url = args[1];

                // 获取jmx数据
                String message1 = HC.HttpsClient(url);

                //创建 Json 解析方法对象
                Json jn = new Json();
                //调用 Json 解析方法
                jn.rpc_analysis(message1,args[2]);

                Map<String, String> tags = jn.tags;
                Map<String, Object> fields = jn.fields;

                //创建inlfuxdb客户端对象
                Infludb_client infludb_client = new Infludb_client(influxdb.getUsername(), influxdb.getPassword(), influxdb.getOpenurl(), influxdb.getDatabase());
                //调用setup方法，初始化influxdb链接
                Infludb_client setup = infludb_client.setup();

                setup.insert(tags, fields, "hdfs_rpc_monitor");
                setup.close();
            }

            Thread.sleep(10000);
        }
    }
}
