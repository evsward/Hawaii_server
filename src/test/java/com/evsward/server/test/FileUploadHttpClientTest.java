package com.evsward.server.test;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class FileUploadHttpClientTest {

	public static void main(String[] args) throws Exception{
		String url = "http://localhost:8080/hiserver/member/member/addMember.do";
		HttpClient httpclient= new DefaultHttpClient();
		HttpPost httpPost= new HttpPost(url);
		MultipartEntity mulentity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
		System.out.println(mulentity.getContentType());
        mulentity.addPart("memName", new StringBody("memName阿萨德"));  
        mulentity.addPart("memSex", new StringBody("1"));  
        mulentity.addPart("identno", new StringBody("410602198312062015"));    
        mulentity.addPart("mobile", new StringBody("13000000000"));
        mulentity.addPart("cardNO", new StringBody("000001"));
        mulentity.addPart("nfcID", new StringBody("1882573582"));
        //添加图片表单数据          
        File image = new File("c:/20150304_112634.jpg");
        System.out.println(image.exists());
        FileBody filebody = new FileBody(image);
        mulentity.addPart("image",filebody );      
        httpPost.setEntity(mulentity);  
        HttpResponse response = httpclient.execute(httpPost);  
          System.out.println("response status = "+response.getStatusLine().getStatusCode());
        if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)  
        {  
        	System.out.println("上传成功");
//            makeToase("上传成功",true);  
//            if(this.image.exists())  
//            this.image.delete();  
        }  
        else  
        {  
//            makeToase("上传失败",true);  
        }
	}
}
