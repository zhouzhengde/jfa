/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.util;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * To Export data to a format file
 *
 * @author Bond(China)
 * @version 1.0.0
 */
public final class WebBook {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebBook.class);

    private String fileName;

    private String contentType;

    /**
     * File type set
     */
    public enum FileType {

        XLS, XLS_X;

        public Workbook createWorkbook() {
            Workbook workbook = null;
            switch (this) {
                case XLS:
                    workbook = new HSSFWorkbook();
                    break;
                case XLS_X:
                    workbook = new XSSFWorkbook();
                    break;
            }
            return workbook;
        }

    }

    /**
     * Customize to template
     */
    public interface CustomizeWorkbook {

        /**
         * Orient extend with outside
         *
         * @param workbook 文档
         * @return 文档
         */
        Workbook customize(Workbook workbook);
    }

    /**
     * To Export
     *
     * @param templateFile 模板文件
     * @param dataSource   数据源
     * @param request      请求HttpServletRequest
     * @param response     响应HttpServletResponse
     */
    public void export(File templateFile, Map<String, Object> dataSource, HttpServletRequest request, HttpServletResponse response) {

        if (templateFile != null && !templateFile.exists()) {
            return;
        }

        InputStream in = null;
        OutputStream out = null;

        try {

            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType(getContentType());

            in = new BufferedInputStream(new FileInputStream(templateFile));
            out = response.getOutputStream();

            XLSTransformer transformer = new XLSTransformer();
            Workbook workbook = transformer.transformXLS(in, dataSource);
            workbook.write(out);
            out.flush();

        } catch (IOException e) {
            LOGGER.error("[WebBook export]: Occur an error", e);
        } catch (InvalidFormatException e) {
            LOGGER.error("[WebBook export]: Occur an error", e);
        } finally {
            FileUtils.close(in);
            FileUtils.close(out);
        }
    }


    /**
     * To Export
     *
     * @param fileType          文件类型
     * @param customizeWorkbook 客户化模板实例
     * @param response          响应HttpServletResponse
     */
    public void export(FileType fileType, CustomizeWorkbook customizeWorkbook, HttpServletResponse response) {

        InputStream in = null;
        OutputStream out = null;

        try {

            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType(getContentType());
            out = response.getOutputStream();

            Workbook workbook = fileType.createWorkbook();
            customizeWorkbook.customize(workbook).write(out);
            //out.flush();

        } catch (IOException e) {
            LOGGER.error("[WebBook export]: Occur an error", e);
        } finally {
            FileUtils.close(in);
            FileUtils.close(out);
        }
    }

    /**
     * Set FileName
     *
     * @param fileName 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Set Content Type
     *
     * @param contentType ContentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        if ($.isEmpty(contentType)) {
            return "application/octet-stream";
        }
        return contentType;
    }
}