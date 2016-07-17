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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TreeSpec {
	private SimpleTree getTeta() {
		return new SimpleTree(null, Arrays.asList(
			new SimpleTree(null, Arrays.asList(
				new SimpleTree(),
				new SimpleTree()
			)),
			new SimpleTree(null, Arrays.asList(
				new SimpleTree(null, Arrays.asList(
					new SimpleTree(null, Arrays.asList(
						new SimpleTree(null, Arrays.asList(
							new SimpleTree()
						)),
						new SimpleTree(null, Arrays.asList(
							new SimpleTree(),
							new SimpleTree(null, Arrays.asList(
								new SimpleTree()
							))
						))
					)),
					new SimpleTree(null, Arrays.asList(
						new SimpleTree()
					))
				))
			))
		));
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
	
	
	@Test(expected = NullPointerException.class)
	public void constructorChildrenNull() {
		new SimpleTree( (List<SimpleTree>)null );
	}
	
	
	@Test
	public void constructorParentNoChildren() {
		SimpleTree parent = new SimpleTree();
		SimpleTree node = new SimpleTree(parent, Arrays.asList());
		
		assertNull("Parent: No parent", parent.parent());
		assertArrayEquals("Parent: Has children", new Object[]{node}, parent.children().toArray());
		
		assertEquals("Testee: Has parent", parent, node.parent());
		assertArrayEquals("Testee: No children", new Object[]{}, node.children().toArray());
	}
	
	
	@Test
	public void constructorParentOneChild() {
		SimpleTree child  = new SimpleTree();
		SimpleTree parent = new SimpleTree();
		SimpleTree node   = new SimpleTree(parent, Arrays.asList(child));
		
		assertNull("Parent: No parent", parent.parent());
		assertArrayEquals("Parent: Has children", new Object[]{node}, parent.children().toArray());
		
		assertEquals("Testee: Has parent", parent, node.parent());
		assertArrayEquals("Testee: Has children", new Object[]{child}, node.children().toArray());
		
		assertEquals("Child: Has parent", node, child.parent());
		assertArrayEquals("Child: No children", new Object[]{}, child.children().toArray());
	}
	
	
	@Test(expected = NullPointerException.class)
	public void constructorParentOneNullChild() {
		new SimpleTree(null, Arrays.asList((SimpleTree)null) );
	}
	
	
	@Test
	public void constructorParentTwoChildren() {
		SimpleTree[] children = {new SimpleTree(), new SimpleTree()};
		SimpleTree parent     = new SimpleTree();
		SimpleTree node       = new SimpleTree(parent, Arrays.asList(children));
		
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
	
	
	@Test(expected = NullPointerException.class)
	public void constructorParentOneChildOneNullChild() {
		new SimpleTree(null, Arrays.asList(new SimpleTree(), null));
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
	
	
	@Test(expected = NullPointerException.class)
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
	
	@SuppressWarnings({ "rawtypes", "unused" })
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
	
	
	@Test(expected = NullPointerException.class)
	public void setChildrenNull() {
		SimpleTree child = new SimpleTree();
		SimpleTree node = new SimpleTree();
		node.setChildren(Arrays.asList(child, null));
	}
	
	
	@Test
	public void setChildrenAdopt() {
		SimpleTree[] children = {new SimpleTree(), new SimpleTree()};
		SimpleTree oldParent = new SimpleTree(null, Arrays.asList(children));
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
	
	
	@Test(expected = NullPointerException.class)
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
		SimpleTree node = new SimpleTree(null, Arrays.asList(childrenBefore));
		
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
	
	
	@Test(expected = NullPointerException.class)
	public void replaceChildNull() {
		SimpleTree node = new SimpleTree(null, Arrays.asList(new SimpleTree()));
		node.replaceChild(0, null);
	}
	
	
	@Test
	public void replaceChild() {
		SimpleTree oldChild = new SimpleTree();
		SimpleTree newChild = new SimpleTree();
		SimpleTree[] children = {new SimpleTree(), oldChild, new SimpleTree()};
		SimpleTree node = new SimpleTree(null, Arrays.asList(children));
		
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
		SimpleTree node = new SimpleTree(null, Arrays.asList(new SimpleTree(), child, new SimpleTree()));
		
		assertEquals("Get child", child, node.getChild(1));
	}
	
	
	@Test
	public void iterator() {
		SimpleTree[] children = {new SimpleTree(), new SimpleTree(), new SimpleTree()};
		SimpleTree node = new SimpleTree(null, Arrays.asList(children));
		
		int i = 0;
		for(SimpleTree child : node) {
			assertEquals("Child "+i, children[i], child);
			i++;
		}
	}
	
	
	@Test
	public void isomorphChildCnt() {
		SimpleTree a = new SimpleTree();
		SimpleTree b = new SimpleTree(null, Arrays.asList(new SimpleTree(), new SimpleTree()));
		
		assertFalse("0 < 2", a.isIsomorphTo(b)); new SimpleTree(a);
		assertFalse("1 < 2", a.isIsomorphTo(b)); new SimpleTree(a);
		assertTrue("2 == 2", a.isIsomorphTo(b)); new SimpleTree(a);
		assertFalse("3 > 2", a.isIsomorphTo(b)); new SimpleTree(a);
		assertFalse("4 > 4", a.isIsomorphTo(b)); new SimpleTree(a);
	}
	
	
	@Test
	public void isomorphSame() {
		SimpleTree a = new SimpleTree(null, Arrays.asList(new SimpleTree(), new SimpleTree()));
		
		assertTrue("same", a.isIsomorphTo(a));
	}
	
	
	@Test
	public void isomorphDifferentClass() {
		SimpleTree a = new SimpleTree();
		SimpleTree b = new SimpleTree(){};
		
		assertFalse("Different class", a.isIsomorphTo(b));
	}
	
	
	@Test
	public void isomorphComplex() {
		assertTrue("Different class", getTeta().isIsomorphTo(getTeta()));
	}
	
	@Test
	public void isomorphComplexFail() {
		SimpleTree tree = getTeta();
		new SimpleTree(tree.getChild(0).getChild(0));
		assertFalse("Different class", tree.isIsomorphTo(getTeta()));
	}
}
