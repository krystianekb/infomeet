package org.infomeet.client.zookeeper;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.infomeet.client.zookeeper.CuratorAppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceDicovery {

	@Inject
	private ServiceDiscovery<RestServiceDetails> discovery;
	
	private Collection<ServiceInstance<RestServiceDetails>> services;

	@PostConstruct
	public void init() throws Exception {
		services = discovery.queryForInstances(CuratorAppConfig.NAME);		
	}
	
	public Collection<ServiceInstance<RestServiceDetails>> getServices() {
		return services;
	}
	
	@Bean(initMethod = "start", destroyMethod = "close")
	public ServiceProvider<RestServiceDetails> serviceProvider() {
		return discovery.serviceProviderBuilder().serviceName(CuratorAppConfig.NAME).build();
	}

}
