package com.example.customrecycle.frame.application;

import android.app.Application;


/***
 * 初始化Application
 * @author GISirFive
 * @since 2016-1-12 下午2:26:54
 */
public interface IAppInitControl {
	
	/**
	 * 公共初始化
	 * @author GISirFive
	 */
	void init(Application context);
	
	/**
	 * 初始化日志
	 * @author GISirFive
	 */
	void initLog();
	
	/**
	 * 初始化加密/解密
	 * @author GISirFive
	 */
	void initSecurity();
	



	/**
	 * 初始化版本更新
	 * @author GISirFive
	 */
	void initUpdateVersion();
	

	/**
	 * 持久化Cookie，自动登录
	 * @author GISirFive
	 */
	void initCookieStore();
	
	/**
	 * 初始化数据库
	 * @author GISirFive
	 */
	void initDatabase();
	
	/**
	 * 初始化短信验证
	 * @author GISirFive
	 */
	void initSMS();
	
	/**
	 * 初始化社会化分享
	 * @author GISirFive
	 */
	void initSocialization();
	
	/**
	 * 初始化统计服务
	 * @author GISirFive
	 */
	void initStatistics();
}
