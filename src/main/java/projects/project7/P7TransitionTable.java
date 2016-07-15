package projects.project7;

import java.util.Iterator;

import nfa.State;
import nfa.TransitionTable;
import nfa.Transitions;

public class P7TransitionTable implements TransitionTable {
	public P7State start;
	
	public Transitions getTransitionsFor(State s) {
		if(s instanceof P7State) {
			return ((P7State)s).transitions;
		}
		else {
			return null;
		}
	}

	
	@Override
	public State getStart() {
		return start;
	}


	@Override
	public Iterator<State> iterator() {
		return start.iterator();
	}
}
