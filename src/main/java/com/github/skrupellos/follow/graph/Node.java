package com.github.skrupellos.follow.graph;

import java.lang.Iterable;



public abstract class Node<
	NODE  extends Node<NODE, ARROW>,
	ARROW extends Arrow<NODE, ARROW>
> {
	private final ArrowSet<NODE, ARROW> tails = new ArrowSet<NODE, ARROW>(self()) {
		public void connect(ARROW arrow, NODE node) {
			arrow.connectTailTo(node);
		}
	};
	
	private final ArrowSet<NODE, ARROW> heads = new ArrowSet<NODE, ARROW>(self()) {
		public void connect(ARROW arrow, NODE node) {
			arrow.connectHeadTo(node);
		}
	};
	
	
	public Node() { }
	
	
	public Node(Iterable<ARROW> tails, Iterable<ARROW> heads) {
		this();
		this.tails.takeover(tails);
		this.heads.takeover(heads);
	}
	
	
	private NODE self() {
		return (NODE) this;
	}
	
	
	public final ArrowSet<NODE, ARROW> tails() {
		return tails;
	}
	
	
	public final ArrowSet<NODE, ARROW> heads() {
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
