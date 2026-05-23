package com.athuige.demo;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class HtmlToPdfService {

    @Autowired
    private TemplateEngine templateEngine;

    public void transTohtml(Map<String, Object> stringObjectMap) throws IOException {
        String templateName = "templates.html";

        Context context = new Context();
        context.setVariable("user", stringObjectMap);
        String template = templateEngine.process(templateName, context);
        System.out.println("template = " + template);

        // 4. 将渲染后的HTML写入临时文件
        File tempHtmlFile = File.createTempFile("temp", ".html");
        try (PrintWriter writer = new PrintWriter(tempHtmlFile)) {
            writer.write(template);
        }
        Path path = Paths.get("D:/test.pdf");
        try (OutputStream fileOutputStream = Files.newOutputStream(path)){

            PdfRendererBuilder pdfRendererBuilder = new PdfRendererBuilder();
            pdfRendererBuilder.withUri(path.toFile().toURI().toASCIIString());
            pdfRendererBuilder.withHtmlContent(template, null);
            pdfRendererBuilder.toStream(fileOutputStream);

            // 添加系统字体
            pdfRendererBuilder.useFont(() -> {
                try {
                    return new FileInputStream("C:/Windows/Fonts/arial.ttf");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }, "Arial");
            pdfRendererBuilder.useFont(() -> {
                try {
                    return new FileInputStream("C:/Windows/Fonts/times.ttf");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }, "Times New Roman");
            pdfRendererBuilder.useFont(() -> {
                try {
                    return new FileInputStream("C:/Windows/Fonts/cour.ttf");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }, "Courier");

            pdfRendererBuilder.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 4. 清理临时文件
            if (tempHtmlFile.exists()) {
                tempHtmlFile.delete();
            }
        }
    }

}
