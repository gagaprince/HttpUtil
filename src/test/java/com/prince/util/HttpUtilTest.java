package com.prince.util;

import com.prince.util.httputil.HttpUtil;
import junit.framework.TestCase;

/**
 * Created by gagaprince on 15-12-12.
 */
public class HttpUtilTest extends TestCase {

    public void testHttpUtilGet(){
        HttpUtil httpUtil = HttpUtil.getInstance();
        String url = "http://zhidao.baidu.com/link?url=lWI8ZRSXNspeUouBkgYTU81dsJ9u-_m9zqilu4AkmJfX6Zgk3pH0HSWsuGgbjWSVoUC1187KDVZ7wPByTu-x8K";
        String content = httpUtil.getContentByUrl(url);
        System.out.println(content);
    }
}
