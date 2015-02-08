//Powered By if, Since 2014 - 2020

package com.template.web.sys.model;

import com.template.common.base.BaseEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author 
 */

@SuppressWarnings({ "unused"})
@Table(name="sys_log")
public class SysLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	
    private String exception; //exception <异常信息>

	
    private String method; //method <操作方式>

	
    private String params; //params <操作提交的数据>

	
    private String remoteAddr; //remote_addr <操作IP地址>

	
    private String requestUri; //request_uri <请求URI>

	
    private String type; //type <日志类型（1：接入日志；2：错误日志）>

	
    private String userAgent; //user_agent <用户代理>



	public String getException() {
		return this.getString("exception");
    }
   
    public void setException(String exception) {
		this.set("exception", exception);
    }

	public String getMethod() {
		return this.getString("method");
    }
   
    public void setMethod(String method) {
		this.set("method", method);
    }

	public String getParams() {
		return this.getString("params");
    }
   
    public void setParams(String params) {
		this.set("params", params);
    }

	public String getRemoteAddr() {
		return this.getString("remoteAddr");
    }
   
    public void setRemoteAddr(String remoteAddr) {
		this.set("remoteAddr", remoteAddr);
    }

	public String getRequestUri() {
		return this.getString("requestUri");
    }
   
    public void setRequestUri(String requestUri) {
		this.set("requestUri", requestUri);
    }

	public String getType() {
		return this.getString("type");
    }
   
    public void setType(String type) {
		this.set("type", type);
    }

	public String getUserAgent() {
		return this.getString("userAgent");
    }
   
    public void setUserAgent(String userAgent) {
		this.set("userAgent", userAgent);
    }


}
