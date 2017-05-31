/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.model;

/**
 *
 * @author abdalla
 */
public class LangInfo {

    private String id;
    private String name;
    private String memory;
    private String timeout;
    private String src;
    private int instances;
    private boolean compiled;
    private String dockerImage;
    private String templateFile;

    public LangInfo() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the memory
     */
    public String getMemory() {
        return memory;
    }

    /**
     * @param memory the memory to set
     */
    public void setMemory(String memory) {
        this.memory = memory;
    }

    /**
     * @return the timeout
     */
    public String getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the instances
     */
    public int getInstances() {
        return instances;
    }

    /**
     * @param instances the instances to set
     */
    public void setInstances(int instances) {
        this.instances = instances;
    }

    /**
     * @return the src
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src the src to set
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the compiled
     */
    public boolean isCompiled() {
        return compiled;
    }

    /**
     * @param compiled the compiled to set
     */
    public void setCompiled(boolean compiled) {
        this.compiled = compiled;
    }

    /**
     * @return the dockerImage
     */
    public String getDockerImage() {
        return dockerImage;
    }

    /**
     * @param dockerImage the dockerImage to set
     */
    public void setDockerImage(String dockerImage) {
        this.dockerImage = dockerImage;
    }

    /**
     * @return the templateFile
     */
    public String getTemplateFile() {
        return templateFile;
    }

    /**
     * @param templateFile the templateFile to set
     */
    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

}
