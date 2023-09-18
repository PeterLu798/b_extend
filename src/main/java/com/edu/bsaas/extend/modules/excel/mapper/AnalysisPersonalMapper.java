package com.edu.bsaas.extend.modules.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.bsaas.extend.modules.excel.entity.AnalysisPersonal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnalysisPersonalMapper extends BaseMapper<AnalysisPersonal> {

    @Select("select * from analysis_personal where course = #{course}")
    List<AnalysisPersonal> selectByCourse(@Param("course") String course);

    @Delete("delete from analysis_personal")
    void deleteAll();
}
