package com.github.skrupellos.follow;

import org.junit.Before;
import org.junit.Test;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.tree.TreeNode;
import com.github.skrupellos.follow.tree.TreeIntNode;
import com.github.skrupellos.follow.tree.TreeExtNode;

import static org.junit.Assert.assertEquals;

public class FullTreeTest {
	private TreeIntNode getTeta() {
		return new RegexCatenation(
			new RegexUnion(
				new RegexSymbol<String>("a"),
				new RegexSymbol<String>("b")
			),
			new RegexStar(
				new RegexUnion(
					new RegexUnion(
						new RegexStar(
							new RegexSymbol<String>("a")
						),
						new RegexCatenation(
							new RegexSymbol<String>("b"),
							new RegexStar(
								new RegexSymbol<String>("a")
							)
						)
					),
					new RegexStar(
						new RegexSymbol<String>("b")
					)
				)
			)
		);
	}
	
	private void createCircle(TreeIntNode[] nodes) {
		int i = 1;
		for(TreeIntNode node : nodes) {
			node.setParent(nodes[i++ % nodes.length]);
		}
	}
	
	@Test
	public void testTreePrinting() {
		String expected = "meow\n"
		                + "- +\n"
		                + "- - a\n"
		                + "- - b\n"
		                + "- *\n"
		                + "- - +\n"
		                + "- - - +\n"
		                + "- - - - *\n"
		                + "- - - - - a\n"
		                + "- - - - meow\n"
		                + "- - - - - b\n"
		                + "- - - - - *\n"
		                + "- - - - - - a\n"
		                + "- - - *\n"
		                + "- - - - b\n";
		assertEquals("treeString()", getTeta().treeString(), expected);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParentCircle1() {
		createCircle(new TreeIntNode[]{
			new TreeIntNode(){}
		});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParentCircle2() {
		createCircle(new TreeIntNode[]{
			new TreeIntNode(){},
			new TreeIntNode(){}
		});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParentCircle3() {
		createCircle(new TreeIntNode[]{
			new TreeIntNode(){},
			new TreeIntNode(){},
			new TreeIntNode(){}
		});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParentCircle4() {
		createCircle(new TreeIntNode[]{
			new TreeIntNode(){},
			new TreeIntNode(){},
			new TreeIntNode(){},
			new TreeIntNode(){}
		});
	}
	
	@Test
	public void getLevelRoot() {
		TreeIntNode n1, n2, n3;
		TreeExtNode n4;
		
		// Test some constructors
		TreeNode[] nodes = {
			n1 = new TreeIntNode(){},   // Test TreeIntNode()
			n2 = new TreeIntNode(n1){}, // Test TreeIntNode(parent)
			n3 = new TreeIntNode(){},   // Test TreeIntNode.setParent(parent)
			n4 = new TreeExtNode(n3){}  // Test TreeExtNode(parent)
		};
		n3.setParent(n2);
		
		// Test getLevel()
		for(int i = 0; i < nodes.length; i++) {
			assertEquals("getLevel "+i, nodes[i].getLevel(), i);
		}
		
		// Test getRoot()
		for(int i = 0; i < nodes.length; i++) {
			assertEquals("getRoot "+i, nodes[i].getRoot(), nodes[0]);
		}
	}
	
	@Test(expected = AlterJungeException.class)
	public void testFailingTree() {
		new RegexCatenation(
			new RegexSymbol<Integer>(23),
			new RegexSymbol<String>("23")
		);
	}
}
