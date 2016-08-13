/*
 * Copyright (c) 2015. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.util;

import com.lscsoft.jfa.commons.exception.DataAccessException;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;


public final class JAXBUtils {

    private JAXBUtils() {
    }

    /**
     * 通过一个指定的Java类，和一个文件生成一个POJO
     *
     * @param clz    Class
     *               由xjc.bat生成
     * @param xmlDoc 数据文件
     * @return
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T parseXmlDocument(Class<?> clz, File xmlDoc) throws DataAccessException {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clz);

            Unmarshaller shaller = jaxbContext.createUnmarshaller();

            return (T) shaller.unmarshal(xmlDoc);
        } catch (Exception e) {
            throw new DataAccessException("[JAXB parseXmlDocument Error]", e);
        }
    }

    /**
     * 生成一个指定XML文档
     *
     * @param t             T
     * @param storeFileName
     * @return
     * @throws JAXBException
     * @throws FileNotFoundException
     */
    public static <T extends Object> Boolean generalXmlDocument(T t, String storeFileName) throws DataAccessException {
        try {

            String pkg = t.getClass().getPackage().getName();
            JAXBContext context = JAXBContext.newInstance(pkg);
            JAXBElement<?> element = _invoke(t);
            if (element == null) {
                return false;
            }
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(element, new FileOutputStream(storeFileName));
            return true;
        } catch (Exception e) {
            throw new DataAccessException("[JAXB generalXmlDocument Error]", e);
        }
    }

    /*
     * 生成一个工厂对象,并创建JAXBElement对象
     */
    @SuppressWarnings("unchecked")
    private static <T extends Object> JAXBElement<T> _invoke(Object o) throws DataAccessException {
        try {
            String pkg = o.getClass().getPackage().getName();
            Class<?> clz = Class.forName(pkg + ".ObjectFactory");

            Method[] methods = clz.getMethods();
            Object factory = clz.newInstance();
            String destName = "create" + o.getClass().getSimpleName();
            for (Method method : methods) {
                if (method.getName().equals(destName)) {
                    return (JAXBElement<T>) method.invoke(factory, o);
                }
            }
            return null;
        } catch (Exception e) {
            throw new DataAccessException("[JAXB _invoke Error]", e);
        }
    }
}
