package br.gov.dataprev.insssat.rppsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.gov.dataprev.insssat.rppsapi.config.AppConfig;

@SpringBootApplication
@EnableConfigurationProperties({AppConfig.class})
public class SpringBootRPPSApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRPPSApiApplication.class, args);
	}

}
