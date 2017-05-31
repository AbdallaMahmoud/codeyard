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
public class SupportedLang implements Comparable<SupportedLang> {

    private String id;
    private String name;
    private String template;

    public SupportedLang() {
    }

    public SupportedLang(String id, String name, String template) {
        this.id = id;
        this.name = name;
        this.template = template;
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
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public int compareTo(SupportedLang t) {
        return getName().compareTo(t.getName());
    }

}
