package projects;

import java.util.List;

/**
 * parse() to be implemented in project 8
 * getNFAGenerator and getRegexParser can be assumed to be given by Teams 1-3 or 4-7 respectively 
 */
public interface Flex {
	default NFAGenerator getNFAGenerator() { return null; }
	default RegexParser getRegexParser() { return null; }
	/**
	 * takes a List of Regular Expressions as Strings and returns the index
	 * of the foremost expression in the list, which matched the 
	 * longest prefix of the input
	 */
	int parse(List<String> regexList,String input);
}
