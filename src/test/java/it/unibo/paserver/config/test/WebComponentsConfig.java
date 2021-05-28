package it.unibo.paserver.config.test;

import java.security.SecureRandom;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.token.SecureRandomFactoryBean;

@Configuration
@ComponentScan(basePackages = { "it.unibo.paserver.web",
		"it.unibo.paserver.rest", "it.unibo.paserver.manteinance" })
public class WebComponentsConfig {

	@Bean
	public FactoryBean<SecureRandom> secureRandom() {
		return new SecureRandomFactoryBean();
	}
}
