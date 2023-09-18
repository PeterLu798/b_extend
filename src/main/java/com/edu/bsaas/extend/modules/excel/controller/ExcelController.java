package com.edu.bsaas.extend.modules.excel.controller;

import com.edu.bsaas.extend.modules.excel.service.StudentScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@Api(tags = "导入导出接口")
public class ExcelController {
    @Autowired
    private StudentScoreService studentScoreService;

    @PostMapping("/importScore")
    @ApiOperation(value = "上传数据")
    public String importScore(@RequestPart("file") MultipartFile file) {
        try {
            studentScoreService.upload(file.getInputStream());
        } catch (Exception e) {
            log.error("导入Excel文件出错：{}", e.getMessage(), e);
            return "上传失败";
        }
        return "上传成功";
    }

    @PostMapping("/importClassTeacher")
    @ApiOperation(value = "上传教师班主任信息")
    public String importClassTeacher(@RequestPart("file") MultipartFile file) {
        try {
            studentScoreService.uploadClassTeacher(file.getInputStream());
        } catch (Exception e) {
            log.error("导入ClassTeacher出错：{}", e.getMessage(), e);
            return "上传失败";
        }
        return "上传成功";
    }

    @GetMapping("/downloadExcel")
    @ApiOperation(value = "导出Excel")
    public String downloadExcel(HttpServletResponse response) {
        try {
            studentScoreService.downloadExcel(response);
        } catch (Exception e) {
            log.error("导出Excel文件出错：{}", e.getMessage(), e);
            return "导出失败";
        }
        return "导出成功";
    }

    @GetMapping("/clearData")
    @ApiOperation(value = "清理历史数据")
    public String clearData() {
        studentScoreService.clearData();
        return "清理成功";
    }

}
