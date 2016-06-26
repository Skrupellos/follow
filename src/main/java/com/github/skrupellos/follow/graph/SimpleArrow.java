package com.github.skrupellos.follow.graph;


class SimpleArrow extends Arrow<SimpleNode, SimpleArrow> {
	SimpleArrow(SimpleNode tail, SimpleNode head) {
		super(tail, head);
	}
	
	
	SimpleArrow(SimpleNode node) {
		super(node);
	}
	
	
	protected SimpleNode createNode() {
		return new SimpleNode();
	}
}
