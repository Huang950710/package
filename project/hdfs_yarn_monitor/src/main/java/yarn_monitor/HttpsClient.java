package yarn_monitor;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpsClient {

    public String HttpsClient(String url) throws IOException {

        String message = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();

//      HttpGet httpGet = new HttpGet("http://10.60.79.100:8088/jmx?qry=Hadoop:service=ResourceManager,name=QueueMetrics,q0=root,q1=default");
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            message = EntityUtils.toString(httpEntity, "utf8");
        }

        return message;
    }
}
