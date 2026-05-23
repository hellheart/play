package com.huige.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.huige.demo.domain.RequsetDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {

    @PostMapping("/export")
    void startExport(@RequestBody RequsetDto requsetDto) {
        String path = "";
        List<List<String>> heads= new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        objects.add("年纪");
        EasyExcel.write(path).sheet("daochu").doWrite(objects);
    }
}
