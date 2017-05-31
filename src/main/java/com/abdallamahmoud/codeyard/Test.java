/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard;

import com.abdallamahmoud.codeyard.model.LangInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author abdalla
 */
public class Test {

    public static void main(String[] args) {
        
        // clean compile assembly:single
        
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            LangInfo langInfo = mapper.readValue(new File("langs/java7/java7.yaml"), LangInfo.class);
            System.out.println(langInfo.getDockerImage());
//            if (true) {
//                System.out.println(langInfo.isCompiled());
//                return;
//            }
//            System.out.println(langInfo.getName());
//            System.out.println(langInfo.getMemory());
//            System.out.println(langInfo.getTimeout());
//            System.out.println(langInfo.getInstances());
            
//            CodeRunner codeRunner = new CodeRunner(langInfo, FileUtils.readFileToString(new File("Solution.java"), "UTF-8"));
//            new Thread(codeRunner).start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
}
