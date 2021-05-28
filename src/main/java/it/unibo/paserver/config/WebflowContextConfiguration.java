package it.unibo.paserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

@Configuration
@ImportResource("classpath:/spring/webflow-config.xml")
public class WebflowContextConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(WebflowContextConfiguration.class);

	@Autowired
	private FlowExecutor flowExecutor;

	@Autowired
	private FlowDefinitionRegistry flowRegistry;

	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter flowHandlerAdapter = new FlowHandlerAdapter();
		flowHandlerAdapter.setFlowExecutor(flowExecutor);
		logger.info("Set up Webflow handler adapter");
		return flowHandlerAdapter;
	}

	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping flowHandlerMapping = new FlowHandlerMapping();
		flowHandlerMapping.setFlowRegistry(flowRegistry);
		logger.info("Set up Webflow handler mapping");
		return flowHandlerMapping;
	}

	@Bean(name = "securityFlowExecutionListener")
	public SecurityFlowExecutionListener getSecurityFlowExecutionListener() {
		return new SecurityFlowExecutionListener();
	}
}
