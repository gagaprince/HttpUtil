package com.prince.util.httputil.bean;

/**
 * Created by zidong.wang on 2016/1/5.
 */
public class HttpConfig {
    private String ua;
    private String refer;
    private String charset;
    private String accept;
    private String acceptEncoding;
    private String cacheControl;
    private String proxyConnection;
    private String acceptLanguage;

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getAccept() {
        return accept;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public String getProxyConnection() {
        return proxyConnection;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public void setProxyConnection(String proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public HttpConfig(){
        this.charset = "utf-8";
        this.accept="text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
        this.acceptEncoding="gzip, deflate, sdch";
        this.cacheControl="max-age=0";
        this.proxyConnection="keep-alive";
        this.acceptLanguage = "zh-CN,zh;q=0.8";
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getRefer() {
        return refer;
    }

    public String getUa() {
        return ua;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }
}
