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

package com.github.skrupellos.follow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.skrupellos.follow.follow.AlgorithmBase;
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
