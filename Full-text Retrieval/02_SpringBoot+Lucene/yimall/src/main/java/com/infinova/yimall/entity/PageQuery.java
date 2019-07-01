package com.infinova.yimall.entity;

import java.util.List;
import java.util.Map;

/**
 * 带参数查询分页类
 * @author yizl
 *
 * @param <T>
 */
public class PageQuery<T> {

	private PageInfo pageInfo;
	/**
	 * 排序字段
	 */
	private Sort sort;
	/**
	 * 查询参数类
	 */
	private T params;
	/**
	 * 返回结果集
	 */
	private List<T> results;
	/**
	 * 不在T类中的参数
	 */
	private Map<String, String> queryParam;
	
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public Map<String, String> getQueryParam() {
		return queryParam;
	}
	public void setQueryParam(Map<String, String> queryParam) {
		this.queryParam = queryParam;
	}
	public T getParams() {
		return params;
	}
	public void setParams(T params) {
		this.params = params;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
}
