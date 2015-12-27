package com.prince.util.httputil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gagaprince on 15-12-12.
 */
public class HttpClientUtil {
    private CloseableHttpClient httpClient;

    private static HttpClientUtil httpClientUtil;
    private HttpClientUtil(){
        httpClient = HttpClients.createDefault();
    }
    public static HttpClientUtil getInstance(){
        if(httpClientUtil==null){
            httpClientUtil = new HttpClientUtil();

        }
        return httpClientUtil;
    }

    public void closeClient(){
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetClient(){
        closeClient();
        httpClient = HttpClients.createDefault();
    }

    public HttpGet giveMeHttpGet(String url){
        HttpGet get = new HttpGet(url);
        return get;
    }
    public CloseableHttpResponse giveMeResponse(HttpRequestBase method){
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(method);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getHtml(CloseableHttpResponse response){
        return getHtml(response,"utf-8");
    }

    public String getHtml(CloseableHttpResponse response,String charset){
        String html = "";
        int status = response.getStatusLine().getStatusCode();
        if(status==200){
            HttpEntity entity = response.getEntity();
            Header contentTypeHeader = entity.getContentType();
            if (contentTypeHeader!=null) {
                try {
                    html = EntityUtils.toString(entity,charset);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
        return html;
    }

    public InputStream getInputStream(CloseableHttpResponse response){
        InputStream in=null;
        int status = response.getStatusLine().getStatusCode();
        if(status==200){
            HttpEntity entity = response.getEntity();
            try {
                in = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return in;
    }

    public HttpEntity getHttpEntity(CloseableHttpResponse response){
        InputStream in=null;
        int status = response.getStatusLine().getStatusCode();
        if(status==200){
            HttpEntity entity = response.getEntity();
            return entity;
        }
        closeResponse(response);
        return null;

    }

    public void closeResponse(CloseableHttpResponse response){
        try {
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
