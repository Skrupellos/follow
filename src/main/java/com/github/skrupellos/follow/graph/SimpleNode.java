package com.github.skrupellos.follow.graph;


class SimpleNode extends GraphNode<SimpleNode, SimpleArrow> {
	public SimpleNode() {
		super();
	}
	
	
	public SimpleNode(Iterable<SimpleArrow> tails, Iterable<SimpleArrow> heads) {
		super(tails, heads);
	}
}
