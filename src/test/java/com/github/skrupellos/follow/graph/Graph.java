package com.github.skrupellos.follow.graph;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import java.util.Arrays;

public class Graph {
	@Test
	public void nodeConstructorSimple() {
		Node n = new Node();
		assertNotNull("tails exists", n.tails());
		assertNotNull("heads exists", n.heads());
	}
	
	@Test
	public void simpleArrow() {
		Node nodes[] = {new Node(), new Node()};
		Arrow arrow = new Arrow(nodes[0], nodes[1]);
		
		assertEquals("Tail link: Arrow -> Node", nodes[0], arrow.tail());
		assertEquals("Head link: Arrow -> Node", nodes[1], arrow.head());
		assertArrayEquals("Tail link: Node -> Arrow", new Object[]{arrow}, nodes[0].tails().arrows().toArray());
		assertArrayEquals("Head link: Node -> Arrow", new Object[]{arrow}, nodes[1].heads().arrows().toArray());
		assertArrayEquals("Start node has no heads",  new Object[]{},      nodes[0].heads().arrows().toArray());
		assertArrayEquals("End node has no tails",    new Object[]{},      nodes[1].tails().arrows().toArray());
	}
	
	
	@Test
	public void delete() {
		Node nodes[] = {new Node(), new Node()};
		Arrow arrow = new Arrow(nodes[0], nodes[1]);
		
		arrow.delete();
		
		assertArrayEquals("Start node has no tails", new Object[]{}, nodes[0].tails().arrows().toArray());
		assertArrayEquals("Start node has no heads", new Object[]{}, nodes[0].heads().arrows().toArray());
		assertArrayEquals("End node has no tails",   new Object[]{}, nodes[1].tails().arrows().toArray());
		assertArrayEquals("End node has no heads",   new Object[]{}, nodes[1].heads().arrows().toArray());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void connectTailToNull() {
		new Arrow(null, new Node());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void connectHeadToNull() {
		new Arrow(new Node(), null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void arrowSetWithNullNode() {
		new ArrowSet<Arrow>(null) {
			public void connect(Arrow arrow, Node<Arrow> node) {
				arrow.connectHeadTo(node);
			}
		};
	}
	
	
	@Test
	public void loopArrow() {
		Node node = new Node();
		Arrow arrow = new Arrow(node);
		
		assertEquals("Tail link: Arrow -> Node", node, arrow.tail());
		assertEquals("Head link: Arrow -> Node", node, arrow.head());
		assertArrayEquals("Tail link: Node -> Arrow", new Object[]{arrow}, node.tails().arrows().toArray());
		assertArrayEquals("Head link: Node -> Arrow", new Object[]{arrow}, node.heads().arrows().toArray());
	}
	
	
	@Test
	public void nodeConstructorTakeover() {
		Node oldNodes[] = {new Node(), new Node(), new Node()};
		Arrow arrows[] = {new Arrow(oldNodes[0], oldNodes[1]), new Arrow(oldNodes[1], oldNodes[2])};
		Node n = new Node(Arrays.asList(arrows[1]), Arrays.asList(arrows[0]));
		
		assertNotNull("tails exists", n.tails());
		assertNotNull("heads exists", n.heads());
	}
}
