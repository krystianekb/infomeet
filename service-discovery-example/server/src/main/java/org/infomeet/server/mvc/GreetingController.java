package org.infomeet.server.mvc;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.infomeet.data.Greeting;
import org.infomeet.server.zookeeper.CuratorAppConfig;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	public static final String PATH = "/" + CuratorAppConfig.NAME;

	@Value("${instancename:greetingcontroller}")
	private String instanceName;

	private static final String template = "%s : Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(PATH)
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template,
				instanceName, name));
	}


}
