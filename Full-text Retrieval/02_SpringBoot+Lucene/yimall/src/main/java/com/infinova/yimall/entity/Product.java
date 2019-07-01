package com.infinova.yimall.entity;
/**
 * 商品bean类
 * @author yizl
 *
 */
public class Product {
	/**
	 * 商品id
	 */
	private int id;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品类型
	 */
	private String category;
	/**
	 * 商品价格
	 */
	private float price;
	/**
	 * 商品产地
	 */
	private String place;
	/**
	 * 商品条形码
	 */
	private String code;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
