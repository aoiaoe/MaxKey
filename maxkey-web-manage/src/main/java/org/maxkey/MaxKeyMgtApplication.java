package org.maxkey;

import java.util.Date;

import javax.servlet.ServletException;

import org.maxkey.web.InitApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:spring/maxkey-mgt.xml"})
@ComponentScan(basePackages = {
		"org.maxkey.MaxKeyMgtConfig"
	}
)
public class MaxKeyMgtApplication extends SpringBootServletInitializer {
	private static final Logger _logger = LoggerFactory.getLogger(MaxKeyMgtApplication.class);

	@Bean
	MaxKeyMgtConfig MaxKeyMgtConfig() {
		return new MaxKeyMgtConfig();
	}
	
	public static void main(String[] args) {
		System.out.println("MaxKeyMgtApplication");

		ConfigurableApplicationContext  applicationContext =SpringApplication.run(MaxKeyMgtApplication.class, args);
		InitApplicationContext initWebContext=new InitApplicationContext(applicationContext);
		
		
		try {
			initWebContext.init(null);
		} catch (ServletException e) {
			e.printStackTrace();
			_logger.error("",e);
		}
		_logger.info("MaxKeyMgt at "+new Date(applicationContext.getStartupDate()));
		_logger.info("MaxKeyMgt Server Port "+applicationContext.getBean(MaxKeyMgtConfig.class).getPort());
		_logger.info("MaxKeyMgt started.");
		
		
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(MaxKeyMgtApplication.class);
	}

}
