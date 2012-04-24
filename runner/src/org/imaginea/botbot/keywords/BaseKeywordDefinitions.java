package org.imaginea.botbot.keywords;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.imaginea.botbot.common.Command;

public abstract class BaseKeywordDefinitions {
	protected HashMap<String, Integer> methodMap = new HashMap<String, Integer>();

	abstract public boolean methodSUpported(Command command);

	protected void collectSupportedMethods(Class c) {
		Iterator<Method> it = Arrays.asList(c.getMethods()).iterator();
		while (it.hasNext()) {
			Method method = it.next();
			if (isSupported(method)) {
				Integer noOfParam = new Integer(
						method.getParameterTypes().length);
				methodMap.put(method.getName(), noOfParam);
			}
		}
		System.out.println(methodMap);
	}

	private boolean isSupported(Method method) {
		Class<?>[] params = method.getParameterTypes();
		int paramLength = params.length;
		if (paramLength == 0)
			return true;
		if (paramLength == 1
				&& (params[0].isAssignableFrom(String.class) || params[0]
						.isAssignableFrom(int.class)))
			return true;
		if (paramLength == 2
				&& (params[0].isAssignableFrom(String.class) && params[1]
						.isAssignableFrom(String.class)))
			return true;

		return false;
	}

	protected void invoker(Object obj, String methodName, List<String> s) {
		
		try {
			if (s.size() == 0) {
				Method method = obj.getClass().getMethod(methodName,
						new Class[] {});
				method.invoke(obj, new Object[] {});

			} else if (s.size() == 1) {
				int tempVal = 0;
				boolean isInteger = false;
				try {
					tempVal = Integer.parseInt(s.get(0));
					isInteger = true;
				} catch (NumberFormatException e) {
					//Escaping as we wanted to check if the variable is integer or not
				}
				if (isInteger) {
					Method method = obj.getClass().getMethod(methodName,
							int.class);
					method.invoke(obj, tempVal);
				} else {
					Method method = obj.getClass().getMethod(methodName,
							String.class);
					method.invoke(obj, s.get(0));
				}
			} else if (s.size() == 2) {
				Method method = obj.getClass().getMethod(methodName,
						String.class, String.class);
				method.invoke(obj, s.get(0), s.get(1));
				
			} else if(s.size()>2){
				Method method = obj.getClass().getMethod(methodName,
						String.class, String.class, String.class);
				method.invoke(obj, s.get(0), s.get(1),s.get(2));
			}
		} catch (NoSuchMethodException e) {
			Assert.fail("nosuch method exception thrown: " + e);
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				InvocationTargetException invocationTargetException = (InvocationTargetException) e;
				Throwable targetException = invocationTargetException
						.getTargetException();
				if (targetException instanceof AssertionFailedError) {
					Assert.fail(targetException.getMessage());
				} else {
					Assert.fail(targetException.getMessage());
				}
			} else {
				Assert.fail(e.toString());
			}
		}

	}

	abstract public void execute(Command command);

}
