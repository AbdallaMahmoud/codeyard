/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.apis;

import com.abdallamahmoud.codeyard.coderun.CodeRunCallback;
import com.abdallamahmoud.codeyard.coderun.CodeRunTask;
import com.abdallamahmoud.codeyard.coderun.CodeRunner;
import com.abdallamahmoud.codeyard.model.LangInfo;
import com.abdallamahmoud.codeyard.model.CodeResult;
import com.abdallamahmoud.codeyard.model.CodeRunRequest;
import com.abdallamahmoud.codeyard.tasksmgr.TasksPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author abdalla
 */
@Controller
public class InteractiveCodeApis {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private TasksPool tasksPool;

    @MessageMapping("/run")
    @SendTo("/queue/reply")
    public void runCode(@Payload CodeRunRequest codeRunRequest, final SimpMessageHeaderAccessor headerAccessor) throws Exception {
        final String sessionId = headerAccessor.getSessionId();
//        System.out.println("username " + principal.getName());
//        template.convertAndSendToUser(sessionId, "/queue/reply", "success yam3lm");
//        System.out.println("sessionId " + sessionId);
//        System.out.println(codeRunRequest.getLang());
//        Thread.sleep(1000);
        System.out.println("INVOKED");

        CodeRunTask codeRunTask = new CodeRunTask(codeRunRequest, new CodeRunCallback() {
            @Override
            public void onResule(CodeResult codeResult) {
                reply(codeResult);
            }

            @Override
            public void onError(Exception ex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            private void reply(CodeResult codeResult) {
                SimpMessageHeaderAccessor headerAccessor2 = SimpMessageHeaderAccessor
                        .create(SimpMessageType.MESSAGE);
                headerAccessor2.setSessionId(sessionId);
                headerAccessor2.setLeaveMutable(true);

                messagingTemplate.convertAndSendToUser(sessionId, "/queue/reply", codeResult,
                        headerAccessor.getMessageHeaders());
                
            }
        });
        
        tasksPool.requestStart(codeRunTask);

    }
}
