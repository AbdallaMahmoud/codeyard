/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.model;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author abdalla
 */
public class SupportedLangs {

    private Set<SupportedLang> supportedLangs = new TreeSet<>();

    public SupportedLangs() {
    }

    /**
     * @return the supportedLangs
     */
    public Set<SupportedLang> getSupportedLangs() {
        return supportedLangs;
    }

    /**
     * @param supportedLangs the supportedLangs to set
     */
    public void setSupportedLangs(Set<SupportedLang> supportedLangs) {
        this.supportedLangs = supportedLangs;
    }

}
