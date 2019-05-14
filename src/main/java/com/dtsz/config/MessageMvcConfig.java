package com.dtsz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/** 
 * @ClassName: MessageMvcConfig 
 * @Description: TODO
 * @see: 
 * @author: Gsy
 * @date: 2019年3月27日 上午11:13:44 
 * @version :1.0 
 */
@SuppressWarnings("deprecation")
@Configuration
public class MessageMvcConfig extends WebMvcConfigurerAdapter{
	
	@Value("${crossorigin.url}")
	String origins = "";
	 
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index.html");
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		 registry.addMapping("/**")
		 		 .allowedOrigins("*");
		         //.allowedOrigins(origins.split(","));
    }
}
