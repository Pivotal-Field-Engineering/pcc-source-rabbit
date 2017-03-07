/*
 * Copyright (c) 2016 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License") ;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.dataservices.scdf.source.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;

/**
 * @author David Turanski
 **/
@EnableConfigurationProperties(GemfireClientCacheProperties.class)
public class GemfireClientCacheConfiguration {

	private static final String SECURITY_CLIENT = "security-client-auth-init";
    private static final String SECURITY_USERNAME = "security-username";
    private static final String SECURITY_PASSWORD = "security-password";

    @Autowired
    GemfireClientCacheProperties gemfireClientCacheProperties;


	@Bean
	public ClientCacheFactoryBean clientCache() {
		ClientCacheFactoryBean clientCacheFactoryBean = new ClientCacheFactoryBean();
		clientCacheFactoryBean.setUseBeanFactoryLocator(false);
		clientCacheFactoryBean.setPoolName("gemfirePool");
		clientCacheFactoryBean.setReadyForEvents(true);
		if (this.gemfireClientCacheProperties.getAuthenticationRequired()) {
			clientCacheFactoryBean.setProperties(setAuthenticationProperties());
		}
		return clientCacheFactoryBean;
	}

	private Properties setAuthenticationProperties() {

		Properties properties = new Properties();
		properties.setProperty(SECURITY_CLIENT,
				"io.pivotal.dataservices.scdf.source.authorize.UserAuthInitialize.create");
		properties.setProperty(SECURITY_USERNAME, this.gemfireClientCacheProperties.getUserName());
		properties.setProperty(SECURITY_PASSWORD, this.gemfireClientCacheProperties.getPassword());

		return properties;
	}


}
