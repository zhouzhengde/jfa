/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Map;

public interface FileService {

    void upload(MultipartFile in, Callback callback);

    void download(OutputStream outputStream, Map<String, Object> params);

}
