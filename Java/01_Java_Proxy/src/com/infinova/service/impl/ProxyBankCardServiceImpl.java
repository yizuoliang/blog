package com.infinova.service.impl;

import com.infinova.service.IBankCardService;

/**
 * 代理银行卡操作实现类(静态代理)
 * @author yizl
 *
 */
public class ProxyBankCardServiceImpl implements IBankCardService {

	private IBankCardService bankCardService;
	
	public ProxyBankCardServiceImpl(IBankCardService bankCardService) {
		this.bankCardService=bankCardService;
	}

	@Override
	public void putInMoney(String cardId) {
		bankCardService.putInMoney(cardId);
		System.out.println("向客户发送短信");
	}

	@Override
	public void outMoney(String cardId) {
		bankCardService.outMoney(cardId);
		System.out.println("向客户发送短信");
	}

	@Override
	public String getMoney(String cardId) {
		bankCardService.getMoney(cardId);
		System.out.println("向客户发送短信");
		return null;
	}
	
}
