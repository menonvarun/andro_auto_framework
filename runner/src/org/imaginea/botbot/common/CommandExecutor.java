package org.imaginea.botbot.common;

import java.util.ArrayList;
import java.util.List;

import org.imaginea.botbot.keywords.BaseKeywordDefinitions;
import org.imaginea.botbot.keywords.NativeDriverKeywordDefinitions;

public class CommandExecutor {
private final List<BaseKeywordDefinitions> definitions= new ArrayList<BaseKeywordDefinitions>();

public CommandExecutor(Prefrences prefrences){
	definitions.add(new NativeDriverKeywordDefinitions(prefrences));
}

}
