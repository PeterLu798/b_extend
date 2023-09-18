package com.edu.bsaas.extend.modules.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.bsaas.extend.modules.excel.entity.ClassTeacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassTeacherMapper extends BaseMapper<ClassTeacher> {

    @Select("select * from class_teacher")
    List<ClassTeacher> selectAll();

    @Delete("delete from class_teacher")
    void deleteAll();
}
