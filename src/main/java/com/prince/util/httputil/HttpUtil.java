package com.prince.util.httputil;

import com.prince.util.fileutil.FileUtil;
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
        try {
            String key = root+changePath(url);
            InputStream in = entity.getContent();
            long length = entity.getContentLength();
            ossUtil.uploadFile(bucketName,key,in,length);
            in.close();
            return ossUtil.getRootUrl(bucketName)+key;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String changePath(String url){
        int index = url.indexOf("/",2);
        String path = url.substring(index);
        return path;
    }

    public HttpEntity getEntityByUrl(String url){
        HttpGet get = httpClientUtil.giveMeHttpGet(url);
        CloseableHttpResponse response = httpClientUtil.giveMeResponse(get);
        return httpClientUtil.getHttpEntity(response);
    }

}
