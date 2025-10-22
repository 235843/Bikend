package bikend.configuration.spring;

import bikend.configuration.mail.MailConfiguration;
import bikend.configuration.database.HibernatePersistanceConfiguration;
import bikend.configuration.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
public class SpringInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                SpringWebConfig.class,
                SecurityConfig.class,
                HibernatePersistanceConfiguration.class,
                MailConfiguration.class
        };
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringWebConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

