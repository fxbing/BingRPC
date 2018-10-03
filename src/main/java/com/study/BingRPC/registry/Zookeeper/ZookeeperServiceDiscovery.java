package com.study.BingRPC.registry.Zookeeper;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.study.BingRPC.registry.ServiceDiscovery;

/**
* @ClassName ZookeeperServiceDiscovery
* @Description 通过zookeeper实现服务发现功能
* @author fxbing
* @date 2018年10月2日
*
*/
    
public class ZookeeperServiceDiscovery implements ServiceDiscovery {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceDiscovery.class);
	
	private String zkAddress;
	
	public ZookeeperServiceDiscovery(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	public String discover(String serviceName) {
		// TODO Auto-generated method stub
		//创建zookeeper客户端
		ZkClient zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
		LOGGER.debug("connect zookeeper");
		try {
			//获取service节点
			String servicePath = Constant.ZK_REGISTRY_PATH + "/" + serviceName;
			if (!zkClient.exists(servicePath)) {
				throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
			}
			List<String> addressList = zkClient.getChildren(servicePath);
			if (CollectionUtils.isEmpty(addressList)) {
				throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
			}
			//获取address节点
			String address;
			int size = addressList.size();
			if (size == 1) {
				//若只有一个地址，则获取该地址
				address = addressList.get(0);
				LOGGER.debug("get only address node: {}", address);
			} else {
				//若存在多个地址，则随机获取一个地址
				address = addressList.get(ThreadLocalRandom.current().nextInt(size));
				LOGGER.debug("get random address node: {}", address);
			}
			//获取address节点的值
			String addressPath = servicePath + "/" + address;
			return zkClient.readData(addressPath);
		} finally {
			// TODO: handle finally clause
			zkClient.close();
		}
	}

}
