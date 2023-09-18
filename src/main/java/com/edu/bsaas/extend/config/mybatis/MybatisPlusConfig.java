package com.edu.bsaas.extend.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单数据源配置（jeecg.datasource.open = false时生效）
 *
 * @Author zhoujf
 */
@Configuration
@MapperScan(value = {"com.edu.bsaas.extend.modules.**.mapper*"})
public class MybatisPlusConfig {

    /**
     * 分页拦截器
     *
     * @param
     * @return
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInterceptor);
        return mybatisPlusInterceptor;
    }

}
