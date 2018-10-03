package com.study.BingRPC.registry.Zookeeper;

/**
* @ClassName Constant
* @Description 存储zookeeper使用的常量
* @author fxbing
* @date 2018年9月29日
*
*/
    
public interface Constant {

	int ZK_SESSION_TIMEOUT = 5000;
	int ZK_CONNECTION_TIMEOUT = 5000;
	
	String ZK_REGISTRY_PATH = "/registry";
}
