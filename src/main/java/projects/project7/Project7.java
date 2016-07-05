package projects.project7;

import com.github.skrupellos.follow.Follow;

import nfa.TransitionTable;
import projects.NFAGenerator;
import regex.RegularExpression;


public class Project7 implements NFAGenerator {
	public TransitionTable nfaFromRegex(RegularExpression regex) {
		return ExportNfa.apply(Follow.<String>apply(ImportRegex.apply(regex)));
	}
}
