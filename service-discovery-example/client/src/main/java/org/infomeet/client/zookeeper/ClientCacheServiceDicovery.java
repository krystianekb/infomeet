package org.infomeet.client.zookeeper;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.ServiceCacheListener;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("clientCacheServiceDicovery")
public class ClientCacheServiceDicovery implements ServiceURLProvider {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ServiceDiscovery<RestServiceDetails> discovery;

	@Inject
	ServiceCache<RestServiceDetails> serviceCache;
	
	private AtomicInteger counter = new AtomicInteger(0);
	
	 @PostConstruct
	 public void init() throws Exception {
		 serviceCache.addListener(new ServiceCacheListener() {
			
			public void stateChanged(CuratorFramework arg0, ConnectionState arg1) {
				
			}
			
			public void cacheChanged() {
				log.info("Added/Removed new server instance");	
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

	public Collection<String> getAllInstancesURLs() throws Exception {				
		return null;
	}

	public String getInstanceURL() throws Exception {
		List<ServiceInstance<RestServiceDetails>> c = serviceCache.getInstances();
		return c.get(counter.getAndIncrement() % c.size()).buildUriSpec();
	}

}

