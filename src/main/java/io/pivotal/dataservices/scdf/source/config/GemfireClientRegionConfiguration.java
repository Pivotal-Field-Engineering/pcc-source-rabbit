/*
 * Copyright 2015-2016 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.dataservices.scdf.source.config;

import java.util.List;

import org.apache.geode.cache.DataPolicy;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.client.Interest;
import org.springframework.util.CollectionUtils;

/**
 * Client region configuration common to Gemfire spring-cloud-stream apps. This
 * configures the 'regionName', 'spring.application.name' by default and injects the
 * pool. Also, any beans of type {@link Interest} will be registered to the client
 * region to control which keys will be automatically synched to the client. At least
 * one of these is required for Gemfire Source apps.
 *
 * @author David Turanski
 */
@Configuration
@Import(GemfireClientCacheConfiguration.class)
@EnableConfigurationProperties(GemfireRegionProperties.class)
public class GemfireClientRegionConfiguration {



	@Autowired
	private GemfireRegionProperties config;

	@Autowired
	private ClientCacheFactoryBean clientCache;


	@SuppressWarnings("rawtypes")
	@Autowired(required = false)
	private List<Interest> keyInterests;

	@Bean(name = "clientRegion")
	@SuppressWarnings({"unchecked", "rawtypes"})
	public ClientRegionFactoryBean clientRegionFactoryBean() {
		ClientRegionFactoryBean clientRegionFactoryBean = new ClientRegionFactoryBean();
		clientRegionFactoryBean.setRegionName(this.config.getRegionName());
		clientRegionFactoryBean.setDataPolicy(DataPolicy.EMPTY);

		if (!CollectionUtils.isEmpty(this.keyInterests)) {
			clientRegionFactoryBean.setInterests(this.keyInterests.toArray(new Interest[this.keyInterests.size()]));
		}

		try {
			clientRegionFactoryBean.setCache(clientCache.getObject());
		} catch (Exception e) {
			throw new BeanCreationException(e.getMessage(), e);
		}
		return clientRegionFactoryBean;
	}

//	@Autowired
//	private PoolFactoryBean gemfirePool;

//	private static final String SECURITY_CLIENT = "security-client-auth-init";
//  private static final String SECURITY_USERNAME = "security-username";
//  private static final String SECURITY_PASSWORD = "security-password";


//	@Bean(name = "clientRegion")
//	@SuppressWarnings({"unchecked", "rawtypes"})
//	public ClientRegionFactoryBean clientRegionFactoryBean() {
//		ClientRegionFactoryBean clientRegionFactoryBean = new ClientRegionFactoryBean();
//		clientRegionFactoryBean.setRegionName(this.config.getRegionName());
//		clientRegionFactoryBean.setDataPolicy(DataPolicy.EMPTY);
//
//		if (!CollectionUtils.isEmpty(this.keyInterests)) {
//			clientRegionFactoryBean.setInterests(this.keyInterests.toArray(new Interest[this.keyInterests.size()]));
//		}
//
//		try {
//			clientRegionFactoryBean.setCache(getGemfireClientCache());
//		} catch (Exception e) {
//			throw new BeanCreationException(e.getMessage(), e);
//		}
//		return clientRegionFactoryBean;
//	}
//
//	private ClientCache getGemfireClientCache() throws Exception {
//
//		ClientCacheFactory factory = new ClientCacheFactory();
//		factory.set(SECURITY_CLIENT, "io.pivotal.dataservices.scdf.source.authorize.UserAuthInitialize.create");
//		factory.set(SECURITY_USERNAME, "operator");
//		factory.set(SECURITY_PASSWORD, "");
//		factory.setPdxReadSerialized(true);
//		factory.setPdxSerializer(new ReflectionBasedAutoSerializer(".*"));
//
//		for (InetSocketAddress locatorAddress : gemfirePool.getPool().getLocators()) {
//			factory.addPoolLocator(locatorAddress.getHostName(), locatorAddress.getPort());
//		}
//
//		ClientCache cache = factory.create();
//		return cache;
//   }

}
