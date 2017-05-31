/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.coderun;

import com.abdallamahmoud.codeyard.model.CodeResult;

/**
 *
 * @author abdalla
 */
public interface CodeRunCallback {

    void onResule(CodeResult codeResult);

    void onError(Exception ex);
}
