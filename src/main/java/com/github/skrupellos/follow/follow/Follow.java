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

package com.github.skrupellos.follow.follow;

import com.github.skrupellos.follow.nfa.NfaState;
import com.github.skrupellos.follow.regex.RegexNode;




public final class Follow<T>  {
	private Follow() {}
	
	
	public static <T> NfaState<T> apply(RegexNode<T> root) {
		NfaState<T> start;
		
		root  = Algorithm1a.<T>apply(root);
		root  = Algorithm1b.<T>apply(root).tree;
		root  = Algorithm1c.<T>apply(root);
		start = Algorithm4 .<T>apply(root).start;
		        Algorithm20.<T>apply(start); // _Modifies_ start
		
		return start;
	}
}
