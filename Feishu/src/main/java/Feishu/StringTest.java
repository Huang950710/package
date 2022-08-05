package Feishu;

public class StringTest {
    public static void main(String[] args) {
        String content = "**Firing**\n\nValue: [ metric='foo' labels={instance=bar} value=10 ]\nLabels:\n - alertname = TestAlert\n - instance = Grafana\nAnnotations:\n - description = \"小文件过多，请及时处理文件\"\n - summary = Notification test\nSilence: http://loaclhost:3000/alerting/silence/new?alertmanager=grafana\u0026matcher=alertname%3DTestAlert\u0026matcher=instance%3DGrafana\n";

        /*
            String s ="> AlarmDate : [ lag.overflow [ " + content.substring(content.indexOf("AlarmContent")-2).split("\\[", 3)[2].split("\\]", 3)[0] + "] ]";
            content = content.substring(0,content.indexOf("AlarmContent")-2)+s+"\n\n";
*/

        System.out.println(content.substring(0,content.indexOf("Silence")));
    }
}
