package com.edu.bsaas.extend.modules.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ClassTeacher {
    /**
     * 班级
     */
    @ExcelProperty(value = "班号")
    private String className;

    /**
     * 班主任
     */
    @ExcelProperty(value = "班主任")
    private String classTeacher;

    /**
     * 班级类型
     */
    @ExcelProperty(value = "班级类型")
    private String classType;

    /**
     * 语文老师
     */
    @ExcelProperty(value = "语文")
    private String chineseTeacher;

    /**
     * 数学
     */
    @ExcelProperty(value = "数学")
    private String mathematicsTeacher;

    /**
     * 英语
     */
    @ExcelProperty(value = "英语")
    private String englishTeacher;

    /**
     * 物理
     */
    @ExcelProperty(value = "物理")
    private String physicsTeacher;

    /**
     * 化学
     */
    @ExcelProperty(value = "化学")
    private String chemistryTeacher;

    /**
     * 生物
     */
    @ExcelProperty(value = "生物")
    private String organismTeacher;

    /**
     * 政治
     */
    @ExcelProperty(value = "政治")
    private String politicsTeacher;

    /**
     * 历史
     */
    @ExcelProperty(value = "历史")
    private String historyTeacher;

    /**
     * 地理
     */
    @ExcelProperty(value = "地理")
    private String geographyTeacher;

}