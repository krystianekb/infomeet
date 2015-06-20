package org.infomeet.watcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.infomeet")
public class ApplicationLauncher {

	private static final Logger log = LoggerFactory.getLogger(ApplicationLauncher.class);
	
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
		joinTheSubThread();
		context.close();
	}

}
