package CovidLoveit.Utils.Configuration;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesUtil implements EnvironmentAware {

    static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        PropertiesUtil.environment = environment;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static String getSecret() {
        return environment.getProperty("auth0_secret_key");
    }
}
