package org.infomeet.client;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.curator.x.discovery.ServiceInstance;
import org.infomeet.client.zookeeper.ClientCacheServiceDicovery;
import org.infomeet.client.zookeeper.ServiceURLProvider;
import org.infomeet.data.Greeting;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class GreetingRestClient {

	private final Logger log = LoggerFactory.getLogger(getClass());
		
	@Inject
	@Named("clientServiceProviderServiceDicovery")
	ServiceURLProvider discovery;
	
	private RestTemplate rest = new RestTemplate();
	
	public Greeting invoke() throws RestClientException, Exception {
		String url = discovery.getInstanceURL();
		log.info("Invoking service at {} using {}",url, discovery.getClass().getSimpleName());
		return rest.getForObject(url, Greeting.class);
	}

}
