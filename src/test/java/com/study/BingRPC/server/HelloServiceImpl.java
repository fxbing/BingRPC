package com.study.BingRPC.server;

import com.study.BingRPC.api.HelloService;

/**
* @ClassName HelloServiceImpl
* @Description 服务接口的实现类
* @author fxbing
* @date 2018年9月29日
*
*/
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

	public String hello(String name) {
		// TODO Auto-generated method stub
		return "Hello! " + name;
	}

}
