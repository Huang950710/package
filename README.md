此文档是 Grfana 与 Kafka Eagle 的告警对接飞书机器人的代码，代码启动端口为 654

Grafan 与 Kafka Eagle 告警渠道都需要设置为 http://127.0.0.1:654 , ip 换成自己启动代码服务器 ip

- 功能说明：

         -此 Jar 包主要功能就是将 Grafana 与 kafka Eagle 的告警对接到飞书机器人

- 使用说明：

         - java -jar Feishu-1.0-SNAPSHOT.jar + webhook地址 + moudle(Grafana / KafkaEagle) + 端口

- 举例：

         - 将 Grafana 的告警对接飞书机器人 
         - java -jar Feishu-1.0-SNAPSHOT.jar https://open.feishu.cn/open-apis/bot/v2/hook/xxxxxxxxxxxxxx Grafana 654
         
         - 将 KafkaEagle 的告警对接飞书机器人
         - java -jar Feishu-1.0-SNAPSHOT.jar https://open.feishu.cn/open-apis/bot/v2/hook/xxxxxxxxxxxxxx KafkaEagle 654
