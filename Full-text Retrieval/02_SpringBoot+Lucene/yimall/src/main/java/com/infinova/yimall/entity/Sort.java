package com.infinova.yimall.entity;
/**
 * 排序字段类
 * @author yizl
 *
 */
public class Sort {
	/**
	 * 字段名
	 */
	private String field;
	/**
	 * 升序,asc,desc
	 */
    private String order;
    
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
    
    
}
