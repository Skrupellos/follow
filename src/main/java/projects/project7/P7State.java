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

import java.util.Iterator;
import java.util.Set;

import nfa.State;

public class P7State implements State {
	private boolean isFinal = false;
	private boolean isStart = false;
	/*package*/ Set<State> states;
	
	/*package*/ final P7Transitions transitions = new P7Transitions();

	/*package*/ void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	/*package*/ Iterator<State> iterator() {
		return states.iterator();
	}
	
	@Override
	public boolean isFinal() {
		return isFinal;
	}

	/*package*/ void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	
	@Override
	public boolean isStart() {
		return isStart;
	}
}
