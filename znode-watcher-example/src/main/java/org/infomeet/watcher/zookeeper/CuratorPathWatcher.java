/**
 * 
 */
package org.infomeet.watcher.zookeeper;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author krystian
 *
 */
@Component
public class CuratorPathWatcher {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private CuratorFramework client;

	@Inject
	TreeCache cache;

	@PostConstruct
	public void init() {
		cache.getListenable().addListener(new TreeCacheListener() {

			public void childEvent(CuratorFramework client, TreeCacheEvent event)
					throws Exception {
				ChildData data = event.getData();
				log.info("Change ({}) : {} = {}", 
					event.getType(),
					data != null ? data.getPath() : null, 
					data != null && data.getData() != null ? new String(data.getData()) : null);
			}
		});
	}

	@Bean(initMethod = "start", destroyMethod = "close")
	public TreeCache pathChildrenCache() {
		return TreeCache.newBuilder(client, "/").build();
	}

}
