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

package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphNode;


public class NfaState<T> extends GraphNode<NfaState<T>, NfaTransition<T>> {
	public boolean isFinal = false;
	
	
	public NfaState() {
		this(false);
	}
	
	
	public NfaState(boolean isFinal) {
		super();
		this.isFinal = isFinal;
	}
	
	
	public NfaState(Iterable<NfaTransition<T>> tails, Iterable<NfaTransition<T>> heads) {
		this(tails, heads, false);
	}
	
	
	public NfaState(Iterable<NfaTransition<T>> tails, Iterable<NfaTransition<T>> heads, boolean isFinal) {
		super(tails, heads);
		this.isFinal = isFinal;
	}
	
	
	protected NfaState<T> uncheckedSelf() {
		return this;
	}
	
	
	public NfaState<T> accept(NfaVisitor<T> visitor) {
		visitor.visitState(this);
		return this;
	}
}
