package it.unibo.paserver.config;

import it.unibo.paserver.web.formatter.PADateTimeFormatter;
import it.unibo.paserver.web.formatter.PALocalDateFormatter;
import it.unibo.paserver.web.security.v2.AuthenticatorRequestInterceptor;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "it.unibo.paserver.web", "it.unibo.paserver.rest", "it.unibo.tper.web", "it.unibo.tper.opendata" })
@ImportResource("classpath:/spring/spring-security.xml")
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
	// @Autowired
	// private AuthenticatorRequestInterceptor authenticatorRequestInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	/**
	 * To resolve message codes to actual messages we need a {@link MessageSource}
	 * implementation. The default implementations use a
	 * {@link java.util.ResourceBundle} to parse the property files with the
	 * messages in it.
	 * 
	 * @return the {@link MessageSource}
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		// messageSource.setFallbackToSystemLocale(false);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("pt_BR"));
		resolver.setCookieName("paLocaleCookie");
		resolver.setCookieMaxAge(28800);
		return resolver;
	}

	@Bean
	public AuthenticatorRequestInterceptor localeAuthenticatorRequestInterceptor() {
		return new AuthenticatorRequestInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("language");
		registry.addInterceptor(interceptor);
		registry.addInterceptor(localeAuthenticatorRequestInterceptor()).addPathPatterns("/api/v2/protected/**/");
		// registry.addInterceptor(restRequestsLogger());
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setOrder(1);
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setViewClass(JstlView.class);
		return internalResourceViewResolver;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new PALocalDateFormatter());
		registry.addFormatter(new PADateTimeFormatter());
	}

	@Bean
	public CommonsMultipartResolver multipartResolver(ServletContext servletContext) {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(servletContext);
		cmr.setMaxUploadSize(-1);
		cmr.setMaxUploadSizePerFile(-1);
		cmr.setMaxInMemorySize(10485760);
		return cmr;
	}

	@Bean
	public RestRequestsLogger restRequestsLogger() {
		return new RestRequestsLogger();
	}
}
