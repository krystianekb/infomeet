package org.infomeet.client.zookeeper;

import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuratorAppConfig {
	
	private static final String INFOMEET_SERVICE_DISCOVERY_EXAMPLE = "infomeet-service-discovery-example";

	public static final String NAME = "greeting";
	
	@Inject
	private CuratorFramework curator;
			
	@Bean(initMethod = "start", destroyMethod = "close")
	public ServiceDiscovery<RestServiceDetails> serviceDiscovery() throws Exception  {
		return ServiceDiscoveryBuilder.builder(RestServiceDetails.class)
				.basePath(INFOMEET_SERVICE_DISCOVERY_EXAMPLE).client(curator).build();
	}
	
}
