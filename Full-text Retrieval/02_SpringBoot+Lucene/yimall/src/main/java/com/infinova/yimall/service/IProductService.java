package com.infinova.yimall.service;

import java.io.IOException;
import java.util.List;

import com.infinova.yimall.entity.PageQuery;
import com.infinova.yimall.entity.Product;
/**
 * 商品服务接口
 * @author yizl
 *
 */
public interface IProductService {
	/**
	 * 通过id获取商品详情
	 * @param id
	 * @return
	 */
	public Product getProductById(String id);
	/**
	 * 获取所有商品
	 * @return
	 */
	public List<Product> getAllProduct();
	
	/**
	 * 通过参数查询 
	 * @param pageQuery 分页查询参数
	 * @return
	 */
	public List<Product> getProductList(PageQuery<Product> pageQuery);
	/**
	 * 添加商品
	 * @param product
	 * @throws IOException 
	 */
	public void addProduct(Product product) throws IOException;
	/**
	 * 根据id删除商品
	 * @param id
	 * @throws IOException 
	 */
	public void deleteProductById(String id) throws IOException;
	/**
	 * 更新商品信息
	 * @param product
	 * @throws IOException 
	 */
	public void updateProductById(Product product) throws IOException; 
}
