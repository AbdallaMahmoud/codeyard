/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author abdalla
 */
@Configuration
public class StaticResourcsConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // map all webapp static resources 
        registry.addResourceHandler("/webapp/**")
                .addResourceLocations("classpath:/webapp/");
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/webapp/template.html")
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver() {
//                    @Override
//                    protected Resource getResource(String resourcePath,
//                            Resource location) throws IOException {
//                        return location.exists() && location.isReadable() ? location
//                                : null;
//                    }
//                });
    }
}
