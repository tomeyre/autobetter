package com.tom.autobetter.api;

import com.tom.autobetter.data.SessionToken;
import com.tom.autobetter.exceptions.APINGException;
import com.tom.autobetter.util.JsonResponseHandler;
import com.tom.autobetter.util.RescriptResponseHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.tom.autobetter.util.CommonConstants.*;

public class HttpUtil {

    private final String HTTP_HEADER_X_APPLICATION = "X-Application";
    private final String HTTP_HEADER_X_AUTHENTICATION = "X-Authentication";
    private final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private final String HTTP_HEADER_ACCEPT = "Accept";
    private final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";
    private SessionToken sessionToken = SessionToken.getInstance();

    public HttpUtil() {
        super();
    }

    private String sendPostRequest(String param, String operation, String URL, ResponseHandler<String> reqHandler){
        String jsonRequest = param;

        HttpPost post = new HttpPost(URL);
        String resp = null;
        try {
            post.setHeader(HTTP_HEADER_CONTENT_TYPE, APPLICATION_JSON);
            post.setHeader(HTTP_HEADER_ACCEPT, APPLICATION_JSON);
            post.setHeader(HTTP_HEADER_ACCEPT_CHARSET, ENCODING_UTF8);
            post.setHeader(HTTP_HEADER_X_APPLICATION, System.getenv("APP_KEY"));
            post.setHeader(HTTP_HEADER_X_AUTHENTICATION, sessionToken.getSsoId());

            post.setEntity(new StringEntity(jsonRequest, ENCODING_UTF8));

            System.out.println(param);
            System.out.println(operation);
            System.out.println(URL);
            System.out.println(reqHandler);
            System.out.println(post.toString());
            System.out.println(System.getenv("APP_KEY"));
            System.out.println(sessionToken.getSsoId());

            HttpClient httpClient = new DefaultHttpClient();

            HttpParams httpParams = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, new Integer(TIMEOUT).intValue());
            HttpConnectionParams.setSoTimeout(httpParams, new Integer(TIMEOUT).intValue());

            resp = httpClient.execute(post, reqHandler);

        } catch (UnsupportedEncodingException e1) {
            //Do something

        } catch (ClientProtocolException e) {
            //Do something

        } catch (IOException ioE){
            //Do something

        }

        return resp;

    }

    public String sendPostRequestRescript(String param, String operation, String api) throws APINGException {
        String apiNgURL = APING_URL + api + RESCRIPT_SUFFIX+operation+"/";

        return  sendPostRequest(param, operation, apiNgURL, new RescriptResponseHandler());

    }

    public String sendPostRequestJsonRpc(String param, String operation, String api) {
        String apiNgURL = APING_URL + api + JSON_RPC_SUFFIX;

        return sendPostRequest(param, operation, apiNgURL, new JsonResponseHandler());

    }

}
