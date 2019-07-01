package com.infinova.yimall.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

@Configuration
public class PageHelperConfig {

	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		// 数据库
		properties.setProperty("helperDialect", "mysql");
		// 是否将参数offset作为PageNum使用
		properties.setProperty("offsetAsPageNum", "true");
		// 是否进行count查询
		properties.setProperty("rowBoundsWithCount", "true");
		// 是否分页合理化
		properties.setProperty("reasonable", "false");
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}
