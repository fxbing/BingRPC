package com.study.BingRPC.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.study.BingRPC.bean.RpcRequest;
import com.study.BingRPC.bean.RpcResponse;
import com.study.BingRPC.codec.RpcDecoder;
import com.study.BingRPC.codec.RpcEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
* @ClassName RpcClient
* @Description RPC客户端（用于发送RPC请求）
* @author fxbing
* @date 2018年10月2日
*
*/
    
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse>{

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcClient.class);
	
	private final String host;
	private final int port;
	
	private RpcResponse response;

	public RpcClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
		// TODO Auto-generated method stub
		this.response = response;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.error("api caught exception {}", cause);
		ctx.close();
	}
	
	public RpcResponse send(RpcRequest request) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			//创建并初始化Netty客户端Bootstrap对象
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline pipeline = channel.pipeline();
					pipeline.addLast(new RpcEncoder(RpcRequest.class));//编码RPC请求
					pipeline.addLast(new RpcDecoder(RpcResponse.class));//解码RPC响应
					pipeline.addLast(RpcClient.this);//处理RPC响应
				}
			});
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			
			//连接RPC服务器
			ChannelFuture future = bootstrap.connect(host, port).sync();
			
			//写入RPC请求数据并关闭连接
			Channel channel = future.channel();
			channel.writeAndFlush(request).sync();
			channel.closeFuture().sync();
			
			//返回RPC响应对象
			return response;
		} finally {
			// TODO: handle finally clause
			group.shutdownGracefully();
		}
	}
}
