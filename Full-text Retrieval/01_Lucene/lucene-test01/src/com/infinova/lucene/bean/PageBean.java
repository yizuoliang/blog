package com.infinova.lucene.bean;
/**
 * 分页bean
 * @author yizl
 *
 */
public class PageBean {
    /**
     * 当前页数
     */
    private Integer pageNow;

    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Integer total;
    
	public Integer getPageNow() {
		return pageNow;
	}
	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
    
    
}
