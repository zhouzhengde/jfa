/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.data.generator;

import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

public class JfaGenerator extends PluginAdapter implements Plugin {


    public boolean validate(List<String> list) {
        return false;
    }
}
