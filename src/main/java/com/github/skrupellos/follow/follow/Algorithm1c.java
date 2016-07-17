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

import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexExtNode;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexVisitor;




public class Algorithm1c<T> extends AlgorithmBase<RegexNode<T>, RegexNode<T>> implements RegexVisitor<T> {
	public static <T> RegexNode<T> apply(RegexNode<T> root) {
		return (new Algorithm1c<T>(root)).result();
	}
	
	
	public Algorithm1c(RegexNode<T> root) {
		super(root);
		root.accept(this);
	}
	
	
	public void post(RegexCatenation<T> regex) {
		define(regex, new RegexCatenation<T>(lookup(regex.left()), lookup(regex.right())));
	}
	
	
	public void post(RegexStar<T> regex) {
		RegexNode<T> sub = lookup(regex.sub());
		define(regex, sub.getClass().equals(RegexStar.class) ? sub : new RegexStar<T>(sub));
	}
	
	
	public void post(RegexUnion<T> regex) {
		define(regex, new RegexUnion<T>(lookup(regex.left()), lookup(regex.right())));
	}
	
	
	public void postExt(RegexExtNode<T> regex) {
		define(regex, regex.deepCopy());
	}
}
