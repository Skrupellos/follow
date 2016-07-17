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

import java.util.LinkedList;
import java.util.List;

import com.github.skrupellos.follow.follow.MapListHelper;

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
