package com.github.skrupellos.follow.graph;

import java.lang.Iterable;


public class Node<ARROW extends Arrow> implements INode<ARROW> {
	private final ArrowSet<ARROW> tails = new ArrowSet<ARROW>(this) {
		public void connect(ARROW arrow, Node<ARROW> node) {
			arrow.connectTailTo(node);
		}
	};
	
	private final ArrowSet<ARROW> heads = new ArrowSet<ARROW>(this) {
		public void connect(ARROW arrow, Node<ARROW> node) {
			arrow.connectHeadTo(node);
		}
	};
	
	
	public Node() { }
	
	
	public Node(Iterable<ARROW> tails, Iterable<ARROW> heads) {
		this();
		this.tails.takeover(tails);
		this.heads.takeover(heads);
	}
	
	
	public final ArrowSet<ARROW> tails() {
		return tails;
	}
	
	
	public final ArrowSet<ARROW> heads() {
		return heads;
	}
	
	
	public Node<ARROW> replaceBy(Node<ARROW> replacement) {
		return replacement.takeover(this);
	}
	
	
	public Node<ARROW> takeover(Node<ARROW> node) {
		tails.takeover(node.tails());
		heads.takeover(node.heads());
		
		return this;
	}
}
