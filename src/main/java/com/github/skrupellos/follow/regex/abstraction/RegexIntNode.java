package com.github.skrupellos.follow.regex.abstraction;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public abstract class RegexIntNode extends RegexNode {
	private List<RegexNode> children = new LinkedList<RegexNode>();
	
	public RegexNode addChild(RegexNode child) throws Exception {
		if(children.contains(child)) {
			throw new Exception("Was already child");
		}
		
		children.add(child);
		child._setParent(this);
		
		return this;
	}
	
	public RegexNode removeChild(RegexNode child) throws Exception {
		if(children.remove(child)) {
			child._setParent(null);
			return this;
		}
		else {
			throw new Exception("Was not a child");
		}
	}
	
	/// @TODO Direct access to package private children attribute?
	/*package*/ void _addChild(RegexNode child) {
		children.add(child);
	}
	
	/// @TODO Direct access to package private children attribute?
	/*package*/ void _removeChild(RegexNode child) {
		children.remove(child);
	}
	
	public List<RegexNode> getChildren() {
		return new ArrayList<RegexNode>(children);
	}
}
