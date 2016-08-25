package com.evsward.server.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.utils.encode.JsonBinder;
import com.dance.core.utils.web.StrutsUtils;
import com.evsward.server.vo.CardTable;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.Prize;
import com.evsward.server.vo.UnAllotedSeat;

public class HIUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HIUtil.class);
	private static final String ENCODING = "utf-8";
	private static final int CARDNOLENGTH = 6;
	private static final String CARDNOARRAY[] = {"0", "00", "000", "0000", "00000"};
	
	/**
	 * 计算比赛的总奖金(减去扣除人数)
	 * @param compLeastPrize
	 * @param compRegFee
	 * @param compTotalRegedPlayer
	 * @param compSubPlayer
	 * @return
	 */
	public static int calcCompTotalPrizeMoney(int compLeastPrize, int compRegFee, int compTotalRegedPlayer, int compSubPlayer){
		int totalPrize = 0;
		totalPrize = (compTotalRegedPlayer - compSubPlayer) * compRegFee;
		if(compLeastPrize > 0 && compLeastPrize > totalPrize){
			return compLeastPrize;
		}
		return totalPrize;
	}
	
	/**
	 * 计算比赛的总奖金
	 * @param compLeastPrize
	 * @param compRegFee
	 * @param compTotalRegedPlayer
	 * @param compSubPlayer
	 * @return
	 */
	public static int calcCompTotalPrizeMoney(int compLeastPrize, int compRegFee, int compTotalRegedPlayer){
		return calcCompTotalPrizeMoney(compLeastPrize, compRegFee, compTotalRegedPlayer, 0);
	}
	
	/**
	 * 根据报名人数查询奖励区间
	 * @param compRegedTotalPlayerCount
	 * @return
	 */
	public static List<Prize> getPrizeListByPlayerCount(long compRegedTotalPlayerCount){
		Set<Entry<String, List<Prize>>> set = SystemCache.prizeMap.entrySet();
		Iterator<Entry<String, List<Prize>>> it = set.iterator();
		Entry<String, List<Prize>> entry = null;
		int allMin = 0;
		int allMax = 0;
		while(it.hasNext()){
			entry = it.next();
			String key = entry.getKey();
			allMin = Integer.parseInt(key.split("-")[0]);
			allMax = Integer.parseInt(key.split("-")[1]);
			if(compRegedTotalPlayerCount <= allMax && compRegedTotalPlayerCount >= allMin){
				return entry.getValue();
			}
		}
		return Collections.emptyList();
	}
	
	/**
	 * 获取座位的分配优先级别
	 * @param seatNO
	 * @param tableType
	 * @return
	 */
	public static int getSeatLevel(int seatNO, int tableType){
		if(tableType == CompetitionInfo.TABLETYPE.SIX){
			if(seatNO == 1){
				return 2;
			}else if(seatNO == 6){
				return 3;
			}else{
				return 1;
			}
		}else if(tableType == CompetitionInfo.TABLETYPE.NINE){
			if(seatNO == 1){
				return 3;
			}else if(seatNO == 5){
				return 2;
			}else if(seatNO == 9){
				return 4;
			}else{
				return 1;
			}
		}else if(tableType == CompetitionInfo.TABLETYPE.TEN){
			if(seatNO == 1){
				return 3;
			}else if(seatNO == 5){
				return 2;
			}else if(seatNO == 9){
				return 4;
			}else if(seatNO == 10){
				return 5;
			}else{
				return 1;
			}
		}
		return 1;
	}
	
	/**
	 * 
	 * @param compID
	 * @param tableType
	 * @param sysType
	 * @param cardTableList
	 * @return
	 */
	public static List<UnAllotedSeat> creatRandomSeat(int compID, int tableType, int sysType, List<CardTable> cardTableList){
		Random r = new Random();
		List<UnAllotedSeat> list = new ArrayList<UnAllotedSeat>();
		List<UnAllotedSeat> insertList = new ArrayList<UnAllotedSeat>();
		CardTable table = null;
		UnAllotedSeat seat = null;
		for (int tcIndex = 0; tcIndex < cardTableList.size(); tcIndex++) {
			table = cardTableList.get(tcIndex);
			if(tableType == CompetitionInfo.TABLETYPE.SIX){
				for (int j = 1; j <= tableType; j++) {
					if(j == 1){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 2, tableType, sysType);
					}else if(j == 6){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 3, tableType, sysType);
					}else{
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 1, tableType, sysType);
					}
					list.add(seat);
				}
			}else if(tableType == CompetitionInfo.TABLETYPE.NINE){
				for (int j = 1; j <= tableType; j++) {
					if(j == 1){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 3, tableType, sysType);
					}else if(j == 5){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 2, tableType, sysType);
					}else if(j == 9){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 4, tableType, sysType);
					}else{
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 1, tableType, sysType);
					}
					list.add(seat);
				}
			}else if(tableType == CompetitionInfo.TABLETYPE.TEN){
				for (int j = 1; j <= tableType; j++) {
					if(j == 1){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 3, tableType, sysType);
					}else if(j == 5){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 2, tableType, sysType);
					}else if(j == 9){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 4, tableType, sysType);
					}else if(j == 10){
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 5, tableType, sysType);
					}else{
						seat = new UnAllotedSeat(table.getTableNO(), j, compID, 0, 1, tableType, sysType);
					}
					list.add(seat);
				}
			}
		}
		//随机list中的所有元素
		int size = list.size();
		long createTime = new Date().getTime();
		while(list.size() > 0){
			int index = r.nextInt(size);
			seat = list.remove(index);
			seat.setCreateTime(createTime);
			insertList.add(seat);
			size = list.size();
			createTime += 1;
		}
		return insertList;
	}
	
	/**
	 * 创建用户的卡号
	 * @param id
	 * @return
	 */
	public static String getMemCardNO(int id){
		String memIDStr = String.valueOf(id);
		return CARDNOARRAY[CARDNOLENGTH - memIDStr.length() - 1] + memIDStr;
	}
	
	/**
	 * 创建json返回时用的初始化的map对象,带有额外对象和key
	 * @param rspCode	返回的状态
	 * @param msg		返回的状态描述
	 * @return
	 */
	public static Map<String, Object> createJsonResInitMap(final int rspCode, final String msg, final Map<String, Object> map){
		Map<String, Object> resmap = createJsonResInitMap(rspCode, msg);
		Set<Entry<String, Object>> set = map.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		while(it.hasNext()){
			Entry<String, Object> entry = it.next();
			resmap.put(entry.getKey(), entry.getValue());
		}
		return resmap;
	}
	
	/**
	 * 创建json返回时用的初始化的map对象
	 * @param rspCode	返回的状态
	 * @param msg		返回的状态描述
	 * @return
	 */
	@SuppressWarnings("serial")
	public static Map<String, Object> createJsonResInitMap(final int rspCode, final String msg){
		Map<String, Object> resmap = new HashMap<String, Object>(){{
			put("rspCode",rspCode);
			put("msg", msg);
		}};
		return resmap;
	}
	
	/**
	 * 转换java对象为json字符串，只转换非null值的属性
	 * @param resmap
	 * @return
	 */
	public static String toJsonNonNullField(Map<String, Object> resmap){
		JsonBinder jsonBinder = JsonBinder.buildNonNullBinder();
		String jsonRes = "";
		jsonRes = jsonBinder.toJson(resmap);
//		logger.error(jsonRes);
		return jsonRes;
	}
	
	/**
	 * 转换java对象为json字符串，所有属性
	 * @param resmap
	 * @return
	 */
	public static String toJsonNormalField(Map<String, Object> resmap){
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder();
		String jsonRes = "";
		jsonRes = jsonBinder.toJson(resmap);
//		logger.info(jsonRes);
		return jsonRes;
	}
	
	/**
	 * 格式化16进制的nfctagID成10进制数字
	 * @param nfctagIDHex
	 * @return
	 * 	null：格式化错误
	 */
	public static Long convertHex2Long(String nfctagIDHex){
		Long nfctagIDDecimal = null;
		if(StringUtils.isEmpty(nfctagIDHex)){
			return nfctagIDDecimal;
		}
		try {
			nfctagIDDecimal = Long.parseLong(nfctagIDHex,16);
		} catch (NumberFormatException e) {
			logger.error(nfctagIDHex + " format 16hex to 10decimal is error", e);
			return nfctagIDDecimal;
		}
		return nfctagIDDecimal;
	}
	
	/** 
	 * 直接发送数据 
	 * 
	 * @param str 到发送的数据
	 * @return 是否发送成功
	 * @date 2008-6-19 
	 */
	public static void sendResponseJson(String str){
		sendResponse(str, "text/json; Charset=" + ENCODING);
	}
	
	/** 
	 * 直接发送数据 
	 * 
	 * @param str 到发送的数据
	 * @return 是否发送成功
	 * @date 2008-6-19 
	 */
	public static void sendResponse(String str, String contentType){
		HttpServletResponse response = StrutsUtils.getResponse();
		if (contentType == null){
			response.setContentType("text/html; Charset=" + ENCODING);
		}
		else if (contentType.length() > 0){
			response.setContentType(contentType);
		}
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");	// HTTP 1.1
		response.setHeader("Pragma", "no-cache");	// HTTP 1.0
		response.setDateHeader("Expires", 0);		// prevents caching at the proxy server 
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			outputStream.write(str.getBytes(ENCODING));
		} catch (Exception e) {
			logger.error("向客户端流写会【"+str+"】数据出错", e);
		}finally{
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断该字符串是否是数字,只能包含0-9的数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**	
	 * 精确四舍五入
	 * @param dNum
	 * @param scale 小数位数
	 * @return
	 */
	public static double round(double dNum, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal bigNum = new BigDecimal(Double.toString(dNum));
        BigDecimal one = new BigDecimal("1");        
        return bigNum.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
	/**
	 * 取小数点后两位,如果是整数,会小数点后加上0,再输出(如 11,输出11.00,.29变成0.29)
	 * @param money
	 * @param pattern
	 * @return 输出格式（#.####）
	 */
	public static String formatMoney(Double money,String pattern){
		if(pattern == null || "".equals(pattern)){
			pattern = "0.0000";
		}
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(money);
	}
	
	/**
	 * 打印请求的参数,只能在web请求下使用
	 * @param request
	 * @param simpleName
	 */
	@SuppressWarnings("unchecked")
	public static void printRequestParam(String actionName, String methodName){
		HttpServletRequest request = StrutsUtils.getRequest();
		Map<String, Object> notifyData = new HashMap<String, Object>();
		Map<String, String[]> param = request.getParameterMap();
		Set<Entry<String, String[]>> set = param.entrySet();
		Iterator<Entry<String, String[]>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			notifyData.put(entry.getKey(), entry.getValue() != null ? entry.getValue()[0] : null);
		}
		long now = System.currentTimeMillis();
		logger.info(now+"=================" + actionName + "." + methodName + " ====== start");
		for(Entry<String, Object> entry : notifyData.entrySet()){
			if(entry != null && entry.getValue() != null){
				logger.info(now+" "+entry.getKey() + "\t=" + entry.getValue().toString());	
			}
		}
		logger.info(now+" clientIP\t=" + request.getRemoteAddr());
	}
	
	public static void main(String[] args) {
		
//		Map<String, Object> resmap = createJsonResInitMap(100,"fadfasfd");
//		System.out.println(resmap.get("rspCode"));
//		System.out.println(resmap.get("msg"));
		//B008BF0E
//		long l = convertHex2Long("041759DA493480");
//		System.out.println(l);
//		int cm = 100;
//		int a = 0x08000000;
//		System.out.println((cm | a ) - a);
		
//		JsonBinder jsonBinder = JsonBinder.buildNormalBinder();
//		String str = "{\"IMEI\":\"fasdfasdfasdfasdf\"}";
//		String imei = jsonBinder.getValue(JsonBinder.JSONObject, str, "IMEI");
//		System.out.println(imei);
		
		String ipStr = "192.168.1.22";
		String[] ipArr = ipStr.split("\\.");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ipArr.length; i++) {
			String hex = Integer.toHexString(new Integer(ipArr[i]));
			if(hex.length() <= 1){
				hex = "0" + hex;
			}
			sb.append(hex.toUpperCase());
			System.out.println(hex.toUpperCase());
		}
		long ip = Long.parseLong(sb.toString(), 16);
		System.out.println(ip);
		
		String ipHex = Long.toHexString(ip);
		System.out.println(ipHex.toUpperCase());
		sb.setLength(0);
		for (int i = 0; i < ipHex.length(); i=i+2) {
			int t = Integer.parseInt(ipHex.toUpperCase().substring(i, i+2),16);
			sb.append(t).append(".");
		}
		System.out.println(sb.toString().substring(0, sb.toString().length() - 1));
		
//		convertHex2Long("172.28.25.66");
	}
}
