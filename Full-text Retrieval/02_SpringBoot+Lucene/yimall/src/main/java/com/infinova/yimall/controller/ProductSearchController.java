package com.infinova.yimall.controller;



import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinova.yimall.entity.PageQuery;
import com.infinova.yimall.entity.Product;
import com.infinova.yimall.entity.ResultBean;
import com.infinova.yimall.service.ILuceneService;
import com.infinova.yimall.utils.ResultUtil;

@RestController
@RequestMapping("/product/search")
public class ProductSearchController {
	
	@Autowired
	private ILuceneService service;
	/**
	 * 
	 * @param pageQuery
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@PostMapping("/searchProduct")
	private ResultBean<PageQuery<Product>> searchProduct(@RequestBody PageQuery<Product> pageQuery) throws IOException, ParseException {
		PageQuery<Product> pageResult= service.searchProduct(pageQuery);
		return ResultUtil.success(pageResult);
	}
	
}
