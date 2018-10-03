package com.study.BingRPC.registry;

/**
* @ClassName ServiceRegistry
* @Description 服务注册接口
* @author fxbing
* @date 2018年10月2日
*
*/
    
public interface ServiceRegistry {
	/**
	* @Title register
	* @Description 注册服务名称与服务地址
	* @param serviceName    服务名称
	* @param serviceAddress 服务地址
	* 
	*/
	    
	void register(String serviceName, String serviceAddress);
}
