package com.infinova.yimall.service;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import com.infinova.yimall.entity.PageQuery;
import com.infinova.yimall.entity.Product;

public interface ILuceneService {
	/**
	 * 启动后将同步Product表,并创建index
	 * @throws IOException 
	 */
	public void synProductCreatIndex() throws IOException;

	public PageQuery<Product> searchProduct(PageQuery<Product> pageQuery) throws IOException, ParseException;
}
