package projects.project7;

import java.util.Collection;
import java.util.HashSet;

import com.github.skrupellos.follow.nfa.NfaTransition;
import com.github.skrupellos.follow.follow.MapHelper;
import com.github.skrupellos.follow.nfa.NfaState;
import com.github.skrupellos.follow.nfa.NfaSymbolTransition;
import com.github.skrupellos.follow.nfa.NfaVisitor;


public class ExportNfa extends MapHelper<NfaState<String>, P7State> implements NfaVisitor<String> {
	private P7TransitionTable transitionTable = new P7TransitionTable();
	
	
	public static P7TransitionTable apply(NfaState<String> start) {
		return (new ExportNfa(start)).result();
	}
	
	
	public P7TransitionTable result() {
		return transitionTable;
	}
	
	
	public ExportNfa(NfaState<String> start) {
		Collection<NfaState<String>> states = start.reachable();
		for(NfaState<String> state : states) {
			state.accept(this);
		}
		
		for(NfaState<String> state : states) {
			for(NfaTransition<String> arrow : state.tails()) {
				arrow.accept(this);
			}
		}
		
		transitionTable.start = lookup(start);
		transitionTable.start.setStart(true);
		transitionTable.start.states = new HashSet<>(getValues());
	}
	
	
	public void visitTransition(NfaSymbolTransition<String> transition) {
		P7State tail = lookup(transition.tail());
		P7State head = lookup(transition.head());
		tail.transitions.add(transition.symbol(), head);
	}
	
	
	public void visitState(NfaState<String> state) {
		P7State p7State = new P7State();
		if(state.isFinal) {
			p7State.setFinal(true);
		}
		define(state, p7State);
	}
	
	
	public void visitTransitionAll(NfaTransition<String> transition) {
		throw new RuntimeException("Forgot how to " + transition);
	}
}
