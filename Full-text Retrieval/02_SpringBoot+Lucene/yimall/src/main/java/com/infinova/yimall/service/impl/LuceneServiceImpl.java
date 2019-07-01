package com.infinova.yimall.service.impl;


import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinova.yimall.dao.ILuceneDao;
import com.infinova.yimall.entity.PageQuery;
import com.infinova.yimall.entity.Product;
import com.infinova.yimall.mapper.ProductMapper;
import com.infinova.yimall.service.ILuceneService;

@Service
public class LuceneServiceImpl implements ILuceneService {

	@Autowired
	private ILuceneDao luceneDao;

	@Autowired
	private ProductMapper mapper;

	@Override
	public void synProductCreatIndex() throws IOException {
		// 获取所有的productList
		List<Product> allProduct = mapper.getAllProduct();
		// 再插入productList
		luceneDao.createProductIndex(allProduct);
	}

	@Override
	public PageQuery<Product> searchProduct(PageQuery<Product> pageQuery) throws IOException, ParseException {
		return luceneDao.searchProduct(pageQuery);
	}

}
