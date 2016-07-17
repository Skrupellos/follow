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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

public class GraphSpec {
	@Test
	public void nodeConstructorSimple() {
		SimpleNode n = new SimpleNode();
		assertNotNull("tails exists", n.tails());
		assertNotNull("heads exists", n.heads());
	}
	
	@Test
	public void simpleArrow() {
		SimpleNode nodes[] = {new SimpleNode(), new SimpleNode()};
		SimpleArrow arrow = new SimpleArrow(nodes[0], nodes[1]);
		
		assertEquals("Tail link: Arrow -> Node", nodes[0], arrow.tail());
		assertEquals("Head link: Arrow -> Node", nodes[1], arrow.head());
		assertArrayEquals("Tail link: Node -> Arrow", new Object[]{arrow}, nodes[0].tails().arrows().toArray());
		assertArrayEquals("Head link: Node -> Arrow", new Object[]{arrow}, nodes[1].heads().arrows().toArray());
		assertArrayEquals("Start node has no heads",  new Object[]{},      nodes[0].heads().arrows().toArray());
		assertArrayEquals("End node has no tails",    new Object[]{},      nodes[1].tails().arrows().toArray());
	}
	
	
	@Test
	public void delete() {
		SimpleNode nodes[] = {new SimpleNode(), new SimpleNode()};
		SimpleArrow arrow = new SimpleArrow(nodes[0], nodes[1]);
		
		arrow.delete();
		
		assertArrayEquals("Start node has no tails", new Object[]{}, nodes[0].tails().arrows().toArray());
		assertArrayEquals("Start node has no heads", new Object[]{}, nodes[0].heads().arrows().toArray());
		assertArrayEquals("End node has no tails",   new Object[]{}, nodes[1].tails().arrows().toArray());
		assertArrayEquals("End node has no heads",   new Object[]{}, nodes[1].heads().arrows().toArray());
	}
	
	
	@Test(expected = NullPointerException.class)
	public void connectTailToNull() {
		new SimpleArrow(null, new SimpleNode());
	}
	
	
	@Test(expected = NullPointerException.class)
	public void connectHeadToNull() {
		new SimpleArrow(new SimpleNode(), null);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void arrowSetWithNullNode() {
		new GraphArrowSet<SimpleNode, SimpleArrow>(null) {
			public void connect(SimpleArrow arrow, SimpleNode node) {
				arrow.connectHeadTo(node);
			}
		};
	}
	
	
	@Test
	public void loopArrow() {
		SimpleNode node = new SimpleNode();
		SimpleArrow arrow = new SimpleArrow(node);
		
		assertEquals("Tail link: Arrow -> Node", node, arrow.tail());
		assertEquals("Head link: Arrow -> Node", node, arrow.head());
		assertArrayEquals("Tail link: Node -> Arrow", new Object[]{arrow}, node.tails().arrows().toArray());
		assertArrayEquals("Head link: Node -> Arrow", new Object[]{arrow}, node.heads().arrows().toArray());
	}
	
	
	@Test
	public void nodeConstructorTakeover() {
		SimpleNode oldNodes[] = {new SimpleNode(), new SimpleNode(), new SimpleNode()};
		SimpleArrow arrows[] = {new SimpleArrow(oldNodes[0], oldNodes[1]), new SimpleArrow(oldNodes[1], oldNodes[2])};
		SimpleNode n = new SimpleNode(Arrays.asList(arrows[1]), Arrays.asList(arrows[0]));
		
		assertNotNull("tails exists", n.tails());
		assertNotNull("heads exists", n.heads());
	}
}
