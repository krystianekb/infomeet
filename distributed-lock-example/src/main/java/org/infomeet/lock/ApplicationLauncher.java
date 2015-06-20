package org.infomeet.lock;

import java.util.Timer;
import java.util.TimerTask;

import org.infomeet.lock.zookeeper.CuratorLockService;
import org.infomeet.lock.zookeeper.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.infomeet")
public class ApplicationLauncher {

	private static final Logger log = LoggerFactory.getLogger(ApplicationLauncher.class);
	
	// in ms, 5000 = 5s
	private static final Integer PERIOD = 5000;
	
	private static void periodicInvocation(final CuratorLockService client) {
		
		new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					try {
						client.doWork(new Task() {

							public void doWork() throws InterruptedException {
								log.info("Executing task");
								Thread.sleep(PERIOD/2);								
							}
							
						});
					} catch (Exception e) {						
						log.error("Exception caught : ", e);
					}
				}
				
			}, 0, PERIOD);
		
	}
	
	private static void joinTheSubThread() {
		   try {
		        Thread.currentThread().join();
		    } catch (InterruptedException e) {
		        log.warn("Interrupted", e);
		    }
	}
	
	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
				"org.infomeet");
		periodicInvocation(context.getBean(CuratorLockService.class));
		joinTheSubThread();
		context.close();
		
	}

}
