package com.evsward.server.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.dao.PrizeDao;
import com.evsward.server.facade.PrizeFacade;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.Prize;

@SuppressWarnings("serial")
@Namespace("/system/prize")
@ParentPackage(value = "hi")
public class PrizeAction extends StrutsAction<Prize, Integer> {

	@Resource
	private PrizeDao prizeDao;
	@Resource
	private PrizeFacade prizeFacade;
	
	public Prize getModel() {
		return null;
	}

	@Override
	public BaseService<Prize, Integer> getService() {
		return null;
	}
	
	/**
	 * 缓存奖励表信息
	 * @return
	 */
	@Action("cachePrizeList")
	public String cachePrizeListTest(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "cachePrizeListTest");
		try {
			this.prizeFacade.cachePrizeListMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	/**
	 * 查询一个比赛对应的奖励区间信息
	 * @return
	 */
	@Action("getScreenPrizeInfo")
	public String getPrizeAreaListByCompID(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getPrizeAreaListByCompID");
		HIUtil.sendResponseJson(this.prizeFacade.getPrizeArea(compID));
		return NONE;
	}
	
	private int compID;
	
	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}
	
	
	
	
	
	
	
	
	
	

	

	@Action("dealPrize")
	public String dealPrizes()throws Exception{
		String filePath = "D:\\netty work\\HI赛事\\hi赛事系统设计\\数据sql\\init_sql\\prize_init_end.sql";
		File file = new File(filePath);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		Prize prize = null;
		List<Prize> list = new ArrayList<Prize>();
		int i = 1;
		while((line = br.readLine()) != null){
			int start = line.indexOf(",");
			int end = line.indexOf("'2015-");
			String splitStr = line.substring(start+2, end-2);
			prize = new Prize();
			prize.setPrizeTempID(1);
			String[] arr = splitStr.split(", ");
			String allMin = arr[1].substring(1, arr[1].length()-1);
			prize.setAllMin(Integer.parseInt(allMin));
			prize.setAllMax(Integer.parseInt(arr[2].substring(1, arr[2].length()-1)));
			prize.setPercent(Double.parseDouble(arr[3].substring(1, arr[3].length()-1)));
			prize.setRankNum(Integer.parseInt(arr[4].substring(1, arr[4].length()-1)));
			prize.setSysType(1);
			prize.setCreateTime(new Date());
			if(list.size() == 0){
				prize.setRankNO(i);
			}else{
				Prize temp = list.get(list.size()-1);
				if(temp.getAllMax() != prize.getAllMax()){
					i=1;
					prize.setRankNO(i);
				}else{
					i++;
					prize.setRankNO(i);
				}
			}
			list.add(prize);
		}

		for (int j = 0; j < list.size(); j++) {
			this.prizeDao.insert("insertPrize", list.get(j));
		}
//		for (int i = 400; i <= 1400; i=i+100) {
//			Prize prize = new Prize();
//			prize.setAllMin(i+1);
//			List<Prize> list = this.prizeDao.getPrizes(prize);
//			System.out.println(list.size());
//			for (int j = 0; j < list.size(); j++) {
//				Prize p = list.get(j);
//				p.setRankNO(j+1);
//				p.setRankNum(list.size());
//				System.out.println(p.toString());
//			}
//			this.prizeDao.updatePrizes(list);
//			
//		}
		System.out.println("==================");
		return NONE;
	}
}
