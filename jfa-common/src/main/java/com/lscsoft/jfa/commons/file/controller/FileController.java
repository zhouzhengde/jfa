/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.file.controller;

import com.lscsoft.jfa.commons.common.BaseController;
import com.lscsoft.jfa.commons.common.ResultMap;
import com.lscsoft.jfa.commons.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("api/file")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    @RequestMapping("upload")
    public Map<String, Object> upload(@RequestParam MultipartFile file) {

        try {

            fileService.upload(file, null);

            return ResultMap.success();

        } catch (Exception e) {
            return ResultMap.failure(e);
        }
    }

    @RequestMapping("download")
    public Map<String, Object> download(HttpServletRequest request, HttpServletResponse response) {

        try {

            fileService.download(response.getOutputStream(), null);

            return ResultMap.success();

        } catch (IOException e) {
            return ResultMap.failure(e);
        }
    }
}
