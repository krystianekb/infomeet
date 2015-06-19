package org.infomeet.leader.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActualTask {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public void doWork() throws InterruptedException {
		log.info("Doing work...");
		Thread.sleep(10000);
		log.info("Finished...");
	}
	
}
