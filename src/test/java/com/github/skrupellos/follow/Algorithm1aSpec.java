package com.github.skrupellos.follow;

import org.junit.Test;

import com.github.skrupellos.follow.tree.TreeNode;
import com.github.skrupellos.follow.tree.SimpleTree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import com.github.skrupellos.follow.regex.RegexVisitor;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexExtNode;
import com.github.skrupellos.follow.regex.RegexIntNode;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import static org.junit.Assert.assertEquals;

public class Algorithm1aSpec {
	private RegexIntNode<String> getTeta() {
		return new RegexCatenation<String>(
			new RegexUnion<String>(
				new RegexSymbol<String>("a"),
				new RegexSymbol<String>("b")
			),
			new RegexStar<String>(
				new RegexUnion<String>(
					new RegexUnion<String>(
						new RegexStar<String>(
							new RegexSymbol<String>("a")
						),
						new RegexCatenation<String>(
							new RegexSymbol<String>("b"),
							new RegexStar<String>(
								new RegexSymbol<String>("a")
							)
						)
					),
					new RegexStar<String>(
						new RegexSymbol<String>("b")
					)
				)
			)
		);
	}
	
	
	@Test
	public void teta() {
		RegexNode<String> in = getTeta();
		RegexNode<String> out = getTeta();
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationEmptyLeft() {
		RegexNode<String> in = new RegexCatenation<String>(new RegexEmptySet<String>(), getTeta());
		RegexNode<String> out = new RegexEmptySet<String>();
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationEmptyRight() {
		RegexNode<String> in = new RegexCatenation<String>(getTeta(), new RegexEmptySet<String>());
		RegexNode<String> out = new RegexEmptySet<String>();
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationNotEmpty() {
		RegexNode<String> in = new RegexCatenation<String>(getTeta(), getTeta());
		RegexNode<String> out = new RegexCatenation<String>(getTeta(), getTeta());
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void emptySet() {
		RegexNode<String> in = new RegexEmptySet<String>();
		RegexNode<String> out = new RegexEmptySet<String>();
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void epsilon() {
		RegexNode<String> in = new RegexEpsilon<String>();
		RegexNode<String> out = new RegexEpsilon<String>();
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void starEmpty() {
		RegexNode<String> in = new RegexStar<String>(new RegexEmptySet<String>());
		RegexNode<String> out = new RegexEmptySet<String>();
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void starNotEmpty() {
		RegexNode<String> in = new RegexStar<String>(getTeta());
		RegexNode<String> out = new RegexStar<String>(getTeta());
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@SuppressFBWarnings(value="DM_STRING_CTOR")
	@Test
	public void symbol() {
		RegexNode<String> in = new RegexSymbol<String>(new String("follow"));
		RegexNode<String> out = new RegexSymbol<String>(new String("follow"));
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEmptyLeft() {
		RegexNode<String> in = new RegexUnion<String>(new RegexEmptySet<String>(), getTeta());
		RegexNode<String> out = getTeta();
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEmptyRight() {
		RegexNode<String> in = new RegexUnion<String>(getTeta(), new RegexEmptySet<String>());
		RegexNode<String> out = getTeta();
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void unionNotEmpty() {
		RegexNode<String> in = new RegexUnion<String>(getTeta(), getTeta());
		RegexNode<String> out = new RegexUnion<String>(getTeta(), getTeta());
		assertTrue("", Algorithm1a.apply(in).isIsomorphTo(out));
	}
}
