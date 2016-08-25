package com.evsward.server.test.yali;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class PlayersRegComps implements Runnable {

	public int memId;
	public int compId;
	
	@Override
	public void run() {
		String url = "http://localhost:7979/memRegComp/memRegComp/memRegComp.do";
		HttpClient httpclient = new DefaultHttpClient();  
    	HttpPost httpPost = new HttpPost(url);
    	List<NameValuePair> formParams = new ArrayList<NameValuePair>();  
        formParams.add(new BasicNameValuePair("memID", memId+""));  
        formParams.add(new BasicNameValuePair("compID", compId+""));  
        UrlEncodedFormEntity urlEncodedFormEntity;  
	  
        try {  
            urlEncodedFormEntity = new UrlEncodedFormEntity(formParams, "UTF-8");  
            httpPost.setEntity(urlEncodedFormEntity);  
            System.out.println("execurting request:" + httpPost.getURI());  
            HttpResponse httpResponse = null;  
            httpResponse = httpclient.execute(httpPost);  
            HttpEntity httpEntity = httpResponse.getEntity();  
            if (httpEntity != null) {  
                String content = EntityUtils.toString(httpEntity, "UTF-8");  
                System.out.println("Response content:" + content);  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            //关闭连接，释放资源  
        	httpclient.getConnectionManager().shutdown();  
        }
	}

	public static void main(String[] args) throws InterruptedException {
//		for (int i = 0; i < 1; i++) {
//			PlayersRegComps pr = new PlayersRegComps();
//			pr.memId = 0;
//			pr.compId = 0;
//			new Thread(pr).start();
//			Thread.sleep(1000);
//		}
	}
}
