package com.github.skrupellos.follow;

import org.junit.Before;
import org.junit.Test;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.tree.TreeIntNode;

public class FullTreeTest {

	TreeIntNode testTree;
	
	@Before
	public void initTestTree() {
		RegexCatenation catenation = new RegexCatenation();
		testTree = catenation;
		
		RegexUnion union = new RegexUnion();
		RegexStar star = new RegexStar();
		testTree.addChild(union);
		testTree.addChild(star);
		
		union.addChild(new RegexSymbol<String>("a"));
		union.addChild(new RegexSymbol<String>("b"));
		
		union = new RegexUnion();
		star.addChild(union);
		
		RegexUnion union1 = new RegexUnion();
		star = new RegexStar();
		union.addChild(union1);
		union.addChild(star.addChild(new RegexSymbol<String>("b")));
		
		star = new RegexStar();
		union1.addChild(star.addChild(new RegexSymbol<String>("a")));
		
		catenation = new RegexCatenation();
		union1.addChild(catenation.addChild(new RegexSymbol<String>("b")));
		
		star = new RegexStar();
		catenation.addChild(star.addChild(new RegexSymbol<String>("a")));
	}
	
	@Test
	public void testTreePrinting() {
		System.out.println();
		System.out.println(testTree.treeString());
	}
	
	@Test(expected = AlterJungeException.class)
	public void testFailingTree_0() {
		TreeIntNode failingTest = new RegexCatenation();
		failingTest.addChild(new RegexSymbol<Integer>(23));
		failingTest.addChild(new RegexSymbol<String>("23"));
	}
}
