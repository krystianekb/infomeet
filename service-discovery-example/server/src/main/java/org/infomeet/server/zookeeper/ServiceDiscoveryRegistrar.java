package org.infomeet.server.zookeeper;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.stereotype.Component;

@Component
public class ServiceDiscoveryRegistrar {
	
	@Inject 
	private ServiceDiscovery<RestServiceDetails> discovery;
	
	@Inject
	private ServiceInstance<RestServiceDetails> serviceInstance;
	
	@PostConstruct
	public void init() throws Exception {
		discovery.registerService(serviceInstance);
	}

}
