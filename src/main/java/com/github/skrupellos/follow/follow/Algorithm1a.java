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
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexExtNode;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexVisitor;




public class Algorithm1a<T> extends AlgorithmBase<RegexNode<T>, RegexNode<T>> implements RegexVisitor<T> {
	public static <T> RegexNode<T> apply(RegexNode<T> root) {
		return (new Algorithm1a<T>(root)).result();
	}
	
	
	public Algorithm1a(RegexNode<T> root) {
		super(root);
		root.accept(this);
	}
	
	
	public void post(RegexCatenation<T> regex) {
		RegexNode<T> left  = lookup(regex.left());
		RegexNode<T> right = lookup(regex.right());
		
		if(left.getClass().equals(RegexEmptySet.class) || right.getClass().equals(RegexEmptySet.class)) {
			define(regex, new RegexEmptySet<T>());
		}
		else {
			define(regex, new RegexCatenation<T>(left, right));
		}
	}
	
	
	public void post(RegexStar<T> regex) {
		RegexNode<T> sub = lookup(regex.sub());
		define(regex, sub.getClass().equals(RegexEmptySet.class) ? sub : new RegexStar<T>(sub));
	}
	
	
	public void post(RegexUnion<T> regex) {
		RegexNode<T> left  = lookup(regex.left());
		RegexNode<T> right = lookup(regex.right());
		
		if(left.getClass().equals(RegexEmptySet.class)) {
			define(regex, right);
		}
		else if(right.getClass().equals(RegexEmptySet.class)) {
			define(regex, left);
		}
		else {
			define(regex, new RegexUnion<T>(left, right));
		}
	}
	
	
	public void postExt(RegexExtNode<T> regex) {
		define(regex, regex.deepCopy());
	}
}
