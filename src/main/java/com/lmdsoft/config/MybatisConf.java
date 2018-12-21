package com.lmdsoft.config;

import com.lmdsoft.modules.common.page.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 类MybatisConf的功能描述:
 * 生成Mybatis配置
 * @Auther lmdsoft
 * @Date 2018-09-15 21:49:43
 */
@Configuration
public class MybatisConf {  
        @Bean
        public PageHelper pageHelper() {
           System.out.println("MyBatisConfiguration.pageHelper()");  
            PageHelper pageHelper = new PageHelper();  
            Properties p = new Properties();  
            p.setProperty("offsetAsPageNum", "true");  
            p.setProperty("rowBoundsWithCount", "true");  
            p.setProperty("reasonable", "true");  
            pageHelper.setProperties(p);  
            return pageHelper;  
        }  
}  