package download.thread;

import java.util.concurrent.ExecutorService;

import cn.hutool.core.thread.ThreadUtil;

public class ThreadPool {
	private static final int THREAD_POOL_SIZE = 4; // 防止请求频繁，降低服务器的压力
	
	private static ExecutorService executorService;
	
	public static void execute(Runnable runnable) {
		if (null == executorService) {
			executorService = ThreadUtil.newExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE);
		}
		executorService.execute(runnable);
	}
}
