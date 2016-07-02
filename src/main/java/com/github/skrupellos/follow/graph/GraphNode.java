package com.github.skrupellos.follow.graph;

import java.lang.Iterable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;
import java.util.Set;



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
	
	
	public NODE replaceBy(NODE replacement) {
		return replacement.takeover(self);
	}
	
	
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
