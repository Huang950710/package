package hdfs_monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Json {

    Map<String, String> tags = new HashMap<>();
    Map<String, Object> fields = new HashMap<>();

    public Json() {
    }

    public void analysis(String message,String company,String directory){

        // 将一个数值转换为格式化的数值，下例含义是使用df对象将对应数字保留两位小数
        // DecimalFormat 方法详解链接：https://www.jianshu.com/p/c1dec1796062
        DecimalFormat df = new DecimalFormat("#.00");
        Date date = new Date();
        SimpleDateFormat ss = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
        String format = simpleDateFormat.format(date);
        String datetime = ss.format(date);

        fields.put("timestamp", datetime);
        fields.put("day",format);
        fields.put("directory",directory);
        tags.put("company", company);

        // 将一行 jSON 数据转换为 JSON 对象
        JSONObject jsonObject = JSON.parseObject(message);

        /**
         * 获取ContentSummary对象中的内容
         * 从 {"ContentSummary":{"directoryCount":1,"fileCount":2,"length":14786,"quota":-1,"spaceConsumed":44358,"spaceQuota":-1,"typeQuota":{}}} 中
         * 获取到如下内容：{"directoryCount":1,"fileCount":2,"length":14786,"quota":-1,"spaceConsumed":44358,"spaceQuota":-1,"typeQuota":{}}
         */
        JSONObject contentSummary = jsonObject.getJSONObject("ContentSummary");

        // 将对象转换为set集合
        Set<Map.Entry<String, Object>> entries = contentSummary.entrySet();
        // 遍历set集合
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getKey().equals("directoryCount")) fields.put("目录数量",entry.getValue());;
            if (entry.getKey().equals("length")) fields.put("文件总大小",entry.getValue());
            if (entry.getKey().equals("fileCount")) fields.put("文件数量",entry.getValue());
        }
    }
}
