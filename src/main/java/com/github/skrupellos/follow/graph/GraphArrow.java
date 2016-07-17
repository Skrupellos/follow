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

package com.github.skrupellos.follow.graph;


import lombok.NonNull;


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
	
	
	protected abstract NODE createState();
	
	
	public NODE tail() {
		return tail;
	}
	
	
	public ARROW connectTailTo(@NonNull NODE node) {
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
	
	
	public ARROW connectHeadTo(@NonNull NODE node) {
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
	@SuppressWarnings("rawtypes")
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
		loopOnNode(createState());
	}
}
