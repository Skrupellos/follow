package com.github.skrupellos.follow.tree;

public abstract class TreeNode {
	private final static String DELIMITER = "--";
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
			if(parent != null) {
				parent.addChild(this);
			}
			this.parent = parent;
		}
		return this;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	
	public TreeNode getRoot() {
		return parent == null ? this : parent.getRoot();
	}
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}
	
	public String TreeString() {
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
