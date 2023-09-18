package com.edu.bsaas.extend.modules.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AnalysisScore {
    /**
     * 班级
     */
    @ExcelProperty(value = "班级")
    private String className;

    /**
     * 班级类型
     */
    @ExcelProperty(value = "班级类型")
    private String classType;

    /**
     * 科目
     */
    @ExcelProperty(value = "科目")
    private String course;

    /**
     * 对应教师
     */
    @ExcelProperty(value = "对应教师")
    private String teacher;

    /**
     * 年级均分
     */
    @ExcelProperty(value = "年级均分")
    private Double gradeMean;

    /**
     * 年级标准差
     */
    @ExcelProperty(value = "年级标准差")
    private Double gradeStd;

    /**
     * 班级标准分均分
     */
    @ExcelProperty(value = "班级标准分均分")
    private Double classStd;

}