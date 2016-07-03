package projects;

import nfa.TransitionTable;
import regex.RegularExpression;
/**
 *  to be implemented in projects 4-7 
 */
public interface NFAGenerator {
	/**
	 * takes the parsetree of the RegularExpression parser and
	 * generates an NFA in the form of a transition table
	 * according to the approach relevant for the particular subproject 
	 */
	TransitionTable nfaFromRegex(RegularExpression regex);
}
