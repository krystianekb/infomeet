package org.infomeet.client;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.curator.x.discovery.ServiceInstance;
import org.infomeet.client.zookeeper.ClientCacheServiceDicovery;
import org.infomeet.client.zookeeper.ClientServiceDicovery;
import org.infomeet.data.Greeting;
import org.infomeet.zookeeeper.data.RestServiceDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GreetingRestClient {

	// @Inject
	// ClientServiceDicovery discovery;

	@Inject
	ClientCacheServiceDicovery discovery;

	private RestTemplate rest = new RestTemplate();

	@PostConstruct
	public void init() throws Exception {

		while (true) {
			for (ServiceInstance<RestServiceDetails> instance : discovery
					.getServices()) {
				String url = instance.buildUriSpec();
				System.out.println("Discovered instance : " + url);
				Greeting result = rest.getForObject(url, Greeting.class);
				System.out.println(result.getContent());
			}
			Thread.sleep(5000);
		}

	}

}
