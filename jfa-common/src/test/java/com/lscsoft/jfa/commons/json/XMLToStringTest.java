/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.json;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class XMLToStringTest extends TestCase {

    @Test
    public void test() throws IOException {

        FileInputStream inputStream = null;
        try {
            String path = Resource.class.getClassLoader().getResource("").getPath();

            inputStream = new FileInputStream(path + "demo.xml");

            StringBuilder stringBuilder = new StringBuilder();
            byte[] bts = new byte[1024];
            int len = inputStream.read(bts);

            while (len != -1) {
                stringBuilder.append(new String(bts, 0, len));
                len = inputStream.read(bts);
            }

            String xml = stringBuilder.toString();

            xml = xml.replaceAll("\r\n|>\\s*<","><");

            System.out.println(xml);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
