package org.infomeet.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperAppConfig {

	private static final String ZK_URL = "%s:%s";

	@Value("${zookeeper.server:localhost}")
	private String zkHost;

	@Value("${zookeeper.port:2181}")
	private Integer zkPort;
	
	@Bean(initMethod = "start", destroyMethod = "close")
	public CuratorFramework curator() {
		return CuratorFrameworkFactory.newClient(
				String.format(ZK_URL, zkHost, zkPort),
				new ExponentialBackoffRetry(1000, 3));
	}
}
