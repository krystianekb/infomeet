package org.infomeet.client;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.infomeet")
public class ApplicationLauncher {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("org.infomeet");		
		Object lock = new Object();
        synchronized (lock) {
            lock.wait();  
        }
        context.close();
	}

}
