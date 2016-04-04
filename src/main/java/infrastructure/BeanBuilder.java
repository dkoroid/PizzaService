package infrastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class BeanBuilder {
	private Class<?> clazz;
	private Object bean;
	private Object beanProxy;
	private boolean isProxyPresent;
	private ApplicationContext applicationContext;

	public BeanBuilder(Class<?> clazz, ApplicationContext applicationContext) {
		this.clazz = clazz;
		this.applicationContext = applicationContext;
	}

	Object build() {
		Object toReturn;
		if (isProxyPresent) {
			toReturn = beanProxy;
		} else {
			toReturn = bean;
		}
		return toReturn;
	}

	void callInitMethod() throws Exception {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if ("init".equals(method.getName())) {
				method.invoke(bean);
			}
		}
	}

	void callPostCreateMethod() throws Exception {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(PostConstruction.class) != null) {
				method.invoke(bean);
			}
		}
	}

	void createBeanProxy() {
		Method[] methods = clazz.getMethods();
		boolean isProxyNeeded = false;
		for (Method method : methods) {
			if (method.getAnnotation(Benchmark.class) != null) {
				isProxyNeeded = true;
				break;
			}
		}

		if (!isProxyNeeded) {
			return;
		}
		isProxyPresent = true;
		ClassLoader loader = clazz.getClassLoader();
		Class<?>[] interfaces = clazz.getInterfaces();
		InvocationHandler h = new DynamicInvocationHandler(bean);
		Object proxyInstance = Proxy.newProxyInstance(loader, interfaces, h);
		beanProxy = proxyInstance;
	}

	static class DynamicInvocationHandler implements InvocationHandler {

		private Object obj;

		public DynamicInvocationHandler(Object obj) {
			this.obj = obj;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Benchmark benchmark = method.getAnnotation(Benchmark.class);
			if (benchmark == null || !benchmark.active()) {
				return method.invoke(obj, args);
			}
			long startTime = System.nanoTime();
			Object object = method.invoke(obj, args);
			long endTime = System.nanoTime();
			long totalTime = endTime - startTime;
			System.out.println("Invoked " + method.getName() + " on " + obj.getClass().getSimpleName() + " with args: "
					+ Arrays.toString(args) + ". Total time: " + totalTime);
			return object;
		}

	}

	void createBean() throws InstantiationException, IllegalAccessException, Exception, InvocationTargetException {

		Constructor<?> constructor = clazz.getConstructors()[0];
		Class<?>[] paramTypes = constructor.getParameterTypes();
		if (paramTypes.length == 0) {
			bean = clazz.newInstance();
		} else {
			bean = createNewInstanceWithParams(constructor, paramTypes);
		}
	}

	private Object createNewInstanceWithParams(Constructor<?> constructor, Class<?>[] paramTypes)
			throws Exception, InstantiationException, IllegalAccessException, InvocationTargetException {
		Object bean;
		Object[] paramBeans = new Object[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++) {
			paramBeans[i] = getBeanByType(paramTypes[i]);
		}
		bean = constructor.newInstance(paramBeans);
		return bean;
	}

	private Object getBeanByType(Class<?> paramType) throws Exception {
		String beanNameByType = getBeanNameByType(paramType);
		Object bean = applicationContext.getBean(beanNameByType);
		return bean;
	}

	private String getBeanNameByType(Class<?> paramType) {
		String paramTypeName = paramType.getSimpleName();
		String paramName = changeFirstLetterToLowercase(paramTypeName);
		return paramName;
	}

	private String changeFirstLetterToLowercase(String paramTypeName) {
		return Character.toLowerCase(paramTypeName.charAt(0)) + paramTypeName.substring(1);
	}
}