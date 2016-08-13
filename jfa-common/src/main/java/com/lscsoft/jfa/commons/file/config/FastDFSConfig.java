/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fastdfs")
public class FastDFSConfig {

    private String trackerServerIP;

    private String storageServerIP;

    private Integer trackerServerPORT;

    private Integer storageServerPORT;


    public String getTrackerServerIP() {
        return trackerServerIP;
    }

    public void setTrackerServerIP(String trackerServerIP) {
        this.trackerServerIP = trackerServerIP;
    }

    public String getStorageServerIP() {
        return storageServerIP;
    }

    public void setStorageServerIP(String storageServerIP) {
        this.storageServerIP = storageServerIP;
    }

    public Integer getTrackerServerPORT() {
        return trackerServerPORT;
    }

    public void setTrackerServerPORT(Integer trackerServerPORT) {
        this.trackerServerPORT = trackerServerPORT;
    }

    public Integer getStorageServerPORT() {
        return storageServerPORT;
    }

    public void setStorageServerPORT(Integer storageServerPORT) {
        this.storageServerPORT = storageServerPORT;
    }
}
