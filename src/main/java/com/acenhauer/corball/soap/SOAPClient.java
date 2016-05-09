package com.acenhauer.corball.soap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class SOAPClient {

    public SOAPClient() {
    }

    public static String soapRequest(String webServiceURL, String soapRequest) throws Exception {
        DefaultHttpClient client = new DefaultHttpClient();
        BasicCookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        StringEntity strEntity = new StringEntity(soapRequest, "text/xml", "UTF-8");
        HttpPost post = new HttpPost(webServiceURL);
        post.setHeader("Accept", "application/xml,application/dime,multipart/related,text/*");
        post.setHeader("SOAPAction", "");
        post.setEntity(strEntity);
        // Execute request
        HttpResponse response = client.execute(post, httpContext);
        HttpEntity respEntity = response.getEntity();
        return EntityUtils.toString(respEntity).replaceAll("&lt;", "<").replaceAll("&gt;", ">")
            .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }
}
