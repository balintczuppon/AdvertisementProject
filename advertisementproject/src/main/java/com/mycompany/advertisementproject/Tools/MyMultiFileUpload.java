/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.advertisementproject.Tools;

import java.io.File;
import org.vaadin.easyuploads.MultiFileUpload;

/**
 *
 * @author balin
 */
public class MyMultiFileUpload extends MultiFileUpload {

    @Override
    protected void handleFile(File file, String fileName, String mimeType, long length) {
    }

    protected String getAreaText() {
        return "";
    }
}
