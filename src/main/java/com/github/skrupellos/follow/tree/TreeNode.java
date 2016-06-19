package com.github.skrupellos.follow.tree;

public abstract class TreeNode {
	private final static String DELIMITER = "- ";
	private final static String NEWLINE = "\n";
	
	private TreeIntNode parent;

	public TreeNode() {
		this(null);
	}
	
	public TreeNode(TreeIntNode parent) {
		setParent(parent);
	}
	
	public TreeNode setParent(TreeIntNode parent) {
		if(this.parent != parent) {
			if(this.parent != null) {
				this.parent.removeChild(this);
			}
			this.parent = parent;
			if(parent != null) {
				if(parent.isAncestor(this)) {
					throw new IllegalStateException("\t[EE] You tried to construct a cycle in a tree. Parent: " + parent + ", Child: " + this);
				}
				parent.addChild(this);
			}
		}
		return this;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	
	public TreeNode getRoot() {
		return parent == null ? this : parent.getRoot();
	}
	
	public boolean isAncestor(TreeNode node) {
		boolean ancestor = parent == node;
		
		if(!(parent == null || parent == node)) {
			ancestor = parent.isAncestor(node);
		} 
		return ancestor;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}
	
	public String treeString() {
		StringBuilder builder = new StringBuilder();
		appendSubTreeString(builder, 0);
		return builder.toString();
	}
	
	void appendSubTreeString(StringBuilder builder, int level) {
		for(int i = 0; i < level; i++) {
			builder.append(DELIMITER);
		}
		builder.append(toString());
		builder.append(NEWLINE);

		level++;
		for(TreeNode t : getChildren()) {
			t.appendSubTreeString(builder, level);
		}
	}
	
	public abstract TreeNode[] getChildren();
}
