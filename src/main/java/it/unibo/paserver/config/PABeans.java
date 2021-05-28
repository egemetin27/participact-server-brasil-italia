package it.unibo.paserver.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.core.token.SecureRandomFactoryBean;

@Configuration
@ImportResource("classpath:/spring/spring-extra-config.xml")
public class PABeans {
	
	

	@Bean
	public FactoryBean<SecureRandom> secureRandom() {
		return new SecureRandomFactoryBean();
	}
}
