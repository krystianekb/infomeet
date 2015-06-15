package org.infomeet.client.zookeeper;

import java.util.Collection;

public interface ServiceURLProvider {

	Collection<String> getAllInstancesURLs() throws Exception;
	
	String getInstanceURL() throws Exception;
	
}
