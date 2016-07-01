package projects.project7;

import nfa.TransitionTable;
import projects.NFAGenerator;
import regex.RegularExpression;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.nfa.Nfa;
import com.github.skrupellos.follow.Follow;


public class Project7 implements NFAGenerator {
	public TransitionTable nfaFromRegex(RegularExpression regex) {
		return ExportNfa.apply(Follow.<String>apply(ImportRegex.apply(regex)));
	}
}
