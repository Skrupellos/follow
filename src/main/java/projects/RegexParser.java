package projects;

import regex.RegularExpression;

/**
 * to be implemented in projects 1-3 
 */

public interface RegexParser {
	/**
	 * takes a String and generates the syntax tree for a regular expression
	 * with the operators | + * ? as well as implicit concatenation and ()
	 */
	RegularExpression parse(String s);
}
