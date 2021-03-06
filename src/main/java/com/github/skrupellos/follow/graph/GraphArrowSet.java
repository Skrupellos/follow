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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lombok.NonNull;


public abstract class GraphArrowSet<
	NODE  extends GraphNode<NODE, ARROW>,
	ARROW extends GraphArrow<NODE, ARROW>
> implements Iterable<ARROW> {
	private final NODE node;
	private final Set<ARROW> arrows;
	
	
	/*package*/ GraphArrowSet(@NonNull NODE node) {
		this.node   = node;
		this.arrows = new HashSet<ARROW>();
	}
	
	
	/**
	 * Return connected Arrow%s. To prevent modification, a shallow copy is
	 * returned.
	 * @see iterator()
	 */
	public Set<ARROW> arrows() {
		return new HashSet<ARROW>(arrows);
	}
	
	
	/**
	 * Return an iterator of connected Arrows. Since an iterator can remove
	 * elements from the underlying collection, an iterator of a shallow
	 * coppy is used.
	 * @see arrows()
	 */
	public Iterator<ARROW> iterator() {
		return arrows().iterator();
	}
	
	
	/**
	 * @{
	 * Take over one end of one or multiple GraphArrow%s from an other (or the
	 * same) GraphArrowSet. This involes \ref remove() "removing" the
	 * GraphArrow%s end from its previous GraphArrowSet and \ref add() "adding"
	 * it to this one. How and which end of the GraphArrow is connected to the
	 * GraphArrowSet (or more precisely to the owning GraphNode of the
	 * GraphArrowSet) is defined by connect(). Since GraphArrowSet itself is an
	 * Iterable, you can pass it to takeover().
	 */
	public void takeover(ARROW arrow) {
		connect(arrow, node);
		
		for(ARROW other : arrows) {
			if(other != arrow && other.equals(arrow)) {
				other.delete();
				break;
			}
		}
	}
	
	
	public void takeover(Iterable<ARROW> arrows) {
		for(ARROW arrow : arrows) {
			takeover(arrow);
		}
	}
	/** @} */
	
	
	protected abstract void connect(ARROW arrow, NODE node);
	
	
	/**
	 * @{
	 * Adds/Removes a link to an GraphArrow, but not the other way round.
	 * Therefore, this is to be solely called by GraphArrow.connectTailTo() and
	 * GraphArrow.connectHeadTo(). Thowse GraphArrow methods add/remove the
	 * link from the other direction. It is asserted that the GraphArrow
	 * is / is not part of the set.
	 */
	/*package*/ final void remove(ARROW arrow) {
		assert(arrows.contains(arrow) == true);
		arrows.remove(arrow);
	}
	
	
	/*package*/ final void add(ARROW arrow) {
		assert(arrows.contains(arrow) == false);
		arrows.add(arrow);
	}
	/** @} */
}
