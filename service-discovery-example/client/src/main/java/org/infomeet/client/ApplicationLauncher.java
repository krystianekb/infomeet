package org.infomeet.client;

import java.util.Timer;
import java.util.TimerTask;

import org.infomeet.data.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestClientException;

@ComponentScan("org.infomeet")
public class ApplicationLauncher {

	private static final Logger log = LoggerFactory.getLogger(ApplicationLauncher.class);
	
	// in ms, 5000 = 5s
	private static final Integer PERIOD = 5000;
	
	private static void periodicInvocation(final GreetingRestClient client) {
				
		new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					try {
						Greeting result = client.invoke();
						log.info("Result : {}", result.getContent());
					} catch (RestClientException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
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
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("org.infomeet");
		periodicInvocation(context.getBean(GreetingRestClient.class));		
		joinTheSubThread();
	}

}
