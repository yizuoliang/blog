# JDK的动态代理

## 一、静态代理

了解动态代理前，有必要先讲解下静态代理。

举个例子：银行开通了短信业务，在你取钱，存钱，转账后都会 给你发送短信，我们来模拟下业务场景。

### 静态代理的实现

下面来模拟下业务代码

#### 1.定义IBankCardService接口

	/**
	 * 银行卡操作接口
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
#### 2.接口实现(BankCardServiceImpl)

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

#### 3.编写代理类

假设项目经理有个需求:在每次业务操作后都需要向用户发送短信.

在不修改已有的实现类的前提下怎么实现这个需求.

1.我们写一个代理类,让它与银行卡操作实现类的接口相同.

2.在代理类的构造器中,传入银行卡操作实现类,在代理类的方法内部仍然调用银行卡操作实现类的方法.



![静态代理](F:\picture\静态代理.jpg)



代理类

	/**
	 * 代理银行卡操作实现类
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

#### 4.调用代理类

	public class ProxyTest {
	
		public static void main(String[] args) {
			IBankCardService bankCardService =new BankCardServiceImpl();
			IBankCardService proxyBankCard=new ProxyBankCardServiceImpl(bankCardService);
			proxyBankCard.putInMoney("9527");
		}
		
	}
	
	打印结果:
	    开始往银行卡账号为:9527的账户存钱
	    向客户发送短信
	

可以看出,代理类的作用:**代理对象=增强代码+目标对象**

代理类只对银行卡操作实现类进行增强，每个方法都添加发送短信业务，真正业务还是在银行卡操作实现类中在进行。

### 静态代理的缺点

我们发现静态代码其实很麻烦,有点脱裤子放屁的意思.

静态代理的缺点：

​	1.要为每一个目标类都要编写相应的代理类，会有很多代理类。

​	2.接口改了，目标类和代理类都要跟着改。

## 二、动态代理

我们只想写增强的代码，不需要写代理类，增强代码还可以复用到不同的目标类。这时动态代理横空出世了。

### 动态代理实现

### 1、获取代理类方式一

1.JDK提供了 java.lang.reflect.Proxy类有一个getProxyClass(ClassLoader, interfaces)静态方法,传入类加载器,和接口,就可以得到代理类的Class对象.

2.得到了代理类的class对象,通过代理类的class对象得到构造器,java.lang.reflect.InvocationHandler类中,每一个动态代理类都要实现InvocationHandler接口,动态代理对象调用一个方法时,就会转到实现InvocationHandler接口类的invoke方法.

3.得到代理类,实行调用.

	public class ProxyTest {
	
		public static void main(String[] args) throws Exception {
			//目标对象
			IBankCardService bankCard=new BankCardServiceImpl();
			//获取代理对象
			IBankCardService proxyBank = (IBankCardService) getProxy(bankCard);
			//调用方法
			proxyBank.getMoney("9527");
		}
		
		/**
		 * 获取代理类
		 * @param target 目标类
		 * @return
		 * @throws SecurityException 
		 * @throws NoSuchMethodException 
		 */
		private static Object getProxy(Object target) throws Exception {
			//得到代理类大class
			Class proxyClass = Proxy.getProxyClass(target.getClass().getClassLoader(), 	target.getClass().getInterfaces());
			//创建代理类的构造函数,构造函数的方法必须传入InvocationHandler接口的实现类
			Constructor constructor=proxyClass.getConstructor(InvocationHandler.class);
			//获取代理类
			Object proxy =constructor.newInstance(new InvocationHandler() {
				
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					//调用目标文件的方法
					Object resulet = method.invoke(target,args);
					//增强方法
					System.out.println("向客户发送短信");
					return resulet;
				}
				
			});
			return proxy;
		}
	}
	
	打印结果:
		查询银行卡账号为:9527的账户 的余额
		向客户发送短信
	
### 2、获取代理类方式二

实际变成中不会使用getProxyClass(),因为JDK的Proxy类提供了更好用的方法newProxyInstance(ClassLoader loader,   Class<?>[] interfaces,InvocationHandler h),直接传入InvocationHandler 实现类就可以的到代理类.

1.代理类的调用处理程序实现

	/**
	 * 发送短信调用类
	 * @author yizl
	 *
	 */
	public class SendMessageInvocation implements InvocationHandler {
		/**
		 * 目标类	
		 */
		private Object obj;
		
		/**
		 * 通过构造方法传参
		 * @param obj
		 */
		public SendMessageInvocation(Object obj) {
			this.obj=obj;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			//调用目标文件的方法
			Object resulet = method.invoke(obj,args);
			//增强方法
			System.out.println("向客户发送短信");
			return resulet;
	
		}
	}

2.获取代理类，调用取钱方法

	public class ProxyTest {
		public static void main(String[] args) throws Exception {
			// 获取银行卡操作实现类
			IBankCardService bankCard = new BankCardServiceImpl();
			// 获取银行卡操作类的代理类
			IBankCardService proxyBank = (IBankCardService)Proxy.newProxyInstance(bankCard.getClass().getClassLoader(),
			bankCard.getClass().getInterfaces(),new SendMessageInvocation(bankCard));
			proxyBank.outMoney("9527");
		}
	}
	
	打印结果:
		向银行卡账号为:9527的账户取钱
		向客户发送短信
	
用JDK提供的代理类，很完美的解决了，不写代理类，直接写增强方法，直接就获取到目标的代理类。

## 三、动态代理的应用

​	设计模式中有一个设计原则是开闭原则：软件中对于扩展是开放的，对于修改是封闭的。再不改变源码的情况下，拓展它的行为。

​	工作中接收了很多以前的代码，里面的逻辑让人摸不透，就可以使用代理类进行增强。

​	Spring的AOP就是Java的动态代理来实现的切面编程。

​	RPC框架，框架本身不知道要调用哪些接口，哪些方法。这是框架可以一个创建代理类给客户端使用。

​	实际开发中的，通用异常处理，通用日志处理，事物处理都可以用到动态代理。

## 四、总结

优点：

​	动态代理类简化了代码编程工作，提高了软件的可扩展性。

缺点：

​	JDK动态代理只能代理有接口的实现类，没有接口的类就不能用JDK的动态代理。（Cglib动态代理可以对没有接口的类实现代理）