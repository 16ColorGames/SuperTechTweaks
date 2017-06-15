/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.logging.log4j.core.helpers.Loader;

/**
 *
 * @author oa10712
 */
public class Utils {

    public static void copyFileUsingStream(String source, String dest) throws IOException {

        copyFileUsingStream(source, new File(dest));
    }

    public static void copyFileUsingStream(String source, File dest) throws IOException {

        InputStream is = Loader.getResource(source, null).openStream();
        OutputStream os = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
    }
}
