package com.github.skrupellos.follow.graph;


class SimpleNode extends Node<SimpleNode, SimpleArrow> {
	public SimpleNode() {
		super();
	}
	
	
	public SimpleNode(Iterable<SimpleArrow> tails, Iterable<SimpleArrow> heads) {
		super(tails, heads);
	}
}
