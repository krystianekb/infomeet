package org.infomeet.server.zookeeper;

import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuratorAppConfig {
	
	private static final String REST_VERSION = "1.0";

	private static final String INFOMEET_SERVICE_DISCOVERY_EXAMPLE = "infomeet-service-discovery-example";

	public static final String NAME = "greeting";
	
	@Value("${server.port:8080}")
	private Integer port;
	
	@Value("${server.host:localhost}")
	private String host;

	@Inject
	private CuratorFramework curator;
	
	@Inject
	private ServiceInstance<RestServiceDetails> serviceInstance;
	
	@Bean
	public ServiceInstance<RestServiceDetails> serviceInstance() throws Exception {
		return ServiceInstance.
			<RestServiceDetails> builder()
			.uriSpec(new UriSpec("{scheme}://{address}:{port}/{name}"))
			.address(host)
			.port(port)
			.name(NAME)
			.payload(new RestServiceDetails(REST_VERSION))				
			.build();
	}
	
	@Bean(initMethod = "start", destroyMethod = "close")
	public ServiceDiscovery<RestServiceDetails> serviceDiscovery() throws Exception  {
		return ServiceDiscoveryBuilder.builder(RestServiceDetails.class)
				.basePath(INFOMEET_SERVICE_DISCOVERY_EXAMPLE).client(curator)
				.thisInstance(serviceInstance).build();
	}
	
}
