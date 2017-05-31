/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.coderun;

import com.abdallamahmoud.codeyard.model.CodeResult;
import com.abdallamahmoud.codeyard.model.LangInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author abdalla
 */
public class CodeRunner {

    private LangInfo langInfo;
    private String code;
    private String input;

    public CodeRunner() {
    }

    public CodeRunner(LangInfo langInfo, String code, String input) {
        this.langInfo = langInfo;
        this.code = code;
        this.input = input;
    }

    public CodeResult runCode() {
        // copy coderun.sh, app/run.sh to tmp directory
        File srcDir = new File("langs", langInfo.getId());
        File tmpDir = new File("tmp", UUID.randomUUID().toString().replace("-", ""));
        File coderunFile = new File("langs", "coderun.sh");

        try {
            // copy app directory to the tmp directory
            FileUtils.copyDirectory(srcDir, tmpDir);
            // copy coderun file to the tmp directory
            FileUtils.copyFileToDirectory(coderunFile, tmpDir);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        File appDir = new File(tmpDir, "app");
        File codeFile = new File(appDir, langInfo.getSrc());
        File inputFile = new File(appDir, "input.txt");
        coderunFile = new File(tmpDir, "coderun.sh");

        // wirte the code and the input
        try {
            FileUtils.writeStringToFile(codeFile, code, "UTF-8");
            FileUtils.writeStringToFile(inputFile, input + "\n", "UTF-8");

            coderunFile.setExecutable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // execute the code
        try {
            Process process = Runtime.getRuntime().exec("./" + coderunFile.getName() + " " + langInfo.getDockerImage(),
                    null, new File(tmpDir.getAbsolutePath()));
//            System.out.println("========= Error");
//            System.out.println(readStream(process.getErrorStream()));
//            System.out.println("========= End Error");
//            System.out.println("========= Output");
//            System.out.println(readStream(process.getInputStream()));
//            System.out.println("========= End Output");
            process.waitFor();
            System.out.println("Finished Running");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String output = null;
        String error = null;
        String status = null;

        // capture output
        try {

            File outputFile = new File(tmpDir, "output.txt");
            File statusFile = new File(tmpDir, "status.txt");
            File errorFile = new File(tmpDir, "error.txt");

            output = (FileUtils.readFileToString(outputFile, "UTF-8"));
            status = (FileUtils.readFileToString(statusFile, "UTF-8"));
            error = (FileUtils.readFileToString(errorFile, "UTF-8"));

//            System.out.println("=========== Output");
//            System.out.println(output);
//
//            System.out.println("=========== Status");
//            System.out.println(status);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // delete tmp directory
        try {
            FileUtils.deleteDirectory(tmpDir);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // analyze output
        try {
            if (langInfo.isCompiled()) {

                String[] statusArr = status.split("\n");

                int compilerStatus = Integer.parseInt(statusArr[0]);
                int runtimeStatus = -1;
                if (statusArr.length > 1) {
                    runtimeStatus = Integer.parseInt(statusArr[1]);
                }

                int codeStatus = -1;
                if (compilerStatus == 0) {
                    // compiled successfully
                    if (runtimeStatus == 0) {
                        // run successfully
                        codeStatus = CodeResult.STATUS_SUCCESS;
                    } else if (runtimeStatus == 124) {
                        // run with timeout
                        codeStatus = CodeResult.STATUS_TIME_OUT;
                    } else {
                        // runtime error
                        codeStatus = CodeResult.STATUS_RUNTIME_ERROR;
                    }
                } else {
                    // compile error
                    codeStatus = CodeResult.STATUS_COMPILE_ERROR;
                }

                CodeResult codeResult = new CodeResult(codeStatus, compilerStatus, runtimeStatus, output,error);
                return codeResult;

            } else {
                String[] statusArr = status.split("\n");

                int runtimeStatus = Integer.parseInt(statusArr[0]);
//                int runtimeStatus = -1;
//                if (statusArr.length > 1) {
//                    runtimeStatus = Integer.parseInt(statusArr[1]);
//                }

                int codeStatus = -1;
                // compiled successfully
                if (runtimeStatus == 0) {
                    // run successfully
                    codeStatus = CodeResult.STATUS_SUCCESS;
                } else if (runtimeStatus == 124) {
                    // run with timeout
                    codeStatus = CodeResult.STATUS_TIME_OUT;
                } else {
                    // runtime error
                    codeStatus = CodeResult.STATUS_RUNTIME_ERROR;
                }

                CodeResult codeResult = new CodeResult(codeStatus, -1, runtimeStatus, output,error);
                return codeResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private String removeLastLine(String string) {
        return string.substring(0, string.length() - 1);
    }

//    private String readStream(InputStream is) {
//        try {
//            StringBuilder sb = new StringBuilder();
//            int len;
//            byte[] barr = new byte[512];
//            while ((len = is.read(barr)) != -1) {
//                sb.append(new String(barr, 0, len));
//            }
//            return sb.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
