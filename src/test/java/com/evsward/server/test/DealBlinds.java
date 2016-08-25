package com.evsward.server.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class DealBlinds {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String path = "c:\\网吧德州竞技比赛盲注表.txt";
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String line = br.readLine();
		String[] data = null;
		String sqltemp = "insert into round(roundtempid, roundstatus, beforechip, smallblind, bigblind, roundtype, roundrank, steplen, systype) values(2, 0, %s, %s, %s, %s, %s, %s, 1);";
		while(line != null){
			if(!StringUtils.isEmpty(line)){
				data = line.split(",");
				if(data.length != 6){
					throw new Exception("数据格式不正确");
				}
				System.out.println(String.format(sqltemp, data[0], data[1], data[2], data[3], data[4], data[5]));
			}
			line = br.readLine();
		}
	}

}
