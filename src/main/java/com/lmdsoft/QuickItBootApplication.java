package com.lmdsoft;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@ServletComponentScan
@EnableAutoConfiguration(exclude={SecurityAutoConfiguration.class})
@Import(FdfsClientConfig.class)
@ComponentScan({"com.lmdsoft.modules.activiti.org.activiti", "com.lmdsoft"})
@EnableMBeanExport (registration = RegistrationPolicy.IGNORE_EXISTING )
public class QuickItBootApplication  extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors","false");
		SpringApplication.run(QuickItBootApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		System.setProperty("es.set.netty.runtime.available.processors","false");
		return builder.sources(QuickItBootApplication.class);
	}

}
