package com.evsward.server.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.evsward.server.vo.CompetitionMember;

public class Test {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(1);
		list.add(210);
		list.add(9);
		list.add(22);
		list.add(211);
		list.add(11);
		list.add(12);
		list.add(8);
		list.add(4);
		list.add(-1);
		Collections.sort(list, new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1 < o2){
					return -1;
				}else if(o1 == o2){
					return 0;
				}else{
					return 1;
				}
			}
		});
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		
		// Date d=new Date();
		// System.out.println(d.getTime()+3600*24+3600*12);
		// SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd E");
		// System.out.println(sf.format(d));

		// final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
		// "星期六" };
		//
		// SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		//
		// Calendar calendar = Calendar.getInstance();
		// Date date = new Date();
		//
		// calendar.setTime(date);
		// int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		//
		// System.out.println(sdfInput.format(date) + " " + dayNames[dayOfWeek -
		// 1]);
	}
}
