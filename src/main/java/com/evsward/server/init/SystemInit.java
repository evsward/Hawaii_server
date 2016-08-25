package com.evsward.server.init;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.service.PrizeService;
import com.evsward.server.service.RoundTempService;
import com.evsward.server.service.ScreensManageService;
import com.evsward.server.service.SystemTcpMsgManageService;
import com.evsward.server.util.Application;
import com.evsward.server.util.SystemCache;

/**
 * 系统初始化
 */
public class SystemInit {
	private static Logger logger = LoggerFactory.getLogger(SystemInit.class);
	
	private static AtomicBoolean classInited = new AtomicBoolean(false);
       
	@Resource
	private CompetitionManageFacade compFacade;
	@Resource
	private SystemTcpMsgManageService sysTcpMsgManService;
	@Resource
	private ScreensManageService screenService;
	@Resource
	private PrizeService prizeService;
	@Resource
	private RoundTempService roundTempService;
	
    public SystemInit() {
        super();
    }

    public void init() {
    	if(classInited.getAndSet(true)){
    		return;
    	}
    	long start = System.currentTimeMillis();
    	logger.info(Application.TPS + "SystemInit初始化开始" + Application.TPS);
    	//初始化系统消息推送关系
		try {
			SystemCache.msgCmdList = sysTcpMsgManService.getAllSystemTcpMsg();
			SystemCache.msgPushFuncList = sysTcpMsgManService.getAllSystemTcpMsgPushFunc();
			SystemCache.funcMsgCmdListMap = sysTcpMsgManService.getFuncMsgListMapping();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.exit(0);//结束服务
		}
		logger.info(Application.TPS + "SytemMsg初始化完毕" + Application.TPS);
		
		//初始化所有大屏幕设备状态为断线
		try {
			screenService.initAllScreenStateNoOnline();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info(Application.TPS + "大屏幕设备初始化完毕" + Application.TPS);
		
		//缓存Prize记录
		try {
			prizeService.getPrizeListMap(SystemCache.prizeMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.exit(0);//结束服务
		}
		logger.info(Application.TPS + "奖励表缓存初始化完毕" + Application.TPS);
		
		//缓存Round记录
		try {
			roundTempService.getAllTempListWithRound(SystemCache.roundMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.exit(0);//结束服务
		}
		logger.info(Application.TPS + "盲注模板缓存初始化完毕" + Application.TPS);
		
		//启动消息推送线程组
		try {
			Application.startPushMsgServiceEntrance();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.exit(0);//结束服务
		}
		logger.info(Application.TPS + "消息推送线程初始化完毕" + Application.TPS);
		
		//初始化比赛后台服务管理线程
		boolean initResult = compFacade.initCompServerManageThread_Serv();
		if(!initResult){
			System.exit(0);//结束服务
		}
		logger.info(Application.TPS + "比赛后台维护线程初始化完毕" + Application.TPS);
		long end = System.currentTimeMillis() - start;
		logger.info(Application.TPS + "SystemInit初始化完毕，耗时【" + end + "】毫秒" + Application.TPS);
    }
}
