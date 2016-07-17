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

package com.github.skrupellos.follow;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nfa.State;
import nfa.TransitionTable;
import nfa.Transitions;
import projects.project7.P7Transitions;
import projects.project7.Project7;
import regex.AlternationExpression;
import regex.Char;
import regex.ConcatenationExpression;
import regex.KleeneStarExpression;
import regex.RegularExpression;

public class Project7Spec {
	
	private final static Char char_1 = new Char() {
		@SuppressFBWarnings(value="DM_STRING_CTOR")
		@Override
		public String getCharacter() {
			return new String("a");
		}
	};
	
	private final static Char char_2 = new Char() {
		@SuppressFBWarnings(value="DM_STRING_CTOR")
		@Override
		public String getCharacter() {
			return new String("b");
		}
	};
	
	private final static Char char_3 = new Char() {
		@SuppressFBWarnings(value="DM_STRING_CTOR")
		@Override
		public String getCharacter() {
			return new String("b");
		}
	};
	
	private final static Char char_4 = new Char() {
		@SuppressFBWarnings(value="DM_STRING_CTOR")
		@Override
		public String getCharacter() {
			return new String("b");
		}
	};
	
	private final static Char char_5 = new Char() {
		@SuppressFBWarnings(value="DM_STRING_CTOR")
		@Override
		public String getCharacter() {
			return new String("a");
		}
	};
	
	private final static Char char_6 = new Char() {
		@SuppressFBWarnings(value="DM_STRING_CTOR")
		@Override
		public String getCharacter() {
			return new String("a");
		}
	};
	
	private final static KleeneStarExpression kleene_1 = new KleeneStarExpression() {
		@Override
		public RegularExpression getChild() {
			return char_5;
		}
	};
	
	private final static KleeneStarExpression kleene_2 = new KleeneStarExpression() {
		@Override
		public RegularExpression getChild() {
			return char_4;
		}
	};
	
	private final static KleeneStarExpression kleene_3 = new KleeneStarExpression() {
		@Override
		public RegularExpression getChild() {
			return char_6;
		}
	};
	
	private final static ConcatenationExpression catenation_1 = new ConcatenationExpression() {
		@Override
		public RegularExpression getLHS() {
			return char_3;
		}
		
		@Override
		public RegularExpression getRHS() {
			return kleene_3;
		}
	};
	
	private final static AlternationExpression alter_1 = new AlternationExpression() {
		@Override
		public RegularExpression getLHS() {
			return char_1; 
		}

		@Override
		public RegularExpression getRHS() {
			return char_2;
		}
	};
	
	private final static AlternationExpression alter_2 = new AlternationExpression() {
		@Override
		public RegularExpression getLHS() {
			return kleene_1;
		}
		
		@Override
		public RegularExpression getRHS() {
			return catenation_1;
		}
	};
	
	private final static AlternationExpression alter_3 = new AlternationExpression() {
		@Override
		public RegularExpression getLHS() {
			return alter_2;
		}
		
		@Override
		public RegularExpression getRHS() {
			return kleene_2;
		}
	};
	
	private final static KleeneStarExpression kleene_4 = new KleeneStarExpression() {
		@Override
		public RegularExpression getChild() {
			return alter_3;
		}
	};
	
	private RegularExpression getTeta() {
		return new ConcatenationExpression() {
			@Override
			public RegularExpression getLHS() {
				return alter_1;
			}

			@Override
			public RegularExpression getRHS() {
				return kleene_4;
			}
		};
	}
	
	@Test
	public void runFullAlgorithm() {
		TransitionTable transitionTable = (new Project7()).nfaFromRegex(getTeta());
		State initial = transitionTable.getStart();
		
		assertTrue(initial.isStart());
		assertFalse(initial.isFinal());
		
		Transitions transitions = transitionTable.getTransitionsFor(initial);
		// from initial state both transitions go to the same state
		assertEquals(transitions.getStateForCharacter("a"), transitions.getStateForCharacter("b"));
		assertEquals(null, transitions.getStateForCharacter("ε"));
		assertEquals(null, transitions.getStateForCharacter("t"));
		
		initial = transitions.getStateForCharacter("a");
		transitions = transitionTable.getTransitionsFor(initial);
		assertFalse(initial.isStart());
		assertTrue(initial.isFinal());
		assertEquals(null, transitions.getStateForCharacter("ε"));
		assertEquals(null, transitions.getStateForCharacter("t"));
		
		assertEquals(initial, transitions.getStateForCharacter("a"));
		List<State> states = ((P7Transitions) transitions).getStatesForCharacter("b");
		assertTrue(states.get(0).equals(initial) || states.get(1).equals(initial));
		for(State s : states) {
			if(!s.equals(initial)) {
				assertTrue(s.isFinal());
				assertFalse(s.isStart());
			}
		}
	}
}
