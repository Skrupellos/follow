package projects.project7;

import nfa.State;
import nfa.Transitions;
import nfa.TransitionTable;

public class P7TransitionTable implements TransitionTable {
	//public P7State start;
	
	
	public Transitions getTransitionsFor(State s) {
		if(s instanceof P7State) {
			return ((P7State)s).transitions;
		}
		else {
			return null;
		}
	}
}
