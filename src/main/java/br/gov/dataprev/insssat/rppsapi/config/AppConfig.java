package br.gov.dataprev.insssat.rppsapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@ConfigurationProperties
@PropertySource(value="file:/etc/config/insssat/rppsapi/application.properties")
public class AppConfig {

   @Bean
   public static PropertySourcesPlaceholderConfigurer propertiesConfigurer() {
	    PropertySourcesPlaceholderConfigurer properties = 
	      new PropertySourcesPlaceholderConfigurer();
	    properties.setIgnoreResourceNotFound(false);
	    return properties;
    }
    

}
