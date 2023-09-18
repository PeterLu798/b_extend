package com.edu.bsaas.extend.modules.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.bsaas.extend.modules.excel.entity.AnalysisScore;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnalysisScoreMapper extends BaseMapper<AnalysisScore> {
    @Select("select * from analysis_score")
    List<AnalysisScore> selectAll();

    @Delete("delete from analysis_score")
    void deleteAll();
}
