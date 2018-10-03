# BingRPC
轻量级分布式RPC框架

## 框架介绍
---
此框架采用Zookeeper+Netty+Protostuff的组合
<ol>
<li>Zookeeper：分布式服务注册框架，负责服务注册与服务发现。</li>
<li>Netty：Java 网络编程框架，负责客户端、服务端和注册中心的通信。</li>
<li>Protostuff：序列化工具</li>
</ol>

## 使用说明
---
### 发布RPC服务 
#### 1. 定义RPC服务接口
在com.study.BingRPC.api包中定义RPC接口：  
```
public interface HelloService {
	
	String hello(String name);
}
```

#### 2. 实现RPC服务接口
```
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
	public String hello(String name) {
		// TODO Auto-generated method stub
		return "Hello! " + name;
	}
}
```  
通过@RpcService注解定义使用的接口名称和服务版本号（如果存在）  

### 配置服务端
####  1. 配置server-spring.xml
```
    <context:component-scan base-package="com.study.BingRPC.server"/>

    <context:property-placeholder location="classpath:config.properties"/>

    <!-- 配置服务注册组件 -->
    <bean id="serviceRegistry" class="com.study.BingRPC.registry.Zookeeper.ZookeeperServiceRegistry">
        <constructor-arg name="zkAddress" value="${zookeeper.address}"/>
    </bean>

    <!-- 配置 RPC 服务器 -->
    <bean id="rpcServer" class="com.study.BingRPC.server.RpcServer">
        <constructor-arg name="serverAddress" value="${service.address}"/>
        <constructor-arg name="serviceRegistry" ref="serviceRegistry"/>
    </bean>
```

####  2. 配置config.properties
<ul>
<li>zookeeper.address: zookeeper服务器地址</li>
<li>service.address：服务发布地址，客户端调用服务时使用</li>
</ul>
####  3. 运行RpcBootstrap类启动服务   
```
public class RpcBootstrap {
	private static final Logger LOGGER = LoggerFactory.getLogger(RpcBootstrap.class);
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LOGGER.debug("start server");
		new ClassPathXmlApplicationContext("server-spring.xml");
	}
}
```

### 配置客户端
####  1. 配置client-spring.xml
```
    <context:property-placeholder location="classpath:config.properties"/>
	
	  <!-- 配置服务发现组件 -->
    <bean id="serviceDiscovery" class="com.study.BingRPC.registry.Zookeeper.ZookeeperServiceDiscovery">
        <constructor-arg name="zkAddress" value="${zookeeper.address}"/>
    </bean>

    <!-- 配置 RPC 代理 -->
    <bean id="rpcProxy" class="com.study.BingRPC.client.RpcProxy">
        <constructor-arg name="serviceDiscovery" ref="serviceDiscovery"/>
    </bean>
```

####  2. 配置config.properties
<ul>
<li>zookeeper.address: zookeeper服务器地址</li>
</ul>  

####  3. 调用 RPC 服务
<ol>
<li>注入 RpcProxy 对象</li>
<li>调用 RpcProxy 对象的 create 方法来创建 RPC 代理接口</li>
<li>调用 RPC 代理接口的方法</li>
</ol>  
  
具体见[com.study.BingRPC.client.HelloClient.java](https://github.com/fxbing/BingRPC/blob/master/src/test/java/com/study/BingRPC/client/HelloClient.java)
