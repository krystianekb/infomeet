/**
 * 
 */
package org.infomeet.lock.zookeeper;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Component;

/**
 * @author krystian
 *
 */
@Component
public class CuratorLockService {

	@Inject
	private CuratorFramework client;

	private static final String lockPath = "/org/infomeet/lockpath";

	public void doWork(Task item) throws Exception {

		InterProcessLock lock = new InterProcessMutex(client, lockPath);		
		try {
			lock.acquire(10, TimeUnit.SECONDS);
			item.doWork();			
		} finally {
			lock.release();
		}
	}

}
