package com.github.skrupellos.follow.graph;



public abstract class GraphArrow<
	NODE  extends GraphNode<NODE, ARROW>,
	ARROW extends GraphArrow<NODE, ARROW>
> {
	private final ARROW self = checkedSelf();
	private NODE tail;
	private NODE head;
	
	
	public GraphArrow(NODE tail, NODE head) {
		connectToNodes(tail, head);
	}
	
	
	public GraphArrow(NODE node) {
		loopOnNode(node);
	}
	
	
	protected abstract ARROW uncheckedSelf();
	
	
	private ARROW checkedSelf() {
		ARROW self = uncheckedSelf();
		assert(self == this);
		return self;
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
			tail.tails().remove(self);
		}
		tail = node;
		tail.tails().add(self);
		
		return self;
	}
	
	
	public NODE head() {
		return head;
	}
	
	
	public ARROW connectHeadTo(NODE node) {
		if(node == null) {
			throw new IllegalArgumentException("null");
		}
		
		if(head != null) {
			head.heads().remove(self);
		}
		head = node;
		head.heads().add(self);
		
		return self;
	}
	
	
	public ARROW loopOnNode(NODE node) {
		return connectToNodes(node, node);
	}
	
	
	public ARROW connectToNodes(NODE tail, NODE head) {
		connectTailTo(tail);
		connectHeadTo(head);
		return self;
	}
	
	
	// Here we make a huge exception and don't use ARROW. Instead we use
	// GraphArrow, which allows us to access private members.
	public boolean equalContents(GraphArrow other) {
		return
			getClass().equals(other.getClass()) &&
			tail == other.tail &&
			head == other.head;
	}
	
	
	/**
	 * Remove this GraphArrow from any GraphNode it is currently connected to.
	 * This is done by removing both ends from the current Nodes and placing
	 * them in a newly created one.
	 */
	public void delete() {
		loopOnNode(createNode());
	}
}
