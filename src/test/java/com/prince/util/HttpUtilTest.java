package com.prince.util;

import com.prince.util.httputil.HttpUtil;
import junit.framework.TestCase;

/**
 * Created by gagaprince on 15-12-12.
 */
public class HttpUtilTest extends TestCase {

    public void testHttpUtilGet(){
        HttpUtil httpUtil = HttpUtil.getInstance();
        String url = "http://www.003zyz.com/listm/index7.html";
        String content = httpUtil.getContentByUrl(url);
        System.out.println(content);
    }

}
