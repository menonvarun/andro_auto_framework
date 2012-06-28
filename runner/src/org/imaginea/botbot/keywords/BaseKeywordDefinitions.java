package org.imaginea.botbot.keywords;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.imaginea.botbot.common.Command;

public abstract class BaseKeywordDefinitions {
	
    protected HashMap<String, ArrayList<MethodMapper>> mMapper= new HashMap<String, ArrayList<MethodMapper>>();
    protected HashMap<String, HashMap<Integer,ArrayList<Method>>> expMap=new HashMap<String, HashMap<Integer,ArrayList<Method>>>();

    abstract public boolean methodSUpported(Command command);
    
    //Creates Hashmap of keywords as key and arraylist of methods with keyword
    protected void collectSupportedMethods(Class c) {
        Iterator<Method> it = Arrays.asList(c.getMethods()).iterator();
        while (it.hasNext()) {
            Method method = it.next();
            addToMethodMapper(method);
            
        }
    }
    
    //Adds methods to the hashmap
    private void addToMethodMapper(Method method){
        String methodName= method.getName();
        HashMap<Integer,ArrayList<Method>> tempHashMap;
        
        ArrayList<MethodMapper> mList;
        if(mMapper.containsKey(methodName)){
            mList= mMapper.get(methodName);
        }else{
            mList= new ArrayList<MethodMapper>();
        }
        MethodMapper mapper= new MethodMapper();
        mapper.setmObject(method);
        mList.add(mapper);
        mMapper.put(methodName, mList);
        
        
        
        ArrayList<Method> expMethodList;
        if(expMap.containsKey(methodName))
        {
        	tempHashMap=expMap.get(methodName);
        	int lenghtParam=method.getParameterAnnotations().length;
        	if(tempHashMap.containsKey(lenghtParam))
        	{
        		expMethodList=tempHashMap.get(lenghtParam);
        		expMethodList.add(method);
        		tempHashMap.put(lenghtParam,expMethodList);
        	}
        	else
        	{
        		expMethodList=new ArrayList<Method>();
        		expMethodList.add(method);
        		tempHashMap.put(lenghtParam,expMethodList);
        	}
        }
        else
        {
        	tempHashMap=new HashMap<Integer, ArrayList<Method>>();
        	expMethodList=new ArrayList<Method>();
        	expMethodList.add(method);
        	tempHashMap.put(method.getParameterTypes().length, expMethodList);
        	expMap.put(methodName, tempHashMap);
        	
        }
        
    }

   
    //Invokes method by converting parameters into required types.
    //And also checks for number of parameters and type of parameters
	protected void invoker(Object obj, String methodName, List<String> parameters) {
		
		try {	
			boolean methodExist=false;
			boolean parameterLengthCheck=false;
			boolean parameterTypeCheck=true;
			Method methodToBeExecuted=null;
			int paramLength=0;
			
			ArrayList<MethodMapper> mappers=mMapper.get(methodName); 
			
			//Selection of method to be executed based on number of parameter. 
			for(MethodMapper mapper:mappers){
	            if(mapper.getLength()==parameters.size()){
	            	methodToBeExecuted=mapper.getmObject();
	            	parameterLengthCheck = true;
	            	paramLength=mapper.pLength;
	                break;
	            }else if(mapper.getLength()<parameters.size()){
	            	methodToBeExecuted=mapper.getmObject();
	            	parameterLengthCheck = true;
	            	paramLength=mapper.pLength;	            	
	            }
	        } 
			
			/*
			int multipleSameParameterLength=0;
			for(MethodMapper mapper:mappers){
				if(mapper.pLength==paramLength)
					multipleSameParameterLength++;
			}
			
			if(multipleSameParameterLength>1)
			{
					
			}*/
			
			
			Assert.assertTrue(methodName
					+ " number of parameters required are not present ",
					parameterLengthCheck);
			
			
			Class<?>[] methodToBeExecutedParams = methodToBeExecuted
					.getParameterTypes();
			Object[] objectArray = new Object[methodToBeExecutedParams.length];
			for (int count = 0; count < methodToBeExecutedParams.length; count++) {
				//Asserting type cast of string to required parameter type
				if(typecastOfParameter(parameters.get(count),
						methodToBeExecutedParams[count].getSimpleName())==null)
				{
					parameterTypeCheck=false;
					break;
				}
				//Assert.assertNotNull(
				//		"Paramater type not matching, expected:"
				//				+ methodToBeExecutedParams[count]
				//						.getSimpleName() + " in command "
				//				+ methodName,
				//		typecastOfParameter(parameters.get(count),
				//				methodToBeExecutedParams[count].getSimpleName()));
				
				
				objectArray[count] = typecastOfParameter(parameters.get(count),
						methodToBeExecutedParams[count].getSimpleName());

			}
			if(!parameterTypeCheck)
			{
				HashMap<Integer,ArrayList<Method>> methodHash=expMap.get(methodName);
				ArrayList<Method> methods=methodHash.get(methodToBeExecutedParams.length);
				
			}
			methodToBeExecuted.invoke(obj, objectArray);			
		} 
		
		catch (Exception e) {
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
				Assert.fail("Excpetion: "+e.toString());
			}
		}

	}

	abstract public void execute(Command command);
	
	//Typecasts Srting input to the mentioned parameter type 
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

	}
	
	//Method holder 
	private class MethodMapper{
        private Method mObject;
        private int pLength;
        private ArrayList<Class<?>> mClass = new ArrayList<Class<?>>();

        public Method getmObject() {
            return mObject;
        }
        public void setmObject(Method mObject) {
            this.mObject = mObject;
            mClass=new ArrayList<Class<?>>(Arrays.asList(mObject.getParameterTypes()));
            pLength=mClass.size();
        }

        public ArrayList<Class<?>> getParams(){
            return mClass;
        }

        public int getLength(){
            return pLength;
        }

    } 
	
	Method methodSelector(Method inputMethod,List<String> parameters)
	{
		
	}

}
