package com.study.BingRPC.registry.Zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.study.BingRPC.registry.ServiceRegistry;

/**
* @ClassName ZookeeperServiceRegistry
* @Description 通过zookeeper实现服务注册功能
* @author fxbing
* @date 2018年10月2日
*
*/
    
public class ZookeeperServiceRegistry implements ServiceRegistry{

	private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceRegistry.class);
	
	private final ZkClient zkClient;

	public ZookeeperServiceRegistry(String zkAddress) {
		//创建zookeeper客户端
		zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
		LOGGER.debug("connect zookeeper {}", zkAddress);
	}
	
	public void register(String serviceName, String serviceAddress) {
		// TODO Auto-generated method stub
		//创建registry节点（持久）
		String registryPath = Constant.ZK_REGISTRY_PATH;
		if (!zkClient.exists(registryPath)) {
			zkClient.createPersistent(registryPath);
			LOGGER.debug("create registry node: {}", registryPath);
		}
		//创建service节点（持久）
		String servicePath = registryPath + "/" + serviceName;
		if (!zkClient.exists(servicePath)) {
			zkClient.createPersistent(servicePath);
			LOGGER.debug("create service node: {}", servicePath);
		}
		// 创建address节点（临时）
		String addressPath = servicePath + "/address-";
		String addressNode = zkClient.createEphemeralSequential(addressPath, serviceAddress);
		LOGGER.debug("create address node: {}", addressNode);
	}

}
