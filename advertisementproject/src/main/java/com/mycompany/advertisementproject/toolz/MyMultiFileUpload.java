
package com.mycompany.advertisementproject.toolz;

import java.io.File;
import org.vaadin.easyuploads.MultiFileUpload;

/**
 *
 * @author Czuppon Balint Peter
 */
public class MyMultiFileUpload extends MultiFileUpload {

    @Override
    protected void handleFile(File file, String fileName, String mimeType, long length) {
    }

    @Override
    protected String getAreaText() {
        return "";
    }
}
