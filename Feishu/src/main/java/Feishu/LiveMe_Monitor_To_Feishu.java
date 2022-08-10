package Feishu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class LiveMe_Monitor_To_Feishu {

    //WebHook地址xxx
    public static String WEBHOOK_TOKEN = "https://open.feishu.cn/open-apis/bot/v2/hook/3ba0b039-9d5a-4d5c-93d7-4ea27b100227";

    public static void send(String message,String WebHook) throws IOException {

        /**
         * WebHook地址
         */
        String WEBHOOK_TOKEN = WebHook;


        /**
         * 解析Json
         * 将一行 jSON 数据转换为 JSON 对象
         */
        JSONObject jsonObject = JSON.parseObject(message);
        String content = "";
        JSONObject markdown = JSON.parseObject(jsonObject.get("markdown").toString().replace("\\n \\n\\n","\n").replace("`","").replace("AlarmID"," AlarmID"));
        for (Map.Entry<String, Object> stringObjectEntry : markdown.entrySet()) {
            content = content + stringObjectEntry.getValue().toString().replace("*","").trim() + "\n\n";
/*
            String s ="> AlarmDate : [ lag.overflow [ " + content.substring(content.indexOf("AlarmContent")-2).split("\\[", 3)[2].split("\\]", 3)[0] + "] ]";
            content = content.substring(0,content.indexOf("AlarmContent")-2)+s+"\n\n";
*/
            content = content.replace("<font color=\"warning\">","").replace("</font>","");
        }

        content = content + "<at user_id=\"all\">所有人</at>" + "\n";


        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
        String s = JSONObject.toJSONString(content);

        String textMsg1 = "{\"msg_type" + "\":" + "\"text\"," + "\"content\":{\"text\":"+ s + "}}";

        StringEntity se = new StringEntity(textMsg1, "utf-8");

        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }

}
