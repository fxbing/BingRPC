package com.study.BingRPC.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.study.BingRPC.api.HelloService;

/**
* @ClassName HelloClient
* @Description 服务测试类，加载spring配置文件来调用服务
* @author fxbing
* @date 2018年10月3日
*
*/
    
public class HelloClient {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ApplicationContext context = new ClassPathXmlApplicationContext("client-spring.xml");
		RpcProxy rpcProxy = context.getBean(RpcProxy.class);
		
		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello("World");
		System.out.println(result);
		
		 HelloService helloService2 = rpcProxy.create(HelloService.class, "sample.hello2");
	     String result2 = helloService2.hello("世界");
	     System.out.println(result2);
	}

}
