package com.infinova;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.infinova.service.IBankCardService;
import com.infinova.service.impl.BankCardServiceImpl;
import com.infinova.service.impl.SendMessageInvocation;

public class ProxyTest {

	public static void main(String[] args) throws Exception {
		//目标对象
		IBankCardService bankCard = new BankCardServiceImpl();
		 //获取代理对象
		IBankCardService proxyBank = (IBankCardService)Proxy.newProxyInstance(bankCard.getClass().getClassLoader(), bankCard.getClass().getInterfaces(),
				new SendMessageInvocation(bankCard));
		//调用方法
		proxyBank.outMoney("9527");
	}

	/**
	 * 
	 * @param target
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private static Object getProxy(Object target) throws Exception {
		 //得到代理类大class
		Class proxyClass = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());
		//创建代理类的构造函数,构造函数的方法必须传入InvocationHandler接口的实现类
		Constructor constructor = proxyClass.getConstructor(InvocationHandler.class);
		//获取代理类
		Object proxy = constructor.newInstance(new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object resulet = method.invoke(target, args);
				//增强方法
                System.out.println("向客户发送短信");
				return resulet;
			}

		});
		return proxy;
	}
}
