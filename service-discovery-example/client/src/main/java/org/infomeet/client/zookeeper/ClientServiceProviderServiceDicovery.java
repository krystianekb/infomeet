package org.infomeet.client.zookeeper;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.curator.x.discovery.DownInstancePolicy;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.strategies.RoundRobinStrategy;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("clientServiceProviderServiceDicovery")
public class ClientServiceProviderServiceDicovery implements ServiceURLProvider {
	
	@Inject
	private ServiceDiscovery<RestServiceDetails> discovery;
	
	@Inject
	ServiceProvider<RestServiceDetails> provider;
		
	@Bean(initMethod = "start", destroyMethod = "close")
	public ServiceProvider<RestServiceDetails> serviceProvider() {
		return discovery.serviceProviderBuilder()
				.providerStrategy(new RoundRobinStrategy<RestServiceDetails>())
				.downInstancePolicy(new DownInstancePolicy(1, TimeUnit.SECONDS, 1))				
				.serviceName(CuratorAppConfig.NAME).build();
	}
	
	public Collection<String> getAllInstancesURLs() throws Exception {				
		return null;
	}

	public String getInstanceURL() throws Exception {
		return provider.getInstance().buildUriSpec();
	}

}

