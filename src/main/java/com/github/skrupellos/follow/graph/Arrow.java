package com.github.skrupellos.follow.graph;



public abstract class Arrow<
	NODE  extends Node<NODE, ARROW>,
	ARROW extends Arrow<NODE, ARROW>
> {
	NODE tail;
	NODE head;
	
	
	public Arrow(NODE tail, NODE head) {
		connectToNodes(tail, head);
	}
	
	
	public Arrow(NODE node) {
		loopOnNode(node);
	}
	
	
	private ARROW self() {
		return (ARROW) this;
	}
	
	
	protected abstract NODE createNode();
	
	
	public NODE tail() {
		return tail;
	}
	
	
	public ARROW connectTailTo(NODE node) {
		if(node == null) {
			throw new IllegalArgumentException("null");
		}
		
		if(tail != null) {
			tail.tails().remove(self());
		}
		tail = node;
		tail.tails().add(self());
		
		return self();
	}
	
	
	public NODE head() {
		return head;
	}
	
	
	public ARROW connectHeadTo(NODE node) {
		if(node == null) {
			throw new IllegalArgumentException("null");
		}
		
		if(head != null) {
			head.heads().remove(self());
		}
		head = node;
		head.heads().add(self());
		
		return self();
	}
	
	
	public ARROW loopOnNode(NODE node) {
		return connectToNodes(node, node);
	}
	
	
	public ARROW connectToNodes(NODE tail, NODE head) {
		connectTailTo(tail);
		connectHeadTo(head);
		return self();
	}
	
	
	/**
	 * Remove this Arrow from any Node it is currently connected to. This is
	 * done by removing both ends from the current Nodes and placing them in a
	 * newly created one.
	 */
	@SuppressWarnings("unchecked")
	public void delete() {
		loopOnNode(createNode());
	}
}
