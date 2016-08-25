package com.evsward.server.init;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evsward.server.util.Application;

public class SystemInitListener implements ServletContextListener{

	private static Logger logger = LoggerFactory.getLogger(SystemInitListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info(Application.TPS + "SystemInitListener初始化开始" + Application.TPS);
		//获取项目根目录
		ServletContext context = sce.getServletContext();
		Application.PATH_ROOT = context.getRealPath("/");
		logger.info(Application.PATH_ROOT + Application.PATH_IMAGE + System.getProperty("file.separator"));
		//创建头像文件目录（如果存在就不创建）
		File imageDic = new File(Application.PATH_ROOT + Application.PATH_IMAGE + System.getProperty("file.separator") + Application.PATH_IMAGE_SMALL);
		if(!imageDic.exists()){
			imageDic.mkdirs();
		}
		logger.info(imageDic.getAbsolutePath());
		logger.info(Application.TPS + "头像文件目录初始化完毕" + Application.TPS);
		//创建广告图片文件目录（如果存在就不创建）
		File advertDic = new File(Application.PATH_ROOT + Application.PATH_ADVERTISMENT);
		if(!advertDic.exists()){
			advertDic.mkdirs();
		}
		logger.info(advertDic.getAbsolutePath());
		logger.info(Application.TPS + "广告文件目录初始化完毕" + Application.TPS);
		//创建广告图片文件目录（如果存在就不创建）
		File exportDic = new File(Application.PATH_ROOT + Application.PATH_EXPORTFILE);
		if(!exportDic.exists()){
			exportDic.mkdirs();
		}
		logger.info(exportDic.getAbsolutePath());
		logger.info(Application.TPS + "文件导出目录初始化完毕" + Application.TPS);
		
		logger.info(Application.TPS + "SystemInitListener初始化完毕" + Application.TPS);
	}

	/**
	 * 中断Application中的线程组的所有线程
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}

	
}
