package com.infinova.service;
/**
 * 银行操作接口
 * @author yizl
 *
 */
public interface IBankCardService {
	
	/**
	 * 存钱
	 * @param cardId
	 */
	public void putInMoney(String cardId);
	
	/**
	 * 取钱
	 * @param cardId
	 */
	public void outMoney(String cardId);
	
	/**
	 * 查询余额
	 * @param cardId
	 */
	public String getMoney(String cardId);
}
