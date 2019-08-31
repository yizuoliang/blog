package com.infinova.service.impl;

import com.infinova.service.IBankCardService;
/**
 * 银行卡操作实现类
 * @author yizl
 *
 */
public class BankCardServiceImpl implements IBankCardService {

	@Override
	public void putInMoney(String cardId) {
		System.out.println("开始往银行卡账号为:"+cardId+" 存钱");
	}

	@Override
	public void outMoney(String cardId) {
		System.out.println("向银行卡账号为:"+cardId+" 取钱");
	}

	@Override
	public String getMoney(String cardId) {
		System.out.println("查询银行卡账号为:"+cardId+" 的余额");
		return null;
	}

}
