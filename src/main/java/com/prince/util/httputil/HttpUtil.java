package com.prince.util.httputil;

import com.prince.util.fileutil.FileUtil;
import com.prince.util.httputil.bean.HttpConfig;
import com.prince.util.ossutil.OSSUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gagaprince on 15-12-12.
 */
public class HttpUtil {
    private HttpClientUtil httpClientUtil;

    private HttpUtil(){
        httpClientUtil = HttpClientUtil.getInstance();
    }
    private static HttpUtil httpUtil;
    public static HttpUtil getInstance(){
        if (httpUtil==null){
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    public String getContentByUrl(String url){
        return getContentByUrl(url,"gbk");
    }

    //读取网页源码
    public String getContentByUrl(String url,String charset){
        HttpGet get = httpClientUtil.giveMeHttpGet(url);
        CloseableHttpResponse response = httpClientUtil.giveMeResponse(get);
        String content = httpClientUtil.getHtml(response, charset);
        httpClientUtil.closeResponse(response);
        return content;
    }

    public String getContentByUrlAndConfig(String url,HttpConfig httpConfig){
        HttpGet get = httpClientUtil.giveMeHttpGet(url);
        get.setHeader("User-Agent",httpConfig.getUa());
        get.setHeader("Referer",httpConfig.getRefer());
        get.setHeader("Accept",httpConfig.getAccept());
        get.setHeader("Accept-Encoding",httpConfig.getAcceptEncoding());
        get.setHeader("Accept-Language",httpConfig.getAcceptLanguage());
        get.setHeader("Cache-Control",httpConfig.getCacheControl());
        get.setHeader("Proxy-Connection",httpConfig.getProxyConnection());

        CloseableHttpResponse response = httpClientUtil.giveMeResponse(get);
        String content = httpClientUtil.getHtml(response, httpConfig.getCharset());
        httpClientUtil.closeResponse(response);
        return content;
    }


    //图片网络地址 和磁盘存储地址
    public void saveImgByUrl(String url,String path){
        HttpGet get = httpClientUtil.giveMeHttpGet(url);
        CloseableHttpResponse response = httpClientUtil.giveMeResponse(get);
        InputStream in = httpClientUtil.getInputStream(response);

        FileUtil fileUtil = FileUtil.getInstance();
        fileUtil.saveInputStreamInFile(in,path);

        httpClientUtil.closeResponse(response);
    }

    public String uploadImgToOss(String url,String bucketName,String root){
        OSSUtil ossUtil = OSSUtil.getInstance();
        HttpEntity entity = getEntityByUrl(url);
        InputStream in = null;
        try {
            String key = root+changePath(url);
            in = entity.getContent();
            long length = entity.getContentLength();
            ossUtil.uploadFile(bucketName,key,in,length);
            in.close();
            return ossUtil.getRootUrl(bucketName)+key;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;

    }

    private String changePath(String url){
        int index = url.indexOf("/", 7);
        String path = url.substring(index);
        return path;
    }

    public HttpEntity getEntityByUrl(String url){
        HttpGet get = httpClientUtil.giveMeHttpGet(url);
        CloseableHttpResponse response = httpClientUtil.giveMeResponse(get);
        return httpClientUtil.getHttpEntity(response);
    }

}
