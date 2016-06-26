package com.github.skrupellos.follow.graph;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.lang.Iterable;


/*package*/ abstract class ArrowSet<
	NODE  extends Node<? extends Node, ? extends Arrow>,
	ARROW extends Arrow<? extends Node, ? extends Arrow>
> implements Iterable<ARROW> {
	private final NODE node;
	private final Set<ARROW> arrows;
	
	
	/*package*/ ArrowSet(NODE node) {
		if(node == null) {
			throw new IllegalArgumentException("null");
		}
		
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
	 * Take over one end of one or multiple Arrow%s from an other (or the same)
	 * ArrowSet. This involes \ref remove() "removing" the Arrow%s end from its
	 * previous ArrowSet and \ref add() "adding" it to this one. How and which
	 * end of the Arrow is connected to the ArrowSet (or more precisely to the
	 * owning Node of the ArrowSet) is defined by connect(). Since ArrowSet
	 * itself is an Iterable, you can pass it to takeover().
	 */
	public void takeover(ARROW arrow) {
		connect(arrow, node);
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
	 * Adds/Removes a link to an Arrow, but not the other way round. Therefore,
	 * this is to be solely called by Arrow.connectTailTo() and
	 * Arrow.connectHeadTo(). Thowse Arrow methods add/remove the link from the
	 * other direction. It is asserted that the Arros is / is not part of the
	 * set.
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
