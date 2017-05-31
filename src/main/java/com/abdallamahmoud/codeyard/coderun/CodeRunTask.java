/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.coderun;

import com.abdallamahmoud.codeyard.tasksmgr.TasksPool;
import com.abdallamahmoud.codeyard.tasksmgr.Task;
import com.abdallamahmoud.codeyard.model.LangInfo;
import com.abdallamahmoud.codeyard.model.CodeResult;
import com.abdallamahmoud.codeyard.model.CodeRunRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;

/**
 *
 * @author abdalla
 */
public class CodeRunTask extends Task {

    private CodeRunRequest codeRunRequest;
    private CodeRunCallback codeRunCallback;

    public CodeRunTask(CodeRunRequest codeRunRequest, CodeRunCallback codeRunCallback) {
        this.codeRunRequest = codeRunRequest;
        this.codeRunCallback = codeRunCallback;
    }

    @Override
    public void run() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            File yamlFile = new File("langs/" + codeRunRequest.getLang() + "/" + codeRunRequest.getLang() + ".yaml");
            LangInfo langInfo = mapper.readValue(yamlFile, LangInfo.class);
            CodeRunner codeRunner = new CodeRunner(langInfo, codeRunRequest.getCode(), codeRunRequest.getInput());
            CodeResult codeResult = codeRunner.runCode();

            codeRunCallback.onResule(codeResult);
        } catch (Exception ex) {
            codeRunCallback.onError(ex);
        } finally {
            reportDone();
        }
    }

//    public void execute() {
//        TasksPool.requestStart(this);
//    }

}
