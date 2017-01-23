package org.suzuki.app.resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.suzuki.algorithm.logging.SuzukiLogger;

import java.io.*;

import static org.apache.http.HttpHeaders.CONTENT_TYPE;

public class Resource {

    public static final String RESOURCE_URL_HEROKU = "https://evening-peak-26255.herokuapp.com/";

    private final String url;

    private final HttpClient client;

    public Resource(String url) {
        this.client = buildHttpClient();
        this.url = url;
    }

    public String get() throws IOException {
        // prepare request
        HttpGet request = new HttpGet(url);
//        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader(CONTENT_TYPE, "application/json");

        // execute request
        SuzukiLogger.log("Sending 'GET' request to URL : " + url);
        HttpResponse response = client.execute(request);
        SuzukiLogger.log("Response Code : " + response.getStatusLine().getStatusCode());

        //read result
        try (InputStream instream = response.getEntity().getContent()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            String resultString = result.toString();

            // log result
            SuzukiLogger.log(resultString);
            return resultString;
        } catch (IOException ex) {
            // In case of an IOException the connection will be released
            // back to the connection manager automatically
            throw ex;
        } catch (RuntimeException ex) {
            // In case of an unexpected exception you may want to abort
            // the HTTP request in order to shut down the underlying
            // connection and release it back to the connection manager.
            request.abort();
            throw ex;
        }

    }

    public void set(int value) throws IOException {
        try{
            HttpPost httpPost = new HttpPost(url);

            String json = "{\"value\":" + value + "}";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            SuzukiLogger.log("Sending 'POST' request to URL : " + url);
            SuzukiLogger.log("With data: " + json);
            HttpResponse response = client.execute(httpPost);
            SuzukiLogger.log("Response Code : " + response.getStatusLine().getStatusCode());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private HttpClient buildHttpClient() {
        try{
            // set up ssl
            TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
            SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy,
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, sf));
            ClientConnectionManager ccm = new PoolingClientConnectionManager(registry);

            // create http client
            return new DefaultHttpClient(ccm);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
