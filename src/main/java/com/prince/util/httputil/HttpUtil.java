package com.prince.util.httputil;

import com.prince.util.fileutil.FileUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

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
        String content = httpClientUtil.getHtml(response,charset);
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

}
