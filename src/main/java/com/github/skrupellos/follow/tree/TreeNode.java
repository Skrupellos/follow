package com.github.skrupellos.follow.tree;

import java.util.List;

public abstract class TreeNode {
	private final static String DELIMITER = "- ";
	private final static String NEWLINE = "\n";
	
	private TreeIntNode parent;
	
	public TreeNode(TreeIntNode parent) {
		setParent(parent);
	}
	
	public TreeNode setParent(TreeIntNode newParent) {
		return setParent(newParent, true, true);
	}
	
	
	/**
	 * This is a variant of setParent(), which can't fail. It's sole purpose is
	 * to make setChildren() happy. clearParent() is only called from
	 * setChildren() by our parent, after the invariant has been checked.
	 * Therefore we don't have to go the offical way and ask our parent to
	 * orphan (remove) us.
	 */
	/*package*/ TreeNode setParent(TreeIntNode newParent, boolean remove, boolean append) {
		// Prevent loops
		/// @TODO This is to restrictive
		for(TreeNode ancestor = newParent; ancestor != null; ancestor = ancestor.getParent()) {
			if(ancestor == this) {
				throw new IllegalArgumentException("Sorry, no circles allowed here");
			}
		}
		
		// Prevent multiple appearances
		if(parent != newParent) {
			// If this was a subtree (not a root node), then unsubscribe from
			// the old parent as a child.
			// /!\ This CAN fail.
			if(parent != null && remove) {
				parent.removeChild(this);
			}
			parent = null;
			
			// If this will become a subtree (not a root node), then subscrie
			// to the new parent as a child.
			// /!\ This CAN fail.
			if(newParent != null && append) {
				newParent.appendChild(this);
			}
			parent = newParent;
		}
		
		return this;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	
	public TreeNode getRoot() {
		return parent == null ? this : parent.getRoot();
	}
	
	public int getLevel() {
		return parent == null ? 0 : parent.getLevel() + 1;
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
	
	public abstract List<TreeNode> getChildren();
}
