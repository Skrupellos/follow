package com.github.skrupellos.follow.graph;

import java.lang.Iterable;



public abstract class GraphNode<
	NODE  extends GraphNode<NODE, ARROW>,
	ARROW extends GraphArrow<NODE, ARROW>
> {
	private final GraphArrowSet<NODE, ARROW> tails = new GraphArrowSet<NODE, ARROW>(self()) {
		public void connect(ARROW arrow, NODE node) {
			arrow.connectTailTo(node);
		}
	};
	
	private final GraphArrowSet<NODE, ARROW> heads = new GraphArrowSet<NODE, ARROW>(self()) {
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
	
	
	private NODE self() {
		return (NODE) this;
	}
	
	
	public final GraphArrowSet<NODE, ARROW> tails() {
		return tails;
	}
	
	
	public final GraphArrowSet<NODE, ARROW> heads() {
		return heads;
	}
	
	
	public NODE replaceBy(NODE replacement) {
		return replacement.takeover(self());
	}
	
	
	public NODE takeover(NODE node) {
		tails.takeover(node.tails());
		heads.takeover(node.heads());
		
		return self();
	}
}
