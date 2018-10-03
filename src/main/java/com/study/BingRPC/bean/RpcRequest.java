package com.study.BingRPC.bean;

/**
* @ClassName RpcRequest
* @Description RPC请求
* @author fxbing
* @date 2018年9月30日
*
*/
    
public class RpcRequest {

	private String requestId;
	private String interfaceName;
	private String serviceVersion;
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] parameters;
	
	
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public Object[] getParameters() {
		return parameters;
	}
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	
	
}
