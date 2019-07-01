package com.infinova.lucene.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.infinova.lucene.bean.Product;

public class ProductUtil {

	public static void main(String[] args) throws IOException, InterruptedException {

		String fileName = "140k_products.txt";
		
		List<Product> products = file2list(fileName);
		//打印有多少条数据
		System.out.println(products.size());
			
	}


	/**
	 * 将文件夹中数据封装到List<Product>中
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static List<Product> file2list(String fileName) throws IOException {
		File f = new File(fileName);
		List<String> lines = FileUtils.readLines(f,"UTF-8");
		List<Product> products = new ArrayList<>();
		for (String line : lines) {
			Product p = line2product(line);
			products.add(p);
		}
		return products;
	}
	

	/**
	 * 将每行数据封装到Product对象中
	 * @param line
	 * @return
	 */
	private static Product line2product(String line) {
		Product p = new Product();
		String[] fields = line.split(",");
		p.setId(Integer.parseInt(fields[0]));
		p.setName(fields[1]);
		p.setCategory(fields[2]);
		p.setPrice(Float.parseFloat(fields[3]));
		p.setPlace(fields[4]);
		p.setCode(fields[5]);
		return p;
	}

}
