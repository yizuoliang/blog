package com.infinova.lucene;

import java.io.IOException;
import java.util.List;

import com.infinova.lucene.bean.Product;
import com.infinova.lucene.dao.ProductDao;
import com.infinova.lucene.util.ProductUtil;

/**
 * 操作商品信息表
 * @author yizl
 *
 */
public class ProductTest {

	private static ProductDao dao= new ProductDao();
	
	public static void main(String[] args) throws Exception {
		//addProduct();
		//查询所有的数据
		selectAllProduct();
	}
	
	/**
	 * 将文档数据全部添加到product表中
	 * @throws IOException 
	 */
	private static void addProduct() throws IOException{
		long start = System.currentTimeMillis();
		List<Product> pList = ProductUtil.file2list("140k_products.txt");
		dao.insertProduct(pList);
		System.out.println("添加"+pList.size()+"条数据,耗时:"+(System.currentTimeMillis() - start)+" 毫秒");
	}
	/**
	 * 查询出所有的商品
	 */
	private static void selectAllProduct() {
		List<Product> pList = dao.selectAllProduct();
		System.out.println("查询出所有的商品,条数为:"+pList.size());	
	}
}
