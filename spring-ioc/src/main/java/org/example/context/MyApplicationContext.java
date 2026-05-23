package org.example.context;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class MyApplicationContext implements ApplicationContext{
    public static Map<Class, Object> beanMap = new HashMap<>();

    public static String rootPath;

    @Override
    public Object getBean(Class clazz) {
        return beanMap.get(clazz);
    }

    public MyApplicationContext(String packagePath) throws Exception {
        String replaceAll = packagePath.replaceAll("\\.", "\\\\");
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(replaceAll);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String decode = URLDecoder.decode(url.getFile(), "utf-8");
            rootPath = decode.substring(decode.length() - replaceAll.length());
            loadFile(new File(decode));
        }
    }

    private void loadFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (File file1 : files) {
                    loadFile(file1);
                }
            }
        } else {
            String absolutePath = file.getAbsolutePath();
            String substring = absolutePath.substring(rootPath.length());
            String replaceAll = substring.replaceAll("\\\\", "\\.");
            String classPath = replaceAll.replaceAll(".class", "");
            try {
                Class aClass = Class.forName(classPath);
                if (aClass.isInterface()) {

                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
