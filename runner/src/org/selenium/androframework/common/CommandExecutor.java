package org.selenium.androframework.common;

import java.util.ArrayList;
import java.util.List;

import org.selenium.androframework.keywords.BaseKeywordDefinitions;
import org.selenium.androframework.keywords.NativeDriverKeywordDefinitions;

public class CommandExecutor {
private final List<BaseKeywordDefinitions> definitions= new ArrayList<BaseKeywordDefinitions>();

public CommandExecutor(Prefrences prefrences){
	definitions.add(new NativeDriverKeywordDefinitions(prefrences));
}

}
