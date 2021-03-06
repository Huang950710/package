package Feishu.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class ChatbotSend {
    //WebHook地址xxx
    public static String WEBHOOK_TOKEN = "https://open.feishu.cn/open-apis/bot/v2/hook/0f69e523-a11f-41ef-b847-749e85e78d92";


    public static void send(String message) throws IOException {

//        String message = "{\"receiver\":\"email2\",\"status\":\"firing\",\"alerts\":[{\"status\":\"firing\",\"labels\":{\"alertname\":\"hdfs_block\",\"hdfs_group\":\"hdfs\"},\"annotations\":{\"description\":\"小文件过多\"},\"startsAt\":\"2022-06-23T10:38:05.441105162+08:00\",\"endsAt\":\"0001-01-01T00:00:00Z\",\"generatorURL\":\"http://loaclhost:3000/alerting/grafana/WCIL6Iq7z/view\",\"fingerprint\":\"a434daf5061af245\",\"silenceURL\":\"http://loaclhost:3000/alerting/silence/new?alertmanager=grafana\\u0026matcher=alertname%3Dhdfs_block\\u0026matcher=hdfs_group%3Dhdfs\",\"dashboardURL\":\"http://loaclhost:3000/d/99eycSqnk\",\"panelURL\":\"http://loaclhost:3000/d/99eycSqnk?viewPanel=6\",\"valueString\":\"[ var='B0' metric='hdfs_block_monitor.平均文件大小(M)' labels={} value=1.4436450478079108 ]\"}],\"groupLabels\":{},\"commonLabels\":{\"alertname\":\"hdfs_block\",\"hdfs_group\":\"hdfs\"},\"commonAnnotations\":{\"description\":\"小文件过多\"},\"externalURL\":\"http://loaclhost:3000/\",\"version\":\"1\",\"groupKey\":\"{}/{hdfs_group=\\\"hdfs\\\"}:{}\",\"truncatedAlerts\":0,\"orgId\":1,\"title\":\"[FIRING:1]  (hdfs_block hdfs)\",\"state\":\"alerting\",\"message\":\"**Firing**\\n\\nValue: [ var='B0' metric='hdfs_block_monitor.平均文件大小(M)' labels={} value=1.4436450478079108 ]\\nLabels:\\n - alertname = hdfs_block\\n - hdfs_group = hdfs\\nAnnotations:\\n - description = 小文件过多\\nSource: http://loaclhost:3000/alerting/grafana/WCIL6Iq7z/view\\nSilence: http://loaclhost:3000/alerting/silence/new?alertmanager=grafana\\u0026matcher=alertname%3Dhdfs_block\\u0026matcher=hdfs_group%3Dhdfs\\nDashboard: http://loaclhost:3000/d/99eycSqnk\\nPanel: http://loaclhost:3000/d/99eycSqnk?viewPanel=6\\n\"}";
        //解析Json
        //将一行 jSON 数据转换为 JSON 对象
        JSONObject jsonObject = JSON.parseObject(message);
        String content = "### Grafana 集群告警\n";
        content = content + "receiver：" + jsonObject.get("receiver") + "\n";

        JSONArray alerts = jsonObject.getJSONArray("alerts");
        for (Object alert : alerts) {
            JSONObject jsonObject1 = JSON.parseObject(alert.toString());
            for (Map.Entry<String, Object> stringObjectEntry : jsonObject1.entrySet()) {
                if (stringObjectEntry.getKey().equals("valueString")) content = content + stringObjectEntry.getKey().toString() + " : " + stringObjectEntry.getValue().toString();
            }
        }


        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
        String s = JSONObject.toJSONString(content);

        String textMsg1 = "{\"msg_type" + "\":" + "\"text\"," + "\"content\":{\"text\":"+ s + "}}";

        StringEntity se = new StringEntity(textMsg1, "utf-8");

        httppost.addHeader("BOX-SIGNATURE-VERSION", "1");
        httppost.addHeader("BOX-SIGNATURE-ALGORITHM", "HmacSHA256");

        httppost.addHeader("BOX-DELIVERY-ID", "1911001047");
        httppost.addHeader("BOX-DELIVERY-TIMESTAMP", "2019-12-09T02:27:42-07:00");

        httppost.addHeader("BOX-SIGNATURE-PRIMARY", "uwleyFtFbAs+W6GmQMlPwzwLUtTQc77XQSIJP2Qak5A=");
        httppost.addHeader("BOX-SIGNATURE-SECONDARY", "uwleyFtFbAs+W6GmQMlPwzwLUtTQc77XQSIJP2Qak5A=");

        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }
}
