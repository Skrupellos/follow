package com.github.skrupellos.follow.graph;


class SimpleArrow extends GraphArrow<SimpleNode, SimpleArrow> {
	public SimpleArrow(SimpleNode tail, SimpleNode head) {
		super(tail, head);
	}
	
	
	public SimpleArrow(SimpleNode node) {
		super(node);
	}
	
	
	protected SimpleNode createNode() {
		return new SimpleNode();
	}
	
	
	protected SimpleArrow uncheckedSelf() {
		return this;
	}
}