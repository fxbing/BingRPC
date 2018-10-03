package com.study.BingRPC.codec;

import com.study.BingRPC.Util.SerializationUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
* @ClassName RpcEncoder
* @Description RPC编码
* @author fxbing
* @date 2018年9月30日
*
*/
    
public class RpcEncoder extends MessageToByteEncoder {
	
	private Class<?> genericClass;

	public RpcEncoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		if (genericClass.isInstance(in)) {
			byte[] data = SerializationUtil.serialize(in);
			out.writeInt(data.length);
			out.writeBytes(data);
		}
	}

}
