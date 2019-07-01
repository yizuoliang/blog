package com.infinova.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 发短信,增强方法
 * @author yizl
 *
 */
public class SendMessageInvocation implements InvocationHandler {
	/**
	 */
	private Object obj;
	
	/**
	 * @param obj
	 */
	public SendMessageInvocation(Object obj) {
		this.obj=obj;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object resulet = method.invoke(obj,args);
		System.out.println("向客户发送短信");
		return resulet;

	}

}
