package com.tom.autobetter.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.tom.autobetter.util.CommonConstants.APP_KEY;

public class HttpUtil {

    static String json = "";

    public String getJSONFromUrl(String url) {

        try {
            // this code block represents/configures a connection to your REST service
            // it also represents an HTTP 'GET' request to get data from the REST service, not POST!
            URL u = new URL(url);
            HttpURLConnection restConnection = (HttpURLConnection) u.openConnection();
            restConnection.setRequestMethod("GET");
            restConnection.setRequestProperty("Content-length", "0");
            restConnection.setUseCaches(false);
            restConnection.setAllowUserInteraction(false);
            restConnection.setConnectTimeout(10000);
            restConnection.setReadTimeout(10000);
            restConnection.connect();
            int status = restConnection.getResponseCode();

            // switch statement to catch HTTP 200 and 201 errors
            switch (status) {
                case 200:
                case 201:
                    // live connection to your REST service is established here using getInputStream() method
                    BufferedReader br = new BufferedReader(new InputStreamReader(restConnection.getInputStream()));

                    // create a new string builder to store json data returned from the REST service
                    StringBuilder sb = new StringBuilder();
                    String line;

                    // loop through returned data line by line and append to stringbuilder 'sb' variable
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    // remember, you are storing the json as a stringy
                    try {
                        json = sb.toString();
                    } catch (Exception e) {
                    }
                    // return JSON String containing data
                    return json;
            }
            // HTTP 200 and 201 error handling from switch statement
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        return null;
    }

    private static int port = 443;

    public String loginToBetfair(String endpoint, String body) {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            Resource resource = new ClassPathResource("client-2048.p12");
            InputStream file = resource.getInputStream();
            KeyManager[] keyManagers = getKeyManagers("pkcs12", file, System.getenv("PASS_TWO"));
            ctx.init(keyManagers, null, new SecureRandom());
            SSLSocketFactory factory = new SSLSocketFactory(ctx, new StrictHostnameVerifier());

            ClientConnectionManager manager = httpClient.getConnectionManager();
            manager.getSchemeRegistry().register(new Scheme("https", port, (SchemeSocketFactory) factory));
            HttpPost httpPost = new HttpPost("https://identitysso-cert.betfair.com/api/certlogin");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "tom.eyre8770@gmail.com"));
            nvps.add(new BasicNameValuePair("password", System.getenv("PASS_TWO")));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            httpPost.setHeader("X-Application", APP_KEY.equals("") ? System.getenv("APP_KEY") : APP_KEY);

//            System.out.println("executing request" + httpPost.getRequestLine());

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

//            System.out.println("----------------------------------------");
//            System.out.println(response.getStatusLine());
            if (entity != null) {
                String responseString = EntityUtils.toString(entity);
                //extract the session token from responsestring
//                System.out.println("responseString" + responseString);
                return new JSONObject(responseString).getString("sessionToken");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }

    private static KeyManager[] getKeyManagers(String keyStoreType, InputStream keyStoreFile, String keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyStorePassword.toCharArray());
        return kmf.getKeyManagers();
    }
}