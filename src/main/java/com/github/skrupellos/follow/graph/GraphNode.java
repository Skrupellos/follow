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

package com.github.skrupellos.follow.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;



public abstract class GraphNode<
	NODE  extends GraphNode<NODE, ARROW>,
	ARROW extends GraphArrow<NODE, ARROW>
> {
	private final NODE self = checkedSelf();
	
	private final GraphArrowSet<NODE, ARROW> tails = new GraphArrowSet<NODE, ARROW>(self) {
		public void connect(ARROW arrow, NODE node) {
			arrow.connectTailTo(node);
		}
	};
	
	private final GraphArrowSet<NODE, ARROW> heads = new GraphArrowSet<NODE, ARROW>(self) {
		public void connect(ARROW arrow, NODE node) {
			arrow.connectHeadTo(node);
		}
	};
	
	
	public GraphNode() { }
	
	
	public GraphNode(Iterable<ARROW> tails, Iterable<ARROW> heads) {
		this();
		this.tails.takeover(tails);
		this.heads.takeover(heads);
	}
	
	
	protected abstract NODE uncheckedSelf();
	
	
	private NODE checkedSelf() {
		NODE self = uncheckedSelf();
		assert(self == this);
		return self;
	}
	
	
	public final GraphArrowSet<NODE, ARROW> tails() {
		return tails;
	}
	
	
	public final GraphArrowSet<NODE, ARROW> heads() {
		return heads;
	}
	
	
	/**
	 * Removes all in- and outgoing arrows from this nodes and adds all, not
	 * already existing arrows, to the replacement node. At the end, this node
	 * has zero Arrows, the replacement node has more arrows. To be exact, the
	 * replacement node gains up to the number of arrows this node had before.
	 */
	public NODE replaceBy(NODE replacement) {
		return replacement.takeover(self);
	}
	
	
	/**
	 * Adds all in- and outgoing arrows from the other node, which don't
	 * duplicate any existing arrows. At the end the other node has zero
	 * Arrows.
	 */
	public NODE takeover(NODE node) {
		tails.takeover(node.tails());
		heads.takeover(node.heads());
		
		return self;
	}
	
	
	public Set<NODE> reachable() {
		return reachable(null);
	}
	
	
	public boolean isReachable(NODE needle) {
		return reachable(needle) == null;
	}
	
	
	private Set<NODE> reachable(NODE needle) {
		Set<NODE> seen = new HashSet<NODE>();
		
		// Using a stack results in a depth-first search, which should answer
		// queries for the end state faster.
		Stack<NODE> open = new Stack<NODE>();
		// guaranty a safe termination condition for for-loop
		open.push(null);
		
		seen.add(self);
		
		for(NODE pos = self; pos != null; pos = open.pop()) {
			for(ARROW arrow : pos.tails()) {
				NODE next = arrow.head();
				
				// If this is a node, which has never been seen bevore, add it
				// to the list of open nodes and the set of seen nodes. Since
				// seen is a set, there won't be any two entries for the same
				// node.
				if(seen.add(next)) {
					if(next == needle) {
						return null;
					}
					
					open.push(next);
				}
			}
		}
		
		return seen;
	}
}
