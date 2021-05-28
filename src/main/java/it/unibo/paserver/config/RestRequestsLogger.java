package it.unibo.paserver.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RestRequestsLogger extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RestRequestsLogger.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if (uri.indexOf("/rest/") < 0) {
			return super.preHandle(request, response, handler);
		}
		String method = request.getMethod();
		String queryString = request.getQueryString();
		String remoteAddr = request.getRemoteAddr();
		String remoteUser = request.getRemoteUser();
		StringBuilder sb = new StringBuilder();
		if (remoteUser != null) {
			sb.append(remoteUser).append(" ");
		}
		sb.append(String.format("%s %s %s", remoteAddr, method, uri));
		if (queryString != null) {
			sb.append("?");
			sb.append(queryString);
		}
		logger.debug("Request {}", sb.toString());
		return super.preHandle(request, response, handler);
	}
}
