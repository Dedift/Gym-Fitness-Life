package by.tms.gymprogect.web.initializer;

import by.tms.gymprogect.database.config.DbConfig;
import by.tms.gymprogect.web.configuration.SecurityConfig;
import by.tms.gymprogect.web.configuration.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public static final String DISPATCHER_SERVLET_URL_PATTERN = "/";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DbConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{DISPATCHER_SERVLET_URL_PATTERN};
    }
}
