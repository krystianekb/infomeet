package org.infomeet.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.infomeet")
public class ApplicationLauncher {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("org.infomeet");		

	}

}
