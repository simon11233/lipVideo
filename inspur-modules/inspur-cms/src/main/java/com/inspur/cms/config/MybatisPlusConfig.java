package com.inspur.cms.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Mybatis-Plus插件配置类
 *
 * @author simon
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.inspur.cms.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

      /**
      * SQL 执行性能分析插件
      * 开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
      * */
      @Bean
      @Profile({"dev","test"})// 设置 dev test 环境开启
      public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        //ms，超过此处设置的ms则sql不执行
        performanceInterceptor.setMaxTime(1000);
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
      }
}
