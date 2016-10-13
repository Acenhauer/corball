package com.acenhauer.corball.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

public class HttpClient {

    private static final Logger LOGGER = LogManager.getLogger(HttpClient.class);

    public HttpClient() {
    }

    public static String httpPost(String webServiceURL, Map<String, String> headers,
                                  String xmlRequest) {
        StringBuilder body = new StringBuilder();
        DefaultHttpClient client = new DefaultHttpClient();
        BasicCookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        StringEntity strEntity = null;
        try {
            strEntity = new StringEntity(xmlRequest, "text/xml", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("UnsupportedEncodingException httpPost", e);
        }
        HttpPost post = new HttpPost(webServiceURL);
        post.setHeader("Api-Key", BaseHttp.apiKey);

        if (headers != null) {
            Iterator it = headers.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                post.setHeader(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        post.setEntity(strEntity);
        try {
            // Execute request
            HttpResponse response = client.execute(post, httpContext);
            body.append(response.getStatusLine() + "\n");
            HttpEntity respEntity = response.getEntity();
            String entity = EntityUtils.toString(respEntity);
            body.append(entity);
        } catch (ClientProtocolException e) {
            LOGGER.info("ClientProtocolException httpPost", e);
        } catch (IOException e) {
            LOGGER.info("IOException httpPost", e);
        } finally {
            post.releaseConnection(); // stop connection
        }
        return body.toString();
    }

    public static String httpGet(String url, Map<String, String> headers) {
        StringBuilder body = new StringBuilder();
        DefaultHttpClient httpclient = new DefaultHttpClient(); // create new httpClient
        BasicCookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        HttpGet httpGet = null; // create new httpGet object
        try {
            httpGet = new HttpGet(url);
            httpGet.setHeader("Api-Key", BaseHttp.apiKey);

            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    httpGet.setHeader(pair.getKey().toString(), pair.getValue().toString());
                }
            }

            CloseableHttpResponse response = httpclient.execute(httpGet); // execute httpGet
            body.append(response.getStatusLine() + "\n");
            HttpEntity e = response.getEntity();
            String entity = EntityUtils.toString(e);
            body.append(entity);
        } catch (ClientProtocolException e) {
            LOGGER.info("ClientProtocolException httpGet", e);
        } catch (IOException e) {
            LOGGER.info("IOException httpGet", e);
        } finally {
            httpGet.releaseConnection(); // stop connection
        }
        return body.toString();
    }

    public static String httpDelete(String url, Map<String, String> headers) {
        StringBuilder body = new StringBuilder();
        DefaultHttpClient httpclient = new DefaultHttpClient(); // create new httpClient
        BasicCookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        HttpDelete httpDelete = null; // create new httpGet object
        try {
            httpDelete = new HttpDelete(url);
            httpDelete.setHeader("Api-Key", BaseHttp.apiKey);

            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    httpDelete.setHeader(pair.getKey().toString(), pair.getValue().toString());
                }
            }

            CloseableHttpResponse response = httpclient.execute(httpDelete); // execute httpDelete
            body.append(response.getStatusLine() + "\n");
            HttpEntity e = response.getEntity();
            String entity = EntityUtils.toString(e);
            body.append(entity);
        } catch (ClientProtocolException e) {
            LOGGER.info("ClientProtocolException httpDelete", e);
        } catch (IOException e) {
            LOGGER.info("IOException httpDelete", e);
        } finally {
            httpDelete.releaseConnection(); // stop connection
        }
        return body.toString();
    }

    public static String httpPut(String url, Map<String, String> headers) {
        StringBuilder body = new StringBuilder();
        DefaultHttpClient httpclient = new DefaultHttpClient(); // create new httpClient
        BasicCookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        HttpPut httpPut = null; // create new httpGet object
        try {
            httpPut = new HttpPut(url);
            httpPut.setHeader("Api-Key", BaseHttp.apiKey);

            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    httpPut.setHeader(pair.getKey().toString(), pair.getValue().toString());
                }
            }

            CloseableHttpResponse response = httpclient.execute(httpPut); // execute httpDelete
            body.append(response.getStatusLine() + "\n");
            HttpEntity e = response.getEntity();
            String entity = EntityUtils.toString(e);
            body.append(entity);
        } catch (ClientProtocolException e) {
            LOGGER.info("ClientProtocolException httpPut", e);
        } catch (IOException e) {
            LOGGER.info("IOException httpPut", e);
        } finally {
            httpPut.releaseConnection(); // stop connection
        }
        return body.toString();
    }

    public enum RequestMethod {
        GET, POST, DELETE, PUT;
    }

    public static String httpsExe(String webServiceURL, Map<String, String> headers, String request,
                                  RequestMethod httpMethod) {

        LOGGER.info("Calling method: " + httpMethod.name() + "> " + webServiceURL);
        LOGGER.info("Request: " + request);

        StringBuilder body = new StringBuilder();
        String httpStatus = "HTTP response code: ";

        if (httpMethod != RequestMethod.POST) {
            webServiceURL += request;
        }

        try {
            /*
            * Disable SSL Certificate validations
            * */
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            String signature = org.apache.commons.codec.digest.DigestUtils.sha256Hex(
                    BaseHttp.apiKey + BaseHttp.sharedSecret
                            + System.currentTimeMillis() / 1000);

            URL url = new URL(webServiceURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setRequestMethod(httpMethod.name());
            connection.setRequestProperty("X-Signature", signature);
            connection.setRequestProperty("Api-Key", BaseHttp.apiKey);
            if (headers != null) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    connection
                            .setRequestProperty(pair.getKey().toString(), pair.getValue().toString());
                }
            }

            if (httpMethod == RequestMethod.POST) {
                connection.setDoOutput(true);
                PrintWriter pw = new PrintWriter(connection.getOutputStream());
                pw.write(request);
                pw.close();
            }

            int responseCode = connection.getResponseCode();
            httpStatus += +responseCode + "\n";

            InputStream ims;
            if (responseCode == 200) {
                ims = connection.getInputStream();
            } else {
                ims = connection.getErrorStream();
            }

            if (ims != null) {
                InputStreamReader isr = new InputStreamReader(ims);
                BufferedReader in = new BufferedReader(isr);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    body.append(inputLine);
                }
                in.close();
            } else {
                body.append(connection.getResponseMessage());
            }

        } catch (Exception e) {
            httpStatus += "000\n";
            body.append(e.getMessage());
            LOGGER.info("Exception", e);
        }

        LOGGER.info("Response: " + body.toString());
        return httpStatus + body.toString();
    }
}
