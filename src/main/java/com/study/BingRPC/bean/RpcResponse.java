package com.study.BingRPC.bean;

/**
* @ClassName RpcResponse
* @Description RPC响应
* @author fxbing
* @date 2018年9月30日
*
*/
    
public class RpcResponse {

	private String requestId;
	private Exception exception;
	private Object result;
	
	
	public boolean hasException() {
        return exception != null;
    }
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
