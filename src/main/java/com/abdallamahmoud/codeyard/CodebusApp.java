/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard;

import com.abdallamahmoud.codeyard.apis.CodeApis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author abdalla
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {CodebusApp.class})
//@EnableJpaRepositories(basePackageClasses = {CodebusApp.class})
//@EntityScan(basePackageClasses = {CodebusApp.class})
public class CodebusApp {

    public static void main(String[] args) {
        SpringApplication.run(CodebusApp.class, args);
    }
}
