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
public class CodeResult {

    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_COMPILE_ERROR = 1;
    public static final int STATUS_RUNTIME_ERROR = 2;
    public static final int STATUS_TIME_OUT = 3;

    private int status;
    private int compileStatus;
    private int runtimeStatus;
    private String output;
    private String error;

    public CodeResult() {
    }

    public CodeResult(int status, int compileStatus, int runtimeStatus, String output, String error) {
        this.status = status;
        this.compileStatus = compileStatus;
        this.runtimeStatus = runtimeStatus;
        this.output = output;
        this.error = error;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    public String getStatusMsg() {
        if (status == STATUS_SUCCESS) {
            return "Code ran successfully.";
        } else if (status == STATUS_COMPILE_ERROR) {
            return "Compiler error !";
        } else if (status == STATUS_RUNTIME_ERROR) {
            return "Runtime error !";
        } else if (status == STATUS_TIME_OUT) {
            return "Timeout !";
        } else {
            throw new RuntimeException("Unknown status value !");
        }
    }

    /**
     * @return the compileStatus
     */
    public int getCompileStatus() {
        return compileStatus;
    }

    /**
     * @return the runtimeStatus
     */
    public int getRuntimeStatus() {
        return runtimeStatus;
    }

    /**
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }
}
