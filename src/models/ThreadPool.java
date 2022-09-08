package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool{
	private static ThreadPool instance;
	private final ExecutorService executorService;
	private static final Logger logger = LogManager.getLogger(ThreadPool.class);
	
	public static ThreadPool getInstance() {
		if(instance == null)
			instance = new ThreadPool();
		return instance;
	}
	
	protected ThreadPool() {
		executorService = Executors.newCachedThreadPool();
	}
	
	public void execute(Runnable runnable) {
		executorService.submit(runnable);
	}

	public void clear() {
		try {
			boolean terminated = executorService.awaitTermination(5, TimeUnit.SECONDS);
			if(terminated){
				logger.info("Threads are terminated in time");
			}else{
				logger.info("Threads are terminated on timeout");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
