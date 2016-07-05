package com.github.skrupellos.follow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexNode;

@SuppressWarnings("rawtypes")
class Dummy extends AlgorithmBase<RegexNode, String> {
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

@SuppressWarnings("rawtypes")
public class AlgorithmBaseSpec {
	@Test(expected = NullPointerException.class)
	public void defineKeyNull() {
		new Dummy(new RegexEpsilon()).callDefine(null, "follow");
	}
	
	@Test(expected = NullPointerException.class)
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
	
	@Test(expected = NullPointerException.class)
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
