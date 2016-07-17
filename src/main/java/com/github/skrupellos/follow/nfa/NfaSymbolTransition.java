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

import com.github.skrupellos.follow.graph.GraphArrow;

public class NfaSymbolTransition<T> extends NfaTransition<T> {
	private T symbol;
	
	
	public NfaSymbolTransition(T symbol, NfaState<T> tail, NfaState<T> head) {
		super(tail, head);
		setSymbol(symbol);
	}
	
	
	public NfaSymbolTransition(T symbol, NfaState<T> state) {
		super(state);
		setSymbol(symbol);
	}
	
	
	public T symbol() {
		return symbol;
	}
	
	
	public NfaSymbolTransition<T> setSymbol(T symbol) {
		this.symbol = symbol;
		return this;
	}
	
	
	@Override
	public String toString() {
		return symbol.toString();
	}
	
	
	public NfaTransition<T> accept(NfaVisitor<T> visitor) {
		visitor.visitTransition(this);
		return this;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equalContents(GraphArrow other) {
		return
			super.equalContents(other) &&
			other instanceof NfaSymbolTransition &&
			symbol.equals( ((NfaSymbolTransition)other).symbol );
	}
}
