/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.apis;

import com.abdallamahmoud.codeyard.coderun.CodeRunner;
import com.abdallamahmoud.codeyard.model.LangInfo;
import com.abdallamahmoud.codeyard.model.CodeResult;
import com.abdallamahmoud.codeyard.model.CodeRunRequest;
import com.abdallamahmoud.codeyard.model.SupportedLang;
import com.abdallamahmoud.codeyard.model.SupportedLangs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author abdalla
 */
@RestController
@RequestMapping("/apis")
public class CodeApis {

//    @RequestMapping(value = "/run", method = RequestMethod.POST)
//    public CodeResult run(@RequestBody CodeRunRequest codeRunRequest) throws Exception {
//        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        File yamlFile = new File("langs/" + codeRunRequest.getLang() + "/" + codeRunRequest.getLang() + ".yaml");
//        LangInfo langInfo = mapper.readValue(yamlFile, LangInfo.class);
//        CodeRunner codeRunner = new CodeRunner(langInfo, codeRunRequest.getCode(), codeRunRequest.getInput());
//        return codeRunner.runCode();
//    }

    @RequestMapping(value = "/langs", method = RequestMethod.GET)
    public SupportedLangs getSupportedLangs() throws Exception {
        SupportedLangs supportedLangs = new SupportedLangs();
        // collect langs
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File langsDir = new File("langs");
        File[] subDirs = langsDir.listFiles();
        for (File subDir : subDirs) {
            if (subDir.isDirectory()) {

                String langId = subDir.getName();

                File yamlFile = new File(subDir, langId + ".yaml");
                LangInfo langInfo = mapper.readValue(yamlFile, LangInfo.class);

                String langName = langInfo.getName();
                String langTemplate = null;

                try {
                    langTemplate = FileUtils.readFileToString(new File(subDir, langInfo.getTemplateFile()), "UTF-8");
                } catch (Exception e) {
                    throw new RuntimeException("Error reading lang template for: " + langId);
                }

                supportedLangs.getSupportedLangs().add(new SupportedLang(langId, langName, langTemplate));
            }
        }

        return supportedLangs;

    }

}
