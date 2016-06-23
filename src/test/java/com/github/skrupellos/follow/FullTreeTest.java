package com.github.skrupellos.follow;

import org.junit.Test;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
import com.github.skrupellos.follow.tree.TreeNode;
import com.github.skrupellos.follow.tree.SimpleTree;

import static org.junit.Assert.assertEquals;

public class FullTreeTest {
	private SimpleTree getTeta() {
		return new SimpleTree(null,
			new SimpleTree(null,
				new SimpleTree(),
				new SimpleTree()
			),
			new SimpleTree(null,
				new SimpleTree(null,
					new SimpleTree(null,
						new SimpleTree(null,
							new SimpleTree()
						),
						new SimpleTree(null,
							new SimpleTree(),
							new SimpleTree(null,
								new SimpleTree()
							)
						)
					),
					new SimpleTree(null,
						new SimpleTree()
					)
				)
			)
		);
	}
	
	private void createCircle(SimpleTree[] nodes) {
		int i = 1;
		for(SimpleTree node : nodes) {
			node.setParent(nodes[i++ % nodes.length]);
		}
	}
	
	@Test
	public void testTreePrinting() {
		String expected = "SimpleTree\n"
		                + "- SimpleTree\n"
		                + "- - SimpleTree\n"
		                + "- - SimpleTree\n"
		                + "- SimpleTree\n"
		                + "- - SimpleTree\n"
		                + "- - - SimpleTree\n"
		                + "- - - - SimpleTree\n"
		                + "- - - - - SimpleTree\n"
		                + "- - - - SimpleTree\n"
		                + "- - - - - SimpleTree\n"
		                + "- - - - - SimpleTree\n"
		                + "- - - - - - SimpleTree\n"
		                + "- - - SimpleTree\n"
		                + "- - - - SimpleTree\n";
		assertEquals("treeString()", expected, getTeta().treeString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParentCircle1() {
		createCircle(new SimpleTree[]{
			new SimpleTree()
		});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParentCircle2() {
		createCircle(new SimpleTree[]{
			new SimpleTree(),
			new SimpleTree()
		});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParentCircle3() {
		createCircle(new SimpleTree[]{
			new SimpleTree(),
			new SimpleTree(),
			new SimpleTree()
		});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParentCircle4() {
		createCircle(new SimpleTree[]{
			new SimpleTree(),
			new SimpleTree(),
			new SimpleTree(),
			new SimpleTree()
		});
	}
	
	@Test
	public void getLevelRoot() {
		SimpleTree n1, n2, n3;
		SimpleTree n4;
		
		// Test some constructors
		TreeNode[] nodes = {
			n1 = new SimpleTree(),   // Test SimpleTree()
			n2 = new SimpleTree(n1), // Test SimpleTree(parent)
			n3 = new SimpleTree(),   // Test SimpleTree.setParent(parent)
			n4 = new SimpleTree(n3)  // Test SimpleTree(parent)
		};
		n3.setParent(n2);
		
		// Test getLevel()
		for(int i = 0; i < nodes.length; i++) {
			assertEquals("getLevel "+i, i, nodes[i].level());
		}
		
		// Test getRoot()
		for(int i = 0; i < nodes.length; i++) {
			assertEquals("getRoot "+i, nodes[0], nodes[i].root());
		}
	}
	
// 	@Test(expected = AlterJungeException.class)
// 	public void testFailingTree() {
// 		new RegexCatenation(
// 			new SimpleTree<Integer>(23),
// 			new SimpleTree<String>("23")
// 		);
// 	}
}
