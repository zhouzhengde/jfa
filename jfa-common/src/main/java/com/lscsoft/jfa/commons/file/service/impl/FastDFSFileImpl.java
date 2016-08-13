/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.file.service.impl;

import com.github.tobato.fastdfs.domain.StorageNode;
import com.github.tobato.fastdfs.service.DefaultTrackerClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import com.lscsoft.jfa.commons.file.config.FastDFSConfig;
import com.lscsoft.jfa.commons.file.service.Callback;
import com.lscsoft.jfa.commons.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Map;

@Service
public class FastDFSFileImpl implements FileService {

    @Autowired
    private FastDFSConfig config;


    public void upload(MultipartFile file, Callback callback) {

        TrackerClient tracker = new DefaultTrackerClient();
        StorageNode storageNode = tracker.getStoreStorage();
        storageNode.setIp(config.getTrackerServerIP());
        storageNode.setPort(config.getTrackerServerPORT());



        //StorageUploadFileRequest uploadFileRequest = new StorageUploadFileRequest();

    }

    public void download(OutputStream outputStream, Map<String, Object> params) {

    }

}
