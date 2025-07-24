package com.fengshen.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 线程池工具类
 * 
 * 
 *
 */
public class ExecutorsUtils {

	//线程池对象
	private final static ExecutorService pool = new ThreadPoolExecutor(5, 1024, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder().setNameFormat("pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
	
	
	/**
	 * 获取线程池对象
	 * @return
	 */
	public static ExecutorService getExecutorPools() {
		return pool;
	}
}
