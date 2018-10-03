package com.study.BingRPC.server;

import com.study.BingRPC.api.HelloService;

/**
* @ClassName HelloServiceImpl2
* @Description 服务接口的实现类的第二种版本
* @author fxbing
* @date 2018年10月3日
*
*/
    
@RpcService(value = HelloService.class, version = "sample.hello2")
public class HelloServiceImpl2 implements HelloService {

	public String hello(String name) {
		// TODO Auto-generated method stub
		return "你好！ " + name;
	}

}
