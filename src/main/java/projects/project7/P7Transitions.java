package projects.project7;

import java.util.LinkedList;
import java.util.List;

import com.github.skrupellos.follow.MapListHelper;

import nfa.State;
import nfa.Transitions;

public class P7Transitions extends MapListHelper<String, P7State> implements Transitions {
	/*package*/ P7Transitions add(String character, P7State state) {
		define(character, state);
		return this;
	}
	
	
	public State getStateForCharacter(String s) {
		List<P7State> states = lookup(s);
		if(states != null) {
			return states.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * You will need this to access arbitrary transitions on states which
	 * may proceed on different paths with the same letter (NFA).
	 */
	public List<State> getStatesForCharacter(String s) {
		List<State> states = new LinkedList<>();
		states.addAll(lookup(s));
		return states;
	}
}
