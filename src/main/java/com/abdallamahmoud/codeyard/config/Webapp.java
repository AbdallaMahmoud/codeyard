/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.config;

import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author abdalla
 */
@Controller
public class Webapp {

    /**
     * A workaround for the root-to-template problem
     *
     * @return
     */
    @RequestMapping("/")
    @ResponseBody
    public ClassPathResource index() throws IOException {
        return new ClassPathResource("/webapp/home.html");
    }
}
