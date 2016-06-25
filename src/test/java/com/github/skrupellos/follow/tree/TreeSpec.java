package com.github.skrupellos.follow.tree;

import org.junit.Test;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
import com.github.skrupellos.follow.tree.TreeNode;
import com.github.skrupellos.follow.tree.SimpleTree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertArrayEquals;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import static org.junit.Assert.assertEquals;

public class TreeSpec {
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
	public void constructorEmpty() {
		SimpleTree node = new SimpleTree();
		
		assertNull("No parent", node.parent());
		assertArrayEquals("No children", new Object[]{}, node.children().toArray());
	}
	
	
	@Test
	public void constructorParent() {
		SimpleTree parent = new SimpleTree();
		SimpleTree node   = new SimpleTree(parent);
		
		assertEquals("Child: Has parent", parent, node.parent());
		assertArrayEquals("Child: No children", new Object[]{}, node.children().toArray());
		assertArrayEquals("Parent: Has children", new Object[]{node}, parent.children().toArray());
	}
	
	
	@Test
	public void constructorParentNull() {
		SimpleTree node = new SimpleTree( (SimpleTree)null );
		
		assertNull("No parent", node.parent());
		assertArrayEquals("No children", new Object[]{}, node.children().toArray());
	}
	
	
	@Test
	public void constructorChildren() {
		SimpleTree[] children = {new SimpleTree(), new SimpleTree()};
		List<SimpleTree> list = Arrays.asList(children);
		SimpleTree node       = new SimpleTree(list);
		
		assertNull("Parent: No parent", node.parent());
		assertArrayEquals("Parent: Has children", children, node.children().toArray());
		
		int i = 0;
		for(SimpleTree child : children) {
			assertEquals("Child "+i+": Has parent", node, child.parent());
			assertArrayEquals("Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void constructorChildrenNull() {
		new SimpleTree( (List<SimpleTree>)null );
	}
	
	
	@Test
	public void constructorParentNoChildren() {
		SimpleTree parent = new SimpleTree();
		SimpleTree node = new SimpleTree(parent, new SimpleTree[]{});
		
		assertNull("Parent: No parent", parent.parent());
		assertArrayEquals("Parent: Has children", new Object[]{node}, parent.children().toArray());
		
		assertEquals("Testee: Has parent", parent, node.parent());
		assertArrayEquals("Testee: No children", new Object[]{}, node.children().toArray());
	}
	
	
	@Test
	public void constructorParentOneChild() {
		SimpleTree child  = new SimpleTree();
		SimpleTree parent = new SimpleTree();
		SimpleTree node   = new SimpleTree(parent, child);
		
		assertNull("Parent: No parent", parent.parent());
		assertArrayEquals("Parent: Has children", new Object[]{node}, parent.children().toArray());
		
		assertEquals("Testee: Has parent", parent, node.parent());
		assertArrayEquals("Testee: Has children", new Object[]{child}, node.children().toArray());
		
		assertEquals("Child: Has parent", node, child.parent());
		assertArrayEquals("Child: No children", new Object[]{}, child.children().toArray());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void constructorParentOneNullChild() {
		new SimpleTree(null, (SimpleTree)null );
	}
	
	
	@Test
	public void constructorParentTwoChildren() {
		SimpleTree[] children = {new SimpleTree(), new SimpleTree()};
		SimpleTree parent     = new SimpleTree();
		SimpleTree node       = new SimpleTree(parent, children[0], children[1]);
		
		assertNull("Parent: No parent", parent.parent());
		assertArrayEquals("Parent: Has children", new Object[]{node}, parent.children().toArray());
		
		assertEquals("Testee: Has parent", parent, node.parent());
		assertArrayEquals("Testee: Has children", children, node.children().toArray());
		
		int i = 0;
		for(SimpleTree child : children) {
			assertEquals("Child "+i+": Has parent", node, child.parent());
			assertArrayEquals("Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void constructorParentOneChildOneNullChild() {
		new SimpleTree(null, new SimpleTree(), null);
	}
	
	
	@Test
	public void constructorParentList() {
		SimpleTree[] children = {new SimpleTree(), new SimpleTree()};
		List<SimpleTree> list = Arrays.asList(children);
		SimpleTree parent     = new SimpleTree();
		SimpleTree node       = new SimpleTree(parent, list);
		
		assertNull("Parent: No parent", parent.parent());
		assertArrayEquals("Parent: Has children", new Object[]{node}, parent.children().toArray());
		
		assertEquals("Testee: Has parent", parent, node.parent());
		assertArrayEquals("Testee: Has children", children, node.children().toArray());
		
		int i = 0;
		for(SimpleTree child : children) {
			assertEquals("Child "+i+": Has parent", node, child.parent());
			assertArrayEquals("Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void constructorParentNullList() {
		new SimpleTree(null, (List<SimpleTree>)null );
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
	
	
	@Test(expected = IllegalArgumentException.class)
	public void setChildrenDup() {
		SimpleTree child = new SimpleTree();
		SimpleTree node = new SimpleTree();
		node.setChildren(Arrays.asList(child, child));
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void setChildrenNull() {
		SimpleTree child = new SimpleTree();
		SimpleTree node = new SimpleTree();
		node.setChildren(Arrays.asList(child, null));
	}
	
	
	@Test
	public void setChildrenAdopt() {
		SimpleTree[] children = {new SimpleTree(), new SimpleTree()};
		SimpleTree oldParent = new SimpleTree(null, children);
		SimpleTree newParent = new SimpleTree();
		
		assertNull("Before: Old parent: No parent", oldParent.parent());
		assertArrayEquals("Before: Old parent: Has children", children, oldParent.children().toArray());
		
		assertNull("Before: New parent: No parent", newParent.parent());
		assertArrayEquals("Before: New parent: No children", new Object[]{}, newParent.children().toArray());
		
		int i = 0;
		for(SimpleTree child : children) {
			assertEquals("Before: Child "+i+": Has parent", oldParent, child.parent());
			assertArrayEquals("Before: Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
		
		newParent.setChildren(Arrays.asList(children));
		
		assertNull("After: Old parent: No parent", oldParent.parent());
		assertArrayEquals("After: Old parent: Has children", new Object[]{}, oldParent.children().toArray());
		
		assertNull("After: New parent: No parent", newParent.parent());
		assertArrayEquals("After: New parent: No children", children, newParent.children().toArray());
		
		i = 0;
		for(SimpleTree child : children) {
			assertEquals("After: Child "+i+": Has parent", newParent, child.parent());
			assertArrayEquals("After: Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void removeChildNull() {
		SimpleTree node = new SimpleTree(Arrays.asList(new SimpleTree()));
		node.removeChild(null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void removeChildNonexisting() {
		SimpleTree node = new SimpleTree(Arrays.asList(new SimpleTree()));
		node.removeChild(new SimpleTree());
	}
	
	
	@Test
	public void removeChild() {
		SimpleTree[] childrenBefore = {new SimpleTree(), new SimpleTree(), new SimpleTree()};
		SimpleTree[] childrenAfter  = {childrenBefore[0], childrenBefore[2]};
		SimpleTree node = new SimpleTree(null, childrenBefore);
		
		assertNull("Before: Node: No parent", node.parent());
		assertArrayEquals("Before: Node: Has children", childrenBefore, node.children().toArray());
		
		int i = 0;
		for(SimpleTree child : childrenBefore) {
			assertEquals("Before: Child "+i+": Has parent", node, child.parent());
			assertArrayEquals("Before: Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
		
		node.removeChild(childrenBefore[1]);
		
		assertNull("After: Node: No parent", node.parent());
		assertArrayEquals("After: Node: Has children", childrenAfter, node.children().toArray());
		
		assertNull("After: Removed: No parent", childrenBefore[1].parent());
		assertArrayEquals("After: Removed: Has children", new Object[]{}, childrenBefore[1].children().toArray());
		
		i = 0;
		for(SimpleTree child : childrenAfter) {
			assertEquals("After: Child "+i+": Has parent", node, child.parent());
			assertArrayEquals("After: Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void replaceChildNull() {
		SimpleTree node = new SimpleTree(null, new SimpleTree());
		node.replaceChild(0, null);
	}
	
	
	@Test
	public void replaceChild() {
		SimpleTree oldChild = new SimpleTree();
		SimpleTree newChild = new SimpleTree();
		SimpleTree[] children = {new SimpleTree(), oldChild, new SimpleTree()};
		SimpleTree node = new SimpleTree(null, children);
		
		assertNull("Before: Testee: No parent", node.parent());
		assertArrayEquals("Before: Testee: Has children", children, node.children().toArray());
		
		int i = 0;
		for(SimpleTree child : children) {
			assertEquals("Before: Child "+i+": Has parent", node, child.parent());
			assertArrayEquals("Before: Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
		
		
		SimpleTree ret = node.replaceChild(1, newChild);
		children[1] = newChild;
		
		assertEquals("replaced", oldChild, ret);
		assertNull("After: Testee: No parent", node.parent());
		assertArrayEquals("After: Testee: Has children", children, node.children().toArray());
		
		i = 0;
		for(SimpleTree child : children) {
			assertEquals("After: Child "+i+": Has parent", node, child.parent());
			assertArrayEquals("After: Child "+i+": No children", new Object[]{}, child.children().toArray());
			i++;
		}
	}
	
	
	@Test
	public void getChild() {
		SimpleTree child = new SimpleTree();
		SimpleTree node = new SimpleTree(null, new SimpleTree(), child, new SimpleTree());
		
		assertEquals("Get child", child, node.getChild(1));
	}
	
	
	@Test
	public void iterator() {
		SimpleTree[] children = {new SimpleTree(), new SimpleTree(), new SimpleTree()};
		SimpleTree node = new SimpleTree(null, children);
		
		int i = 0;
		for(SimpleTree child : node) {
			assertEquals("Child "+i, children[i], child);
			i++;
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
