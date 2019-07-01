package com.infinova.yimall.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.infinova.yimall.entity.PageInfo;
import com.infinova.yimall.entity.PageQuery;
import com.infinova.yimall.entity.Product;
import com.infinova.yimall.entity.ResultBean;
import com.infinova.yimall.service.IProductService;
import com.infinova.yimall.utils.ResultUtil;
/**
 * 操作商品的控制层
 * @author yizl
 *
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private IProductService service;
	
	@GetMapping("/helloProduct")
	public String helloProduct() {
		return "Hello Product !";
	}
	
	
	@PostMapping("/getProductList")
	public ResultBean<PageQuery<Product>> getProductList(@RequestBody PageQuery<Product> pageQuery) {
		PageInfo pageInfo = pageQuery.getPageInfo();
		Page<Product> page = PageHelper.startPage(pageInfo);
		//也可以使用PageHelper的排序
		//PageHelper.orderBy("id desc");
		List<Product> pList=service.getProductList(pageQuery);
		pageInfo.setTotal(page.getTotal());
		pageQuery.setResults(pList);
		
		return ResultUtil.success(pageQuery);
	}
	
	@PostMapping("/addProduct")
	public ResultBean<Product> addProduct(@RequestBody Product product) throws IOException{
		service.addProduct(product);
		return ResultUtil.success();
	}
	
	@GetMapping("/deleteProductById")
	public ResultBean<Product> deleteProductById(String id) throws IOException{
		service.deleteProductById(id);
		return ResultUtil.success();
	}
	
	@PostMapping("/updateProductById")
	public ResultBean<Product> updateProductById(@RequestBody Product product) throws IOException{
		service.updateProductById(product);
		return ResultUtil.success();
	}
	
}
