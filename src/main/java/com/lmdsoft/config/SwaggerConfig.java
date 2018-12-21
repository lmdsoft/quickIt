package com.lmdsoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 类SwaggerConfig的功能描述:
 * Swagger配置
 * @Auther lmdsoft
 * @Date 2018-08-15 11:52:12
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.lmdsoft.modules.activiti.rest.editor.controller.ModelController")) // 对该包下的api进行监控
                .paths(PathSelectors.any()) // 对该包下的所有路径进行监控
                .build();
    }
}
