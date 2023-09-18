package com.edu.bsaas.extend.modules.excel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AnalysisPersonal {
    /**
     * 班级
     */
    @ExcelProperty(value = "班级")
    private String className;

    /**
     * 学号
     */
    @ExcelProperty(value = "学号")
    private Long studentId;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 科目分数
     */
    @ExcelProperty(value = "该科目分数")
    private Double middleSchoolScore;

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
     * 个人标准分
     */
    @ExcelProperty(value = "个人标准分")
    private Double personalStd;

    /**
     * 科目
     */
    @ExcelIgnore
    private String course;

}