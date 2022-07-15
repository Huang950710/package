package hdfs_rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Set;

public class Master_Json {

    public static String master(String message) {

        String aa = null;

        //将一行 jSON 数据转换为 JSON 对象
        JSONObject jsonObject = JSON.parseObject(message);

        //因为 beans 在JSON数据中是数组，所有用 JSON 对象的 getJSONArray 来获取内容
        JSONArray beans = jsonObject.getJSONArray("beans");

        for (Object bean : beans) {
            //将数组中的每个集合转换为一个 字符串，在转换为 JSON 对象 ，
            JSONObject jsonObject1 = JSON.parseObject(bean.toString());

            //遍历
            if (jsonObject1.get("name").equals("Hadoop:service=NameNode,name=FSNamesystem")) {
                JSONObject jsonObject2 = JSON.parseObject(jsonObject1.toString());
                Set<Map.Entry<String, Object>> entries = jsonObject2.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    // rpc在交互中平均等待时间
                    if (entry.getKey().equals("tag.HAState")) {
                        aa = entry.getValue().toString();
                    }
                }
            }
        }

        return aa;
    }
}