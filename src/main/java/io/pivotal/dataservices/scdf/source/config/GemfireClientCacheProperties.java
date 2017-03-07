package io.pivotal.dataservices.scdf.source.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("gemfire.client")
public class GemfireClientCacheProperties {


	private Boolean authenticationRequired = false;
	private String userName;
	private String password;

	public Boolean getAuthenticationRequired() {
		return authenticationRequired;
	}
	public void setAuthenticationRequired(Boolean authenticationRequired) {
		this.authenticationRequired = authenticationRequired;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
