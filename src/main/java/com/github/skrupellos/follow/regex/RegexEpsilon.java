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


public class RegexEpsilon<T> extends RegexExtNode<T> {
	// Nothing
	public RegexEpsilon() {
		super();
	}
	
	
	// Only parent
	public RegexEpsilon(RegexIntNode<T> parent) {
		super(parent);
	}
	
	
	@Override
	public String toString() {
		return "ε";
	}
	
	
	public RegexNode<T> deepCopy() {
		return new RegexEpsilon<T>();
	}
	
	
	public RegexNode<T> accept(RegexVisitor<T> visitor) {
		visitor.pre(this);
		visitor.post(this);
		return this;
	}
}
