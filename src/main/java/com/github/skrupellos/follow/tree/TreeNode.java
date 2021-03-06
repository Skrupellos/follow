/* This file is part of Follow (https://github.com/Skrupellos/follow).
 * Copyright (c) 2016 Skruppy <skruppy@onmars.eu> and kratl.
 *
 * Follow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Follow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Follow. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.skrupellos.follow.tree;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lombok.NonNull;


public abstract class TreeNode<SELF extends TreeNode<SELF>> implements Iterable<SELF> {
	private List<SELF> children = new LinkedList<SELF>();
	private SELF parent;
	private final SELF self = checkedSelf();
	
	private final static String DELIMITER = "- ";
	private final static String NEWLINE = "\n";
	
	
	// Nothing
	public TreeNode() {
		this(null, Collections.<SELF>emptyList());
	}
	
	
	// Only parent
	public TreeNode(SELF parent) {
		this(parent, Collections.<SELF>emptyList());
	}
	
	
	// Only children
	public TreeNode(List<SELF> children) {
		this(null, children);
	}
	
	
	// Parent & children
	public TreeNode(SELF parent, List<SELF> children) {
		setParent(parent);
		setChildren(children);
	}
	
	
	protected abstract SELF uncheckedSelf();
	
	
	private SELF checkedSelf() {
		SELF self = uncheckedSelf();
		assert(self == this);
		return self;
	}
	
	
	public SELF setParent(SELF newParent) {
		return setParent(newParent, true, true);
	}
	
	
	/**
	 * This is a variant of setParent(), which can't fail. It's sole purpose is
	 * to make setChildren() happy. clearParent() is only called from
	 * setChildren() by our parent, after the invariant has been checked.
	 * Therefore we don't have to go the offical way and ask our parent to
	 * orphan (remove) us.
	 */
	/*package*/ SELF setParent(SELF newParent, boolean remove, boolean append) {
		// Prevent loops
		for(SELF ancestor = newParent; ancestor != null; ancestor = ancestor.parent()) {
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
				parent.removeChild(self);
			}
			parent = null;
			
			// If this will become a subtree (not a root node), then subscrie
			// to the new parent as a child.
			// /!\ This CAN fail.
			if(newParent != null && append) {
				newParent.appendChild(self);
			}
			parent = newParent;
		}
		
		return self;
	}
	
	
	public SELF parent() {
		return parent;
	}
	
	
	/**
	 * Return connected children. To prevent modification, a shallow copy is
	 * returned.
	 * @see iterator()
	 */
	public List<SELF> children() {
		return new LinkedList<SELF>(children);
	}
	
	
	/**
	 * Return an iterator of children. Since an iterator can remove
	 * elements from the underlying collection, an iterator of a shallow
	 * coppy is used.
	 * @see children()
	 */
	public Iterator<SELF> iterator() {
		return children().iterator();
	}
	
	
	public SELF setChildren(@NonNull List<SELF> newChildren) {
		List<SELF> adopted   = new LinkedList<SELF>();
		
		// #### Checks, which don't modify the data structure.
		
		// The invariant must hold.
		invariant(newChildren);
		
		for(SELF child : newChildren) {
			// The chidren must not be null.
			if(child == null) {
				throw new NullPointerException("\"child\" must not be null");
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
		for(SELF child : adopted) {
			child.setParent(self, true, false);
			children.add(child);
		}
		
		// #### Remove (orphran) children.
		// Set parent=null for children, who are not in the subtree anymore.
		// /!\ This can NOT fail.
		for(SELF child : children) {
			if(newChildren.contains(child) == false) {
				child.setParent(null, false, true);
			}
		}
		
		// Replace tge whole list to keep the order
		children = new LinkedList<SELF>(newChildren);
		
		return self;
	}
	
	
	public SELF appendChild(SELF child) {
		List<SELF> children = children();
		children.add(child);
		setChildren(children);
		
		return self;
	}
	
	
	public SELF removeChild(@NonNull SELF child) {
		List<SELF> children = children();
		if(children.remove(child) == false) {
			throw new IllegalArgumentException("I decline this paternity suit");
		}
		setChildren(children);
		
		return self;
	}
	
	
	public SELF replaceChild(int pos, @NonNull SELF child) {
		List<SELF> children = children();
		SELF old = children.set(pos, child);
		setChildren(children);
		
		return old;
	}
	
	
	public SELF getChild(int pos) {
		return children.get(pos);
	}
	
	
	public SELF root() {
		SELF root = self;
		
		for(SELF ancestor = root; ancestor != null; ancestor = ancestor.parent()) {
			root = ancestor;
		}
		
		return root;
	}
	
	
	public int level() {
		int level = 0;
		
		for(SELF ancestor = parent; ancestor != null; ancestor = ancestor.parent()) {
			level++;
		}
		
		return level;
	}
	
	
	public boolean shallowEquivalent(SELF other) {
		return other != null && this.getClass() == other.getClass();
	}
	
	
	public boolean isIsomorphTo(SELF other) {
		// Fast path: Both objects are identical
		if(other == this) {
			return true;
		}
		
		// Not isomorph, if the root nodes itself are not equivalent
		if(shallowEquivalent(other) == false) {
			return false;
		}
		
		// Not isomorph, if the other root has less children than us or they
		// are not isomorph to our children.
		Iterator<SELF> otherChildren = other.iterator();
		for(SELF child : children) {
			if(!(otherChildren.hasNext() && child.isIsomorphTo(otherChildren.next()))) {
				return false;
			}
		}
		
		// Not isomorph, if the other root has more children.
		if(otherChildren.hasNext()) {
			return false;
		}
		
		// If all checks have been passed, the objects are isomorph
		return true;
	}
	
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
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
		for(SELF t : children()) {
			t.appendSubTreeString(builder, level);
		}
	}
	
	
	protected void invariant(List<SELF> newChildren) { }
}
