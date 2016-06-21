package com.github.skrupellos.follow.graph;



public class Arrow<NODE extends INode<Arrow<NODE>>> {
	NODE tail;
	NODE head;
	
	
	Arrow(NODE tail, NODE head) {
		connectToNodes(tail, head);
	}
	
	
	Arrow(NODE node) {
		loopOnNode(node);
	}
	
	
	public NODE tail() {
		return tail;
	}
	
	
	public Arrow<NODE> connectTailTo(NODE node) {
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
	
	
	public NODE head() {
		return head;
	}
	
	
	public Arrow<NODE> connectHeadTo(NODE node) {
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
	
	
	public Arrow<NODE> loopOnNode(NODE node) {
		return connectToNodes(node, node);
	}
	
	
	public Arrow<NODE> connectToNodes(NODE tail, NODE head) {
		connectTailTo(tail);
		connectHeadTo(head);
		return this;
	}
	
	
	/**
	 * Remove this Arrow from any Node it is currently connected to. This is
	 * done by removing both ends from the current Nodes and placing them in a
	 * newly created one.
	 */
	@SuppressWarnings("unchecked")
	public void delete() {
		loopOnNode((NODE) new Node<Arrow<NODE>>());
	}
}
