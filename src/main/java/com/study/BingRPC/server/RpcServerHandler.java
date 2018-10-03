package com.study.BingRPC.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.study.BingRPC.bean.RpcRequest;
import com.study.BingRPC.bean.RpcResponse;
import com.study.BingRPC.Util.StringUtil;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
* @ClassName RpcServerHandler
* @Description RPC服务端处理器（用于处理RPC请求）
* @author fxbing
* @date 2018年9月30日
*
*/
    
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest>{

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);
	
	private final Map<String, Object> handlerMap;
	
	public RpcServerHandler(Map<String, Object> handlerMap) {
		this.handlerMap = handlerMap;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
		// 创建并初始化RPC响应对象
		RpcResponse response = new RpcResponse();
		response.setRequestId(request.getRequestId());
		try {
			Object result = handle(request);
			response.setResult(result);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("处理调用结果失败: {} ", e);
		}
		//写入RPC响应对象并自动关闭连接
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	private Object handle(RpcRequest request) throws Exception {
		//获取服务对象
		String serviceName = request.getInterfaceName();
		String serviceVersion = request.getServiceVersion();
		if (StringUtil.isNotEmpty(serviceVersion)) {
            serviceName += "-" + serviceVersion;
        }
		Object serviceBean = handlerMap.get(serviceName);
		//获取反射调用所需的参数
		Class<?> serviceClass = serviceBean.getClass();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] parameters = request.getParameters();
		//执行反射调用
//		Method method = serviceClass.getMethod(methodName, parameterTypes);
//		method.setAccessible(true);
//		return method.invoke(serviceBean, parameters);
		//使用CGLib执行反射调用
		FastClass serviceFastClass = FastClass.create(serviceClass);
		FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
		return serviceFastMethod.invoke(serviceBean, parameters);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.error("服务异常 :", cause);
		ctx.close();
	}
}
