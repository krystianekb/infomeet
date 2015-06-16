package org.infomeet.client.zookeeper;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.stereotype.Component;

@Component("clientServiceDiscovery")
public class ClientServiceDicovery implements ServiceURLProvider{

	@Inject
	private ServiceDiscovery<RestServiceDetails> discovery;
		
	public Collection<ServiceInstance<RestServiceDetails>> getServices() throws Exception {
		return discovery.queryForInstances(CuratorAppConfig.NAME);
	}
	
	public String getInstanceURL() throws Exception {
		
		Collection<ServiceInstance<RestServiceDetails>> col = discovery.queryForInstances(CuratorAppConfig.NAME);
		return col.iterator().next().buildUriSpec();
	}

	public Collection<String> getAllInstancesURLs() throws Exception {
		Collection<String> tmp = new ArrayList<String>();		
		for (ServiceInstance<RestServiceDetails> instance: discovery.queryForInstances(CuratorAppConfig.NAME)) {
			tmp.add(instance.buildUriSpec());
		}		
		return tmp;
	}

}
