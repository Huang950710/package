package yarn_monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class YarnClusterMetrics {

    public static void YARN_CLUSTER_METRICS_JSON(String IP1, String IP2){

        String url1="http://"+ IP1 +":8088/ws/v1/cluster/metrics";
        String url2="http://"+ IP2 +":8088/ws/v1/cluster/metrics";

        // 创建http解析方法对象
        HttpsClient HC = new HttpsClient();
        // 获取链接对应的块信息
        String message1 = null;
        String message2 = null;
        try {
            message1 = HC.HttpsClient(url1);
            message2 = HC.HttpsClient(url2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将一行 jSON 数据转换为 JSON 对象
        JSONObject jsonObject1 = JSON.parseObject(message1);
        JSONObject jsonObject2 = JSON.parseObject(message2);

        //因为 clusterInfo 在JSON数据中是集合{}，所有用 JSON 对象的 getJSONObject 来获取内容
        JSONObject clusterInfo1 = jsonObject1.getJSONObject("clusterMetrics");
        JSONObject clusterInfo2 = jsonObject2.getJSONObject("clusterMetrics");


        // app提交数量
        YarnEntrance.fields1.put("appsSubmitted",clusterInfo1.get("appsSubmitted"));
        // app完成数量
        YarnEntrance.fields1.put("appsCompleted",clusterInfo1.get("appsCompleted"));
        // app等待数量
        YarnEntrance.fields1.put("appsPending",clusterInfo1.get("appsPending"));
        // app的运行数量
        YarnEntrance.fields1.put("appsRunning",clusterInfo1.get("appsRunning"));
        // app失败数量
        YarnEntrance.fields1.put("appsFailed",clusterInfo1.get("appsFailed"));
        // app被kill的数量
        YarnEntrance.fields1.put("appsKilled",clusterInfo1.get("appsKilled"));
        // 以 MB 为单位的总内存量
        YarnEntrance.fields1.put("totalMB",clusterInfo1.get("totalMB"));
        // 保留的内存量（以 MB 为单位）
        YarnEntrance.fields1.put("reservedMB",clusterInfo1.get("reservedMB"));
        // 可用内存MB
        YarnEntrance.fields1.put("availableMB",clusterInfo1.get("availableMB"));
        // 挂起的容器数量
        YarnEntrance.fields1.put("containersPending",clusterInfo1.get("containersPending"));
        // 节点总数
        YarnEntrance.fields1.put("totalNodes",clusterInfo1.get("totalNodes"));
        // 活动节点数量
        YarnEntrance.fields1.put("activeNodes",clusterInfo1.get("activeNodes"));
        // 丢失节点数量
        YarnEntrance.fields1.put("lostNodes",clusterInfo1.get("lostNodes"));
        // 不正常节点数量
        YarnEntrance.fields1.put("unhealthyNodes",clusterInfo1.get("unhealthyNodes"));
        // 已退役节点数量
        YarnEntrance.fields1.put("decommissionedNodes",clusterInfo1.get("decommissionedNodes"));
        // 分配的容器数量
        YarnEntrance.fields1.put("containersAllocated",clusterInfo1.get("containersAllocated"));
        // 虚拟核心总数
        YarnEntrance.fields1.put("totalVirtualCores",clusterInfo1.get("totalVirtualCores"));
        // 分配的虚拟核心数
        YarnEntrance.fields1.put("allocatedVirtualCores",clusterInfo1.get("allocatedVirtualCores"));
        // 可用虚拟核心数
        YarnEntrance.fields1.put("availableVirtualCores",clusterInfo1.get("availableVirtualCores"));
        // 以 MB 为单位分配的内存量
        YarnEntrance.fields1.put("allocatedMB",clusterInfo1.get("allocatedMB"));


        YarnEntrance.fields2.put("appsSubmitted",clusterInfo2.get("appsSubmitted"));
        YarnEntrance.fields2.put("appsCompleted",clusterInfo2.get("appsCompleted"));
        YarnEntrance.fields2.put("appsPending",clusterInfo2.get("appsPending"));
        YarnEntrance.fields2.put("appsRunning",clusterInfo2.get("appsRunning"));
        YarnEntrance.fields2.put("appsFailed",clusterInfo2.get("appsFailed"));
        YarnEntrance.fields2.put("appsKilled",clusterInfo2.get("appsKilled"));
        YarnEntrance.fields2.put("containersPending",clusterInfo2.get("containersPending"));
        YarnEntrance.fields2.put("totalNodes",clusterInfo2.get("totalNodes"));
        YarnEntrance.fields2.put("activeNodes",clusterInfo2.get("activeNodes"));
        YarnEntrance.fields2.put("lostNodes",clusterInfo2.get("lostNodes"));
        YarnEntrance.fields2.put("unhealthyNodes",clusterInfo2.get("unhealthyNodes"));
        YarnEntrance.fields2.put("decommissionedNodes",clusterInfo2.get("decommissionedNodes"));
        YarnEntrance.fields2.put("containersAllocated",clusterInfo2.get("containersAllocated"));
        YarnEntrance.fields2.put("allocatedVirtualCores",clusterInfo2.get("allocatedVirtualCores"));
        YarnEntrance.fields2.put("availableVirtualCores",clusterInfo2.get("availableVirtualCores"));
        YarnEntrance.fields2.put("totalMB",clusterInfo2.get("totalMB"));
        YarnEntrance.fields2.put("allocatedMB",clusterInfo2.get("allocatedMB"));
        YarnEntrance.fields2.put("availableMB",clusterInfo2.get("availableMB"));
        YarnEntrance.fields2.put("reservedMB",clusterInfo2.get("reservedMB"));
        YarnEntrance.fields2.put("totalVirtualCores",clusterInfo2.get("totalVirtualCores"));

    }
}
