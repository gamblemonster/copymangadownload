package download.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.hutool.core.lang.Singleton;
import download.service.Download;

public class DownloadThreadPool {
	private Map<String, Future<?>> futureMap;
	
	private ExecutorService executorService;
	
	private DownloadThreadPool() {
		try {
			executorService = Executors.newVirtualThreadPerTaskExecutor();
		} catch (NoSuchMethodError e) {
			// TODO: handle exception
			executorService = Executors.newCachedThreadPool();
		}
		
		futureMap = new HashMap<>();
	}
	
	private ExecutorService getExecutorService() {
		return executorService;
	}
	
	private Map<String, Future<?>> getFutureMap() {
		return futureMap;
	}
	
	public static void execute(Download runnable) {
		DownloadThreadPool threadPool = Singleton.get(DownloadThreadPool.class);
		if (threadPool.getFutureMap().get(runnable.getId()) == null || threadPool.getFutureMap().get(runnable.getId()).isDone()) {
			threadPool.getFutureMap().put(runnable.getId(), threadPool.getExecutorService().submit(runnable));
		}
	}
}
