package com.github.skrupellos.follow;

import org.junit.Test;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
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

class Dummy extends AlgorithmBase<String> {
	public Dummy(RegexNode root) {
		super(root);
	}
	
	
	public void callDefine(RegexNode key, String value) {
		define(key, value);
	}
	
	public String callLookup(RegexNode key) {
		return lookup(key);
	}
}


public class AlgorithmBaseSpec {
	@Test(expected = IllegalArgumentException.class)
	public void defineKeyNull() {
		new Dummy(new RegexEpsilon()).callDefine(null, "follow");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void defineValueNull() {
		new Dummy(new RegexEpsilon()).callDefine(new RegexEpsilon(), null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void defineMultipleSame() {
		Dummy testee = new Dummy(new RegexEpsilon());
		RegexNode key = new RegexEpsilon();
		testee.callDefine(key, "a");
		testee.callDefine(key, "b");
	}
	
	@Test
	public void defineMultipleDifferent() {
		Dummy testee = new Dummy(new RegexEpsilon());
		RegexNode key[] = {new RegexEpsilon(), new RegexEpsilon()};
		
		testee.callDefine(key[0], "a");
		testee.callDefine(key[1], "b");
		
		assertEquals("", "a", testee.callLookup(key[0]));
		assertEquals("", "b", testee.callLookup(key[1]));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void lookupNullKey() {
		Dummy testee = new Dummy(new RegexEpsilon());
		testee.callDefine(new RegexEpsilon(), "follow");
		testee.callLookup(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void lookupNonExisting() {
		Dummy testee = new Dummy(new RegexEpsilon());
		testee.callDefine(new RegexEpsilon(), "follow");
		testee.callLookup(new RegexEpsilon());
	}
}
