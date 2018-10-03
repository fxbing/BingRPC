package com.study.BingRPC.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
* @ClassName RpcBootstrap
* @Description 加载spring配置文件来发布服务
* @author fxbing
* @date 2018年9月29日
*
*/
    
public class RpcBootstrap {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RpcBootstrap.class);

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LOGGER.debug("start server");
		new ClassPathXmlApplicationContext("server-spring.xml");
	}

}
