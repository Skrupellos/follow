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

package com.github.skrupellos.follow.regex;

import java.util.Arrays;
import java.util.List;


public class RegexUnion<T> extends RegexIntNode<T> {
	// Only children
	public RegexUnion(RegexNode<T> left, RegexNode<T> right) {
		super(null, Arrays.asList(left, right));
	}
	
	public RegexUnion(List<RegexNode<T>> subs) {
		super(subs);
	}
	
	// Parent & children
	public RegexUnion(RegexNode<T> left, RegexNode<T> right, RegexIntNode<T> parent) {
		super(parent, Arrays.asList(left, right));
	}
	
	public RegexUnion(List<RegexNode<T>> subs, RegexIntNode<T> parent) {
		super(parent, subs);
	}
	
	@Override
	public String toString() {
		return "+";
	}
	
	public RegexNode<T> left() {
		return getChild(0);
	}
	
	public RegexNode<T> right() {
		return getChild(1);
	}
	
	public RegexNode<T> replaceLeft(RegexNode<T> sub) {
		return replaceChild(0, sub);
	}
	
	public RegexNode<T> replaceRight(RegexNode<T> sub) {
		return replaceChild(1, sub);
	}
	
	@Override
	protected void invariant(List<RegexNode<T>> newChildren) {
		if(newChildren.size() != 2) {
			throw new IllegalArgumentException("RegexUnion must have exactly 2 children");
		}
	}
	
	public RegexNode<T> deepCopy() {
		return new RegexUnion<T>(left().deepCopy(), right().deepCopy());
	}
	
	
	public RegexNode<T> accept(RegexVisitor<T> visitor) {
		visitor.pre(this);
		left().accept(visitor);
		visitor.inter(this);
		right().accept(visitor);
		visitor.post(this);
		return this;
	}
}
