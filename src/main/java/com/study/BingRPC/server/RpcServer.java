package com.study.BingRPC.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.study.BingRPC.Util.StringUtil;
import com.study.BingRPC.bean.RpcRequest;
import com.study.BingRPC.bean.RpcResponse;
import com.study.BingRPC.codec.RpcDecoder;
import com.study.BingRPC.codec.RpcEncoder;
import com.study.BingRPC.registry.ServiceRegistry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
* @ClassName RpcServer
* @Description RPC服务器
* @author fxbing
* @date 2018年9月30日
*
*/
    
public class RpcServer implements ApplicationContextAware, InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);
	
	private String serviceAddress;
	private ServiceRegistry serviceRegistry;
	
	private Map<String, Object> handlerMap = new HashMap<String, Object>();//存放服务名与服务对象之间的映射关系
	
	public RpcServer(String serverAddress) {
		this.serviceAddress = serverAddress;
	}
	
	
	public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
		this.serviceAddress = serverAddress;
		this.serviceRegistry = serviceRegistry;
	}


	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					// TODO Auto-generated method stub
					channel.pipeline()
					.addLast(new RpcDecoder(RpcRequest.class))//将RPC请求进行解码（为了处理请求）
					.addLast(new RpcEncoder(RpcResponse.class))//将RPC请求进行编码（为了返回响应）
					.addLast(new RpcServerHandler(handlerMap));//处理RPC请求
					
				}
			}).option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true);
			// 获取 RPC 服务器的 IP 地址与端口号
			String[] array = StringUtil.split(serviceAddress, ":");
			String host = array[0];
			int port = Integer.parseInt(array[1]);
			// 启动 RPC 服务器
			ChannelFuture future = bootstrap.bind(host, port).sync();
			LOGGER.debug("server started on port {}", port);
			// 注册 RPC 服务地址
			if (serviceRegistry != null) {
				for (String interfaceName : handlerMap.keySet()) {
					serviceRegistry.register(interfaceName, serviceAddress);
					LOGGER.debug("register service: {} => {}", interfaceName, serviceAddress);
				}
			}
			LOGGER.debug("server started on port {}", port);
			//关闭RPC服务器
			future.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// TODO Auto-generated method stub
		//获取所有带有RpcService注解的SpringBean
		Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);

		if (MapUtils.isNotEmpty(serviceBeanMap)) {
			for (Object serviceBean : serviceBeanMap.values()) {
				RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName();
                String serviceVersion = rpcService.version();
                if (StringUtil.isNotEmpty(serviceVersion)) {
                    serviceName += "-" + serviceVersion;
                }
				handlerMap.put(serviceName, serviceBean);
			}
		}
	}

}
