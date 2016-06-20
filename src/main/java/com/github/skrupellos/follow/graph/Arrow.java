package com.github.skrupellos.follow.graph;



public class Arrow {
	INode tail;
	INode head;
	
	
	Arrow(INode tail, INode head) {
		connectToNodes(tail, head);
	}
	
	
	Arrow(INode node) {
		loopOnNode(node);
	}
	
	
	public INode tail() {
		return tail;
	}
	
	
	public Arrow connectTailTo(INode node) {
		if(node == null) {
			throw new IllegalArgumentException("null");
		}
		
		if(tail != null) {
			tail.tails().remove(this);
		}
		tail = node;
		tail.tails().add(this);
		
		return this;
	}
	
	
	public INode head() {
		return head;
	}
	
	
	public Arrow connectHeadTo(INode node) {
		if(node == null) {
			throw new IllegalArgumentException("null");
		}
		
		if(head != null) {
			head.heads().remove(this);
		}
		head = node;
		head.heads().add(this);
		
		return this;
	}
	
	
	public Arrow loopOnNode(INode node) {
		return connectToNodes(node, node);
	}
	
	
	public Arrow connectToNodes(INode tail, INode head) {
		connectTailTo(tail);
		connectHeadTo(head);
		return this;
	}
	
	
	/**
	 * Remove this Arrow from any Node it is currently connected to. This is
	 * done by removing both ends from the current Nodes and placing them in a
	 * newly created one.
	 */
	public void delete() {
		loopOnNode(new Node());
	}
}
