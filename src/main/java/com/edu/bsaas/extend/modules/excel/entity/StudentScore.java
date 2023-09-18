package com.edu.bsaas.extend.modules.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StudentScore {

    @ExcelProperty(value = "年级")
    private String grade;

    @ExcelProperty(value = "班级")
    private String className;

    @ExcelProperty(value = "学号")
    private Long studentId;

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "性别")
    private String sex;

    @ExcelProperty(value = "民族")
    private String nation;

    @ExcelProperty(value = "身份证号码")
    private String idCard;

    @ExcelProperty(value = "政治面貌")
    private String politicCountenance;

    @ExcelProperty(value = "是否住宿")
    private String ifLiveInSchool;

    @ExcelProperty(value = "区县")
    private String county;

    @ExcelProperty(value = "初中毕业学校")
    private String graduateSchool;

    @ExcelProperty(value = "中考准考证号吗")
    private String middleSchoolNumber;

    @ExcelProperty(value = "语文120")
    private Double chinese;

    @ExcelProperty(value = "数学120")
    private Double mathematics;

    @ExcelProperty(value = "英语120")
    private Double english;

    @ExcelProperty(value = "物理75")
    private Double physics;

    @ExcelProperty(value = "化学65")
    private Double chemistry;

    @ExcelProperty(value = "生物30")
    private Double organism;

    @ExcelProperty(value = "政治70")
    private Double politics;

    @ExcelProperty(value = "历史30")
    private Double history;

    @ExcelProperty(value = "地理30")
    private Double geography;

    @ExcelProperty(value = "中考成绩")
    private Double middleSchoolScore;
}
