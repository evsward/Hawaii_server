package com.evsward.server.test.yali;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
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

import com.evsward.server.util.HIUtil;



public class PlayersRegister implements Runnable{
	
	public static final int SIZE = 2000;

	private static List<String> playerNames = new ArrayList<String>();
	private static List<String> playerIndentys = new ArrayList<String>();
	private static List<String> playermobiles = new ArrayList<String>();
	private static List<String> nfcIds = new ArrayList<String>();
	private static List<String> cardNOs = new ArrayList<String>();
	private static File smallfiles[] = null;
	private static File bigfiles[] = null;
	
	public String name;
	public String identy;
	public String mobile;
	public String nfcId;
	public int sex;
	public String cardNO;
	/**
	 * 随机2000组选手信息
	 * @throws IOException 
	 */
	public static void init() throws IOException{
		//1、随机选手姓名，全部是 英文，长度固定10个英文字母，可以重复
		//3、随机选手身份证号，无重复
		//4、随机选手手机号，可以重复
		String consts = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
		Set<String> _playerIndentys = new HashSet<String>();
		
		Random rand = new Random();
		int index = 0;
		String constArr[] = consts.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < 10; j++) {
				index = rand.nextInt(52);
				sb.append(constArr[index]);
			}
			playerNames.add(sb.toString());
			sb.setLength(0);
		}
		sb.setLength(0);
		
		String indentyConst = "1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0";
		String indextyConstArr[] = indentyConst.split(",");
		while(_playerIndentys.size() < SIZE){
			for (int j = 0; j < 18; j++) {
				index = rand.nextInt(40);
				sb.append(indextyConstArr[index]);
			}
			_playerIndentys.add(sb.toString());
			sb.setLength(0);
		}
		sb.setLength(0);
		Iterator<String> it = _playerIndentys.iterator();
		while(it.hasNext()){
			playerIndentys.add(it.next());
		}
		
		
		while(playermobiles.size() < SIZE){
			sb.append("1");
			for (int i = 0; i < 10; i++) {
				index = rand.nextInt(40);
				sb.append(indextyConstArr[index]);
			}
			playermobiles.add(sb.toString());
			sb.setLength(0);
		}
		
		System.out.println(playerNames.size());
		System.out.println(playerIndentys.size());
		System.out.println(playermobiles.size());
		
		
		//读取数据文件
		File file = new File("c:/hiphoto/initednfc.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while(line != null){
			if(!line.isEmpty()){
				String arr[] = line.split(",");
				cardNOs.add(HIUtil.getMemCardNO(Integer.parseInt(arr[0])));
				nfcIds.add(arr[1]);
				System.out.println(arr[0] + ", " + arr[1]);
			}
			line = br.readLine();
		}
		
		//复制照片
		File photosmall = new File("C:/hiphoto/small");
		File photobig = new File("C:/hiphoto/big");
		String path = "d:/server/hiserver/memimage/";
		bigfiles = photobig.listFiles();
		smallfiles = photosmall.listFiles();
		for (int i = 0; i < SIZE; i++) {
			index = rand.nextInt(75);
			
			String filenameBig = path + cardNOs.get(i) + ".jpg";
			File destFileBig = new File(filenameBig);
			if(!destFileBig.exists()){
				destFileBig.createNewFile();
			}
			FileUtils.copyFile(bigfiles[index], destFileBig);
			
			String filenamesmall = path + "small/" + cardNOs.get(i) + ".jpg";
			File destFileSmall = new File(filenamesmall);
			if(!destFileSmall.exists()){
				destFileSmall.createNewFile();
			}
			FileUtils.copyFile(smallfiles[index], destFileSmall);
		}
	}
	
	@Override
	public void run() {
		String url = "http://localhost:7979/member/member/addMemberTest.do";
		HttpClient httpclient = new DefaultHttpClient();  
    	HttpPost httpPost = new HttpPost(url);
    	List<NameValuePair> formParams = new ArrayList<NameValuePair>();  
        formParams.add(new BasicNameValuePair("nfcID", nfcId));  
        formParams.add(new BasicNameValuePair("cardNO", cardNO));  
        formParams.add(new BasicNameValuePair("memName", name));  
        formParams.add(new BasicNameValuePair("mobile", mobile));  
        formParams.add(new BasicNameValuePair("memSex", sex+""));  
        formParams.add(new BasicNameValuePair("identno", identy));  
        formParams.add(new BasicNameValuePair("sysType", "1"));  
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

	public static void main(String[] args) throws InterruptedException, IOException {
//		init();
//		for (int i = 0; i < 1; i++) {
//			PlayersRegister pr = new PlayersRegister();
//			pr.nfcId = nfcIds.get(i);
//			pr.name = playerNames.get(i);
//			pr.mobile = playermobiles.get(i);
//			pr.identy = playerIndentys.get(i);
//			pr.sex = i%2;
//			pr.cardNO = cardNOs.get(i);
//			new Thread(pr).start();
//			Thread.sleep(1000);
//		}
		
	}
}
