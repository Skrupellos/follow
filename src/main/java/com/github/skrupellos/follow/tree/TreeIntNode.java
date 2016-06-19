package com.github.skrupellos.follow.tree;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

public abstract class TreeIntNode extends TreeNode {
	private List<TreeNode> children;
	
	public TreeIntNode() {
		this(null, Collections.<TreeNode>emptyList());
	}
	
	public TreeIntNode(TreeIntNode parent) {
		this(parent, Collections.<TreeNode>emptyList());
	}
	
	public TreeIntNode(List<TreeNode> children) {
		this(null, children);
	}
	
	public TreeIntNode(TreeIntNode parent, List<TreeNode> children) {
		super(parent);
		this.children = new LinkedList<TreeNode>();
		setChildren(children);
	}
	
	@Override
	public List<TreeNode> getChildren() {
		return new LinkedList<TreeNode>(children);
	}
	
	public TreeIntNode setChildren(List<TreeNode> newChildren) {
		List<TreeNode> adopted = new LinkedList<TreeNode>();
		
		// #### Checks, which don't modify the data structure.
		// Argument must not be null.
		if(newChildren == null) {
			throw new IllegalArgumentException("\"children\" must not be null");
		}
		
		// The invariant must hold.
		invariant(newChildren);
		
		for(TreeNode child : newChildren) {
			// The chidren must not be null.
			if(child == null) {
				throw new IllegalArgumentException("\"child\" must not be null");
			}
			
			// Prevent duplicates.
			if(children.contains(child) == false) {
				if(adopted.contains(child)) {
					throw new IllegalArgumentException("No duplicacted children");
				}
				adopted.add(child);
			}
		}
		
		// #### Add (adopt) children.
		// Set parent=this for children, who are now new in the subtree.
		// Add the child immediately, so there won't be a broken
		// association (parent still not knows child, but child already
		// knows parent).
		// /!\ This CAN fail.
		for(TreeNode child : adopted) {
			child.setParent(this, true, false);
			children.add(child);
		}
		
		// #### Remove (orphran) children.
		// Set parent=null for children, who are not in the subtree anymore.
		// /!\ This can NOT fail.
		for(TreeNode child : children) {
			if(newChildren.contains(child) == false) {
				child.setParent(null, false, true);
				children.remove(child);
			}
		}
		
		return this;
	}
	
	public TreeIntNode appendChild(TreeNode child) {
		if(child == null) {
			throw new IllegalArgumentException("\"child\" must not be null");
		}
		
		List<TreeNode> children = getChildren();
		children.add(child);
		setChildren(children);
		
		return this;
	}
	
	public TreeIntNode removeChild(TreeNode child) {
		if(child == null) {
			throw new IllegalArgumentException("\"child\" must not be null");
		}
		
		List<TreeNode> children = getChildren();
		if(children.remove(child) == false) {
			throw new IllegalArgumentException("I decline this paternity suit");
		}
		setChildren(children);
		
		return this;
	}
	
	public TreeNode replaceChild(int pos, TreeNode child) {
		if(child == null) {
			throw new IllegalArgumentException("\"child\" must not be null");
		}
		
		List<TreeNode> children = getChildren();
		TreeNode old = children.set(pos, child);
		setChildren(children);
		
		return old;
	}
	
	public TreeNode getChild(int pos) {
		return children.get(pos);
	}
	
	// Some sort of paternity test.
	protected void invariant(List<TreeNode> newChildren) {
		/* Sorry, you are adopted */
	}
}
