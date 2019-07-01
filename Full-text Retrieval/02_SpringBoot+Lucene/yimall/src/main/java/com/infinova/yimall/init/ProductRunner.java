package com.infinova.yimall.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.infinova.yimall.service.ILuceneService;
/**
 * 项目启动后,立即执行
 * @author yizl
 *
 */
@Component
@Order(value = 1)
public class ProductRunner implements ApplicationRunner {
	
	@Autowired
	private ILuceneService service; 
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		/**
		 * 启动后将同步Product表,并创建index
		 */
		service.synProductCreatIndex();
	}
}
