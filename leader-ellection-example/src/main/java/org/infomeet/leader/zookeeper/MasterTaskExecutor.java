package org.infomeet.leader.zookeeper;

import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.infomeet.leader.task.ActualTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MasterTaskExecutor {
		
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CuratorFramework client;
	
	@Inject
	private ActualTask task;
	
	@Inject
	private LeaderSelector leader;
			
	public void execute() {
		// to put myself into the queue to require the leadership
		leader.autoRequeue();
		
	}
	
	@Bean(initMethod = "start", destroyMethod = "close")
	public LeaderSelector leaderSelector() {
		return new LeaderSelector(client, "/org/infomeet/leader/ellection", new LeaderSelectorListener() {

			public void stateChanged(CuratorFramework client,
					ConnectionState newState) {
				log.info("State has changed - current state = {}", newState);	
			}

			public void takeLeadership(CuratorFramework client)
					throws Exception {
				log.info("Taken over leadership role");
				task.doWork();				
			}
			
		});
	}

}
