package com.infinova.yimall.dao;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import com.infinova.yimall.entity.PageQuery;
import com.infinova.yimall.entity.Product;

public interface ILuceneDao {
	/**
	 * 创建索引
	 * @param productList
	 * @throws IOException 
	 */
	public void createProductIndex(List<Product> productList) throws IOException;
	/**
	 * 查询索引
	 * @param pageQuery
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public PageQuery<Product> searchProduct(PageQuery<Product> pageQuery) throws IOException, ParseException;
	/**
	 * 添加一个新索引
	 * @param product
	 * @throws IOException 
	 */
	public void addProductIndex(Product product) throws IOException;
	/**
	 * 通过id删除商品索引
	 * @param id
	 * @throws IOException 
	 */
	public void deleteProductIndexById(String id) throws IOException;
	
	
}
