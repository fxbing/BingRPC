package com.study.BingRPC.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
* @ClassName RpcService
* @Description 对服务接口实现类制定远程接口
* @author fxbing
* @date 2018年9月29日
*
*/
    
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

	Class<?> value();//服务接口类
	
	String version() default "";//服务版本号

}
