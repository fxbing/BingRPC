package com.study.BingRPC.codec;

import java.util.List;

import com.study.BingRPC.Util.SerializationUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
* @ClassName RpcDecoder
* @Description RPC解码
* @author fxbing
* @date 2018年9月30日
*
*/
    
public class RpcDecoder extends ByteToMessageDecoder {

	private Class<?> genericClass;
	
	public RpcDecoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		if (in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}
		byte[] data = new byte[dataLength];
		in.readBytes(data);
		
		out.add(SerializationUtil.deserialize(data, genericClass));
	}

	
}
