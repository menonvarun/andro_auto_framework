package org.imaginea.botbot.keywords;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.imaginea.botbot.common.Command;

public abstract class BaseKeywordDefinitions {

	protected HashMap<String, HashMap<Integer, ArrayList<Method>>> methodMap = new HashMap<String, HashMap<Integer, ArrayList<Method>>>();
	
	abstract public boolean methodSUpported(Command command);

	// Creates Hashmap of keywords as key and arraylist of methods with keyword
	protected void collectSupportedMethods(Class c) {
		Iterator<Method> it = Arrays.asList(c.getMethods()).iterator();
		while (it.hasNext()) {
			Method method = it.next();
			addToMethodMapper(method);

		}
	}

	// Adds methods to the hashmap
	private void addToMethodMapper(Method method) {
		String methodName = method.getName();
		HashMap<Integer, ArrayList<Method>> tempHashMap;
		ArrayList<Method> tempMethodList;

		if (methodMap.containsKey(methodName)) {
			tempHashMap = methodMap.get(methodName);
			int lengthParam = method.getParameterAnnotations().length;
			if (tempHashMap.containsKey(lengthParam)) {
				tempMethodList = tempHashMap.get(lengthParam);
				tempMethodList.add(method);
				tempHashMap.put(lengthParam, tempMethodList);
			} else {
				tempMethodList = new ArrayList<Method>();
				tempMethodList.add(method);
				tempHashMap.put(lengthParam, tempMethodList);
			}
		} else {
			tempHashMap = new HashMap<Integer, ArrayList<Method>>();
			tempMethodList = new ArrayList<Method>();
			tempMethodList.add(method);
			tempHashMap.put(method.getParameterTypes().length, tempMethodList);
			methodMap.put(methodName, tempHashMap);

		}

	}

	// Invokes method by converting parameters into required types.
	// And also checks for number of parameters and type of parameters
	protected void invoker(Object obj, String methodName,
			List<String> parameters) {

		boolean parameterLengthCheck = false;
		int paramLength = 0;

		Set<Integer> paramCountMethodSet = methodMap.get(methodName).keySet();
		//Selecting number of parameters that should be considered
		if (paramCountMethodSet.contains(parameters.size())) {
			paramLength = parameters.size();
			parameterLengthCheck = true;
		} else {
			Iterator<Integer> iterator = paramCountMethodSet.iterator();
			while (iterator.hasNext()) {
				int tempParamLength = (Integer) iterator.next();
				if (tempParamLength < parameters.size()) {
					paramLength = tempParamLength;
					parameterLengthCheck = true;
				}
			}

		}

		Assert.assertTrue(methodName
				+ " number of parameters required are not present ",
				parameterLengthCheck);

		// Getting methods with required parameters length
		ArrayList<Method> methods = methodMap.get(methodName).get(paramLength);
		//Invoking suitable method with compatible parameters
		methodInvoker(obj, methods, parameters);

	}

	abstract public void execute(Command command);

	// Typecasts Srting input to the mentioned parameter type.
	// Returns null in case of incompatible types
	private Object typecastOfParameter(String parameter, String parameterType) {
		HashMap<String, Integer> parameterKeyValue = new HashMap<String, Integer>() {
			{
				put("int", 0);
				put("long", 1);
				put("String", 2);
				put("float", 3);
				put("boolean", 4);
			}
		};

		if (parameterKeyValue.get(parameterType) == null) {
			Assert.fail("Parameter type not supported : " + parameterType);
		}
		try {
			switch (parameterKeyValue.get(parameterType)) {

			case 0:
				return Integer.parseInt(parameter);
			case 1:
				return Long.parseLong(parameter);
			case 2:
				return parameter;
			case 3:
				return new Float(parameter);
			case 4:
				if (parameter.equalsIgnoreCase("true"))
					return true;
				else if (parameter.equalsIgnoreCase("false"))
					return false;
				else
					return null;
			default:
				return null;

			}
		} catch (Exception e) {
			return null;
		}

	}

	// selects the method based on compatibility of parameters and invokes it
	private void methodInvoker(Object obj, ArrayList<Method> methods,
			List<String> parameters) {

		boolean canBeexecuted = false;
		String actualParamType = "";
		String expectedParamType = "";
		try {
			for (Method method : methods) {
				canBeexecuted = true;
				Class<?>[] methodParams = method.getParameterTypes();
				Object[] paramObjectArray = new Object[methodParams.length];
				for (int count = 0; count < methodParams.length; count++) {
					// Checking type cast of string to required parameter type
					if (typecastOfParameter(parameters.get(count),
							methodParams[count].getSimpleName()) == null) 
					{
						actualParamType = parameters.get(count);
						expectedParamType = methodParams[count].getSimpleName();
						canBeexecuted = false;
						break;
					}
					paramObjectArray[count] = typecastOfParameter(
							parameters.get(count),
							methodParams[count].getSimpleName());
				}
				if (canBeexecuted) {
					method.invoke(obj, paramObjectArray);
					break;
				}

			}
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
				Assert.fail("Excpetion: " + e.toString());
			}
		}

		Assert.assertTrue("Parameter passed is incorrect Required-type:"
				+ expectedParamType + "..Value passed is:" + actualParamType, canBeexecuted);
	}

}
