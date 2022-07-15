package yarn_monitor;

import java.util.HashMap;
import java.util.Map;

public class YarnEntrance {

    public static Map<String, String> tags = new HashMap<>();
    public static Map<String, Object> fields1 = new HashMap<>();
    public static Map<String, Object> fields2 = new HashMap<>();


    public static void main(String[] args) {

        while(true){

            Influxdb influxdb = new Influxdb("http://10.60.79.103:8086",
                    "admin",
                    "admin",
                    "monitor");

            tags.put("company",args[0]);

            YarnClusterInfo.YARN_CLUSTER_INFO_JSON(args[1],args[2]);
            YarnClusterMetrics.YARN_CLUSTER_METRICS_JSON(args[1],args[2]);
            YarnClusterJmx.YARN_CLUSTER_JMX_JSON(args[1],args[2]);

            System.out.println(fields1);
            System.out.println(fields2);

            for (int i = 0; i < 80; i++) {
                System.out.print("*");
            }
            System.out.println(" ");

            //创建inlfuxdb客户端对象
            Infludb_client infludb_client = new Infludb_client(influxdb.getUsername(), influxdb.getPassword(), influxdb.getOpenurl(), influxdb.getDatabase());
            //调用setup方法，初始化influxdb链接
            Infludb_client setup = infludb_client.setup();

            setup.insert(tags, fields1, "hdfs_yarn_monitor");
            setup.insert(tags, fields2, "hdfs_yarn_monitor");
            setup.close();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
