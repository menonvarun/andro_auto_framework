package org.imaginea.botbot.keywords;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.imaginea.botbot.common.Command;

public abstract class BaseKeywordDefinitions {
	/*
	 * This HaspMap maps keyword name to another hashmap which has no of
	 * parameters as key and methods with same parameter length in arraylist.
	 * Purpose of this hasmap is to support dynamic mapping of keyword based on
	 * paramter types and parameter length to matching method .
	 */ 
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
		HashMap<Integer, ArrayList<Method>> methodHashMap;
		ArrayList<Method> methodList;

		if (methodMap.containsKey(methodName)) {
			methodHashMap = methodMap.get(methodName);
			int lengthParam = method.getParameterAnnotations().length;
			if (methodHashMap.containsKey(lengthParam)) {
				methodList = methodHashMap.get(lengthParam);
				methodList.add(method);
				methodHashMap.put(lengthParam, methodList);
			} else {
				methodList = new ArrayList<Method>();
				methodList.add(method);
				methodHashMap.put(lengthParam, methodList);
			}
		} else {
			methodHashMap = new HashMap<Integer, ArrayList<Method>>();
			methodList = new ArrayList<Method>();
			methodList.add(method);
			methodHashMap.put(method.getParameterTypes().length, methodList);
			methodMap.put(methodName, methodHashMap);

		}

	}


	/*
	 * It checks for method for the supplied keyword. Also selects method with
	 * parameter length. It calls methodInvoker which invokes the method
	 */
	protected void invoker(Object obj, String methodName,
			List<String> parameters) {

		boolean parameterLengthCheck = false;
		int paramLength = 0;
		//Getting a sorted set
		TreeSet<Integer> paramCountMethodSet = new TreeSet<Integer>(methodMap.get(methodName).keySet());
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

	/*
	 * Method invoker chooses a method from array list by checking compatibility
	 * of passed parameters with required parameter type by method. It loops
	 * through methods of same parameter length, checks if supplied parameters
	 * can be type casted to required parameter types.Once all parameters of a
	 * method are compatible,parameters are typecasted and that method is invoked
	 * and looping is broken. Other wise loop continues, if no parameter is
	 * compatible then assert statement is executed and test case is failed.
	 */
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
					String paramType=methodParams[count].getSimpleName();
					Object parameter=typecastOfParameter(parameters.get(count),paramType);
					// Checking type cast of string to required parameter type
					if ( parameter== null) 
					{
						actualParamType = parameters.get(count);
						expectedParamType = methodParams[count].getSimpleName();
						canBeexecuted = false;
						break;
					}
					paramObjectArray[count] = parameter;
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
				+ expectedParamType + "..Value passed is:" + actualParamType,canBeexecuted);
	}

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

}
