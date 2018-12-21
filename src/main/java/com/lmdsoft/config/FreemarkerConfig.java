package com.lmdsoft.config;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
/**
 * 类FreemarkerConfig的功能描述:
 * 生成静态模板配置
 * @Auther lmdsoft
 * @Date 2018-08-15 22:49:43
 */
@Configuration
@Component
public class FreemarkerConfig {
    @Autowired
    private freemarker.template.Configuration conf;
    @Value("${siteurl}")
    private static String siteurl;
    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        //configuration.setSharedVariable("user_topics_tag", userTopicDirective);
        System.out.println("==========================================      "+siteurl);
        conf.setSharedVariable("siteurl","");



    }

}
