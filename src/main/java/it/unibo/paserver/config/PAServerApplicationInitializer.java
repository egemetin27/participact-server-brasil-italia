package it.unibo.paserver.config;

import it.unibo.tper.configuration.TPerConfiguration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.BeanIds;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class PAServerApplicationInitializer implements WebApplicationInitializer {

	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
	private static final Logger logger = LoggerFactory.getLogger(PAServerApplicationInitializer.class);

	private static final Class<?>[] configurationClasses = new Class<?>[] { TestDataContextConfiguration.class,
			InfrastructureContextConfiguration.class, WebMvcContextConfiguration.class, PABeans.class,
			TPerConfiguration.class};

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerDispatcherServlet(servletContext);
		registerListener(servletContext);
		registerSpringSecurityFilterChain(servletContext);
	}

	private void registerDispatcherServlet(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext dispatcherContext = createContext(WebMvcContextConfiguration.class, WebflowContextConfiguration.class);
		ServletRegistration.Dynamic dispatcher;
		dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(dispatcherContext));
		if (dispatcher != null) {
			dispatcher.setAsyncSupported(true);
			dispatcher.setLoadOnStartup(1);
			dispatcher.addMapping("/");
		} else {
			logger.warn("Double dispatcher servlet initialization");
		}
	}

	private void registerListener(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = createContext(configurationClasses);
		servletContext.addListener(new ContextLoaderListener(rootContext));
		/* needed by Spring Security */
		servletContext.addListener(new RequestContextListener());
	}

	/**
	 * Factory method to create {@link AnnotationConfigWebApplicationContext}
	 * instances.
	 * 
	 * @param annotatedClasses
	 * @return
	 */
	private AnnotationConfigWebApplicationContext createContext(final Class<?>... annotatedClasses) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(annotatedClasses);
		return context;
	}

	private void registerSpringSecurityFilterChain(ServletContext servletContext) {
		FilterRegistration.Dynamic springSecurityFilterChain = servletContext
				.addFilter(BeanIds.SPRING_SECURITY_FILTER_CHAIN, new DelegatingFilterProxy());
		if (springSecurityFilterChain != null) {
			springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");
		} else {
			logger.warn("Double spring security chain registration");
		}
	}
}
