/* This file is part of Follow (https://github.com/Skrupellos/follow).
 * Copyright (c) 2016 Skruppy <skruppy@onmars.eu> and kratl.
 *
 * Follow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Follow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Follow. If not, see <http://www.gnu.org/licenses/>.
 */

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
