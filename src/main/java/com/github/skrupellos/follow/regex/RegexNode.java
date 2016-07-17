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

import java.util.List;

import com.github.skrupellos.follow.tree.TreeNode;


public abstract class RegexNode<T> extends TreeNode<RegexNode<T>> {
	// Nothing
	public RegexNode() {
		super();
	}
	
	
	// Only parent
	public RegexNode(RegexIntNode<T> parent) {
		super(parent);
	}
	
	
	// Only children
	public RegexNode(List<RegexNode<T>> children) {
		super(children);
	}
	
	
	// Parent & children
	public RegexNode(RegexIntNode<T> parent, List<RegexNode<T>> children) {
		super(parent, children);
	}
	
	
	public abstract RegexNode<T> deepCopy();
	
	
	public abstract RegexNode<T> accept(RegexVisitor<T> visitor);
	
	
	protected RegexNode<T> uncheckedSelf() {
		return this;
	}
}
