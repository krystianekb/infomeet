package org.infomeet.client.zookeeper;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.ServiceCacheListener;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ClientCacheServiceDicovery {

	@Inject
	private ServiceDiscovery<RestServiceDetails> discovery;

	@Inject
	ServiceCache<RestServiceDetails> serviceCache;

	 @PostConstruct
	 public void init() throws Exception {
		 serviceCache.addListener(new ServiceCacheListener() {
			
			public void stateChanged(CuratorFramework arg0, ConnectionState arg1) {
				
			}
			
			public void cacheChanged() {
				System.out.println("Added/Removed new server instance");				
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

}

