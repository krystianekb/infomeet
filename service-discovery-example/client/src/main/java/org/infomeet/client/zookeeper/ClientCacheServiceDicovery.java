package org.infomeet.client.zookeeper;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.ServiceCacheListener;
import org.apache.curator.x.discovery.strategies.RoundRobinStrategy;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ClientCacheServiceDicovery implements ServiceURLProvider {

	@Inject
	private ServiceDiscovery<RestServiceDetails> discovery;

	@Inject
	ServiceCache<RestServiceDetails> serviceCache;
	
	@Inject
	ServiceProvider<RestServiceDetails> provider;
	
	private Iterator<ServiceInstance<RestServiceDetails>> iterator;

	private AtomicInteger counter = new AtomicInteger(0);
	
	 @PostConstruct
	 public void init() throws Exception {
		 serviceCache.addListener(new ServiceCacheListener() {
			
			public void stateChanged(CuratorFramework arg0, ConnectionState arg1) {
				
			}
			
			public void cacheChanged() {
				System.out.println("Added/Removed new server instance");	
				iterator = serviceCache.getInstances().iterator();
			}
		});
	 }

	public Collection<ServiceInstance<RestServiceDetails>> getServices() {
		return serviceCache.getInstances();
	}

	@Bean(initMethod = "start", destroyMethod = "close")
	public ServiceCache<RestServiceDetails> serviceCache() {
		return discovery.serviceCacheBuilder().name(CuratorAppConfig.NAME)
				.build();
	}

	@Bean(initMethod = "start", destroyMethod = "close")
	public ServiceProvider<RestServiceDetails> serviceProvider() {
		return discovery.serviceProviderBuilder()
				.providerStrategy(new RoundRobinStrategy<RestServiceDetails>())
				.serviceName(CuratorAppConfig.NAME).build();
	}
	
	public Collection<String> getAllInstancesURLs() throws Exception {
		
		
		return null;
	}

	public String getInstanceURL() throws Exception {
		return provider.getInstance().buildUriSpec();
	}

}

