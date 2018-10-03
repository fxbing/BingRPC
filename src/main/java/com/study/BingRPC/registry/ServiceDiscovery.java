package com.study.BingRPC.registry;

/**
* @ClassName ServiceDiscovery
* @Description 服务发现接口
* @author fxbing
* @date 2018年10月2日
*
*/
    
public interface ServiceDiscovery {

	/**
	* @Title discover
	* @Description 根据服务名称查找服务地址
	* @param serviceName 服务名称
	* @return 服务地址
	* 
	*/
	    
	String discover(String serviceName);
}
