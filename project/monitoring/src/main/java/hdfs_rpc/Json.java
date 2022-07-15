package hdfs_rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Json {

    Map<String, String> tags = new HashMap<>();
    Map<String, Object> fields = new HashMap<>();

    public Json() {
    }

    @Test
    public void rpc_analysis(String message,String company){

        //将一个数值转换为格式化的数值，下例含义是使用df对象将对应数字保留两位小数
        //DecimalFormat 方法详解链接：https://www.jianshu.com/p/c1dec1796062
        DecimalFormat df = new DecimalFormat("#.00");
        Date date = new Date();
        SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String datetime = ss.format(date);
        fields.put("timestamp", datetime);
        tags.put("company", company);

        //******************************************************************************************************************************************************************
        //将一行 jSON 数据转换为 JSON 对象
        JSONObject jsonObject = JSON.parseObject(message);

        //因为 beans 在JSON数据中是数组，所有用 JSON 对象的 getJSONArray 来获取内容
        JSONArray beans = jsonObject.getJSONArray("beans");
        //遍历数组
        for (Object bean : beans) {
            //将数组中的每个集合转换为一个 字符串，在转换为 JSON 对象 ，
            JSONObject jsonObject1 = JSON.parseObject(bean.toString());

            //遍历
            if (jsonObject1.get("name").equals("Hadoop:service=NameNode,name=RpcActivityForPort8020")){
                JSONObject jsonObject2 = JSON.parseObject(jsonObject1.toString());
                Set<Map.Entry<String, Object>> entries = jsonObject2.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    // rpc在交互中平均等待时间
                    if (entry.getKey().equals("RpcQueueTimeAvgTime")) fields.put("RPC."+entry.getKey(),entry.getValue());
                    // rpc queue中完成的rpc操作数目
                    if (entry.getKey().equals("RpcQueueTimeNumOps")) fields.put("RPC."+entry.getKey(),entry.getValue());
                    // rpc在最近的交互中连接数目
                    if (entry.getKey().equals("RpcProcessingTimeNumOps")) fields.put("RPC."+entry.getKey(),entry.getValue());
                    // rpc在最近的交互中平均操作时间
                    if (entry.getKey().equals("RpcProcessingTimeAvgTime")) fields.put("RPC."+entry.getKey(),entry.getValue());
                    // 当前的连接数
                    if (entry.getKey().equals("NumOpenConnections")) fields.put("RPC."+entry.getKey(),entry.getValue());
                }
            }
            else if (jsonObject1.get("name").equals("Hadoop:service=NameNode,name=RpcDetailedActivityForPort8020")){
                JSONObject jsonObject2 = JSON.parseObject(jsonObject1.toString());
                Set<Map.Entry<String, Object>> entries = jsonObject2.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    // 接收到的dn心跳次数
                    if (entry.getKey().equals("SendHeartbeatNumOps")) fields.put("RpcDetailed."+entry.getKey(),entry.getValue());
                    // 心跳请求的平均处理耗时
                    if (entry.getKey().equals("SendHeartbeatAvgTime")) fields.put("RpcDetailed."+entry.getKey(),entry.getValue());
                    // HA切换的次数
                    if (entry.getKey().equals("TransitionToActiveNumOps")) fields.put("RpcDetailed."+entry.getKey(),entry.getValue());
                    // HA切换的平均耗时
                    if (entry.getKey().equals("TransitionToActiveAvgTime")) fields.put("RpcDetailed."+entry.getKey(),entry.getValue());
                }
            }
            else if (jsonObject1.get("name").equals("Hadoop:service=NameNode,name=JvmMetrics")){
                JSONObject jsonObject2 = JSON.parseObject(jsonObject1.toString());
                Set<Map.Entry<String, Object>> entries = jsonObject2.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    // JVM 运行时可以使用的最大内存大小 (MB)
                    if (entry.getKey().equals("MemMaxM")) fields.put("JVM."+entry.getKey(),entry.getValue());
                    // JVM 配置的 HeapMemory 的大小 (MB)
                    if (entry.getKey().equals("MemHeapMaxM")) fields.put("JVM."+entry.getKey(),entry.getValue());
                    // 当前使用的堆内存（以 MB 为单位）
                    if (entry.getKey().equals("MemHeapUsedM")) fields.put("JVM."+entry.getKey(),entry.getValue());
                    // 总 GC 计数
                    if (entry.getKey().equals("GcCount")) fields.put("JVM."+entry.getKey(),entry.getValue());
                    // 以毫秒为单位的总 GC 时间
                    if (entry.getKey().equals("GcTimeMillis")) fields.put("JVM."+entry.getKey(),entry.getValue());
                    // 当前使用的非堆内存（以 MB 为单位）
                    if (entry.getKey().equals("MemNonHeapUsedM")) fields.put("JVM."+entry.getKey(),entry.getValue());
                    // 当前提交的非堆内存（以 MB 为单位）
                    if (entry.getKey().equals("MemNonHeapCommittedM")) fields.put("JVM."+entry.getKey(),entry.getValue());

                    //线程运行数量
                    if (entry.getKey().equals("ThreadsRunnable")) fields.put("JVM."+entry.getKey(),entry.getValue());
                    //线程阻塞数量
                    if (entry.getKey().equals("ThreadsBlocked")) fields.put("JVM."+entry.getKey(),entry.getValue());
                    //线程等待数量
                    if (entry.getKey().equals("ThreadsWaiting")) fields.put("JVM."+entry.getKey(),entry.getValue());
                }
            }
            else if (jsonObject1.get("name").equals("Hadoop:service=NameNode,name=FSNamesystem")){
                JSONObject jsonObject2 = JSON.parseObject(jsonObject1.toString());
                Set<Map.Entry<String, Object>> entries = jsonObject2.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    // 当前遗失的block数量
                    if (entry.getKey().equals("MissingBlocks")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // hdfs总的块数
                    if (entry.getKey().equals("BlocksTotal")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 损坏的block数量
                    if (entry.getKey().equals("CorruptBlocks")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 待删除块队列数
                    if (entry.getKey().equals("PendingDeletionBlocks")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 失去心跳的总数量
                    if (entry.getKey().equals("ExpiredHeartbeats")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 当前数据节点的原始容量(以字节为单位)
                    if (entry.getKey().equals("CapacityTotal")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 当前在所有DataNode中使用的容量(以字节为单位)
                    if (entry.getKey().equals("CapacityUsed")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 当前剩余容量(字节)
                    if (entry.getKey().equals("CapacityRemaining")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // DataNodes用于非DFS目的的当前空间(以字节为单位)
                    if (entry.getKey().equals("CapacityUsedNonDFS")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 当前连接数
                    if (entry.getKey().equals("TotalLoad")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 当前文件和目录的数量
                    if (entry.getKey().equals("FilesTotal")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 待机NameNode中用于以后处理的挂起的与块相关的消息的当前数量
                    if (entry.getKey().equals("PendingDataNodeMessageCount")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 由于心跳延迟而标记为过期的DataNodes当前数目
                    if (entry.getKey().equals("StaleDataNodes")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 当前存活的datanode节点数
                    if (entry.getKey().equals("NumLiveDataNodes")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 当前死亡的数据节点数
                    if (entry.getKey().equals("NumDeadDataNodes")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                    // 所有Datanodes的卷故障总数
                    if (entry.getKey().equals("VolumeFailuresTotal")) fields.put("FSNamesystem."+entry.getKey(),entry.getValue());
                }
            }
        }
        //******************************************************************************************************************************************************************
    }
}
