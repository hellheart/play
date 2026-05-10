package com.huige.learning.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public String page() {
        return "user";
    }

    @GetMapping("/api")
    @ResponseBody
    public Map<String, Object> query(@RequestParam(defaultValue = "") String name,
                                     @RequestParam(defaultValue = "") String email,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        PageHelper.startPage(page, size);
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.findByNameOrEmail(name, email));
        Map<String, Object> result = new HashMap<>();
        result.put("content", pageInfo.getList());
        result.put("totalElements", pageInfo.getTotal());
        result.put("totalPages", pageInfo.getPages());
        result.put("number", pageInfo.getPageNum());
        result.put("size", pageInfo.getPageSize());
        return result;
    }

    @PostMapping("/api")
    @ResponseBody
    public User save(@RequestBody User user) {
        if (user.getId() != null) {
            userMapper.update(user);
        } else {
            userMapper.insert(user);
        }
        return user;
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        userMapper.deleteById(id);
        return "ok";
    }

    @PostMapping("/api/batch")
    @ResponseBody
    public String batchSave(@RequestBody List<User> users) {
        userMapper.batchInsert(users);
        return "ok";
    }

    @GetMapping("/api/export")
    public void exportData(HttpServletResponse response) throws IOException {
        List<User> users = userMapper.findByNameOrEmail("", "");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户数据");
        Row header = sheet.createRow(0);
        String[] cols = {"ID", "用户名", "姓名", "年龄", "邮箱", "电话"};
        for (int i = 0; i < cols.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(cols[i]);
            cell.setCellStyle(headerStyle(workbook));
        }
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(u.getId() != null ? u.getId() : 0);
            row.createCell(1).setCellValue(u.getUsername() != null ? u.getUsername() : "");
            row.createCell(2).setCellValue(u.getName() != null ? u.getName() : "");
            row.createCell(3).setCellValue(u.getAge() != null ? u.getAge() : 0);
            row.createCell(4).setCellValue(u.getEmail() != null ? u.getEmail() : "");
            row.createCell(5).setCellValue(u.getPhone() != null ? u.getPhone() : "");
        }
        for (int i = 0; i < cols.length; i++) {
            sheet.autoSizeColumn(i);
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("用户数据.xlsx", "UTF-8"));
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.flush();
    }

    @PostMapping("/api/import")
    @ResponseBody
    public Map<String, Object> importData(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<User> users = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                User user = new User();
                user.setUsername(getCellString(row, 1));
                user.setName(getCellString(row, 2));
                String ageStr = getCellString(row, 3);
                user.setAge(ageStr.isEmpty() ? null : (int) Double.parseDouble(ageStr));
                user.setEmail(getCellString(row, 4));
                user.setPhone(getCellString(row, 5));
                users.add(user);
            }
            workbook.close();
            if (!users.isEmpty()) {
                userMapper.batchInsert(users);
            }
            result.put("code", 200);
            result.put("message", "成功导入 " + users.size() + " 条数据");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "导入失败: " + e.getMessage());
        }
        return result;
    }

    private String getCellString(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private CellStyle headerStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
