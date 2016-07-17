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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.skrupellos.follow.follow.Algorithm1b;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexIntNode;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class Algorithm1bSpec {
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
	
	
	private RegexIntNode<String> epsilonRegex() {
		return new RegexUnion<String>(
			new RegexSymbol<String>("a"),
			new RegexStar<String>(
				new RegexCatenation<String>(
					new RegexEpsilon<String>(),
					new RegexEpsilon<String>()
				)
			)
		);
	}
	
	
	@Test
	public void teta() {
		RegexNode<String> in = getTeta();
		RegexNode<String> out = getTeta();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationEpsilonLeft() {
		RegexNode<String> in = new RegexCatenation<String>(new RegexEpsilon<String>(), getTeta());
		RegexNode<String> out = getTeta();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationEpsilonRight() {
		RegexNode<String> in = new RegexCatenation<String>(getTeta(), new RegexEpsilon<String>());
		RegexNode<String> out = getTeta();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationNoEpsilon() {
		RegexNode<String> in = new RegexCatenation<String>(getTeta(), getTeta());
		RegexNode<String> out = new RegexCatenation<String>(getTeta(), getTeta());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void emptySet() {
		RegexNode<String> in = new RegexEmptySet<String>();
		RegexNode<String> out = new RegexEmptySet<String>();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void epsilon() {
		RegexNode<String> in = new RegexEpsilon<String>();
		RegexNode<String> out = new RegexEpsilon<String>();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void starEpsilon() {
		RegexNode<String> in = new RegexStar<String>(new RegexEpsilon<String>());
		RegexNode<String> out = new RegexEpsilon<String>();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void starNoEpsilon() {
		RegexNode<String> in = new RegexStar<String>(getTeta());
		RegexNode<String> out = new RegexStar<String>(getTeta());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@SuppressFBWarnings(value="DM_STRING_CTOR")
	@Test
	public void symbol() {
		RegexNode<String> in = new RegexSymbol<String>(new String("follow"));
		RegexNode<String> out = new RegexSymbol<String>(new String("follow"));
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEpsilonLeftSingle() {
		RegexNode<String> in = new RegexUnion<String>(new RegexEpsilon<String>(), getTeta());
		RegexNode<String> out = new RegexUnion<String>(new RegexEpsilon<String>(), getTeta());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEpsilonRightSingle() {
		RegexNode<String> in = new RegexUnion<String>(getTeta(), new RegexEpsilon<String>());
		RegexNode<String> out = new RegexUnion<String>(getTeta(), new RegexEpsilon<String>());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEpsilonLeftMultiple() {
		RegexNode<String> in = new RegexUnion<String>(new RegexEpsilon<String>(), epsilonRegex());
		RegexNode<String> out = new RegexUnion<String>(new RegexSymbol<String>("a"), new RegexEpsilon<String>());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEpsilonRightMultiple() {
		RegexNode<String> in = new RegexUnion<String>(epsilonRegex(), new RegexEpsilon<String>());
		RegexNode<String> out = new RegexUnion<String>(new RegexSymbol<String>("a"), new RegexEpsilon<String>());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionNoEpsilon() {
		RegexNode<String> in = new RegexUnion<String>(getTeta(), getTeta());
		RegexNode<String> out = new RegexUnion<String>(getTeta(), getTeta());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionIndirectEpsilon() {
		RegexNode<String> in = new RegexUnion<String>(epsilonRegex(), epsilonRegex());
		RegexNode<String> out = new RegexUnion<String>(
			new RegexUnion<String>(new RegexSymbol<String>("a"), new RegexEpsilon<String>()),
			new RegexUnion<String>(new RegexSymbol<String>("a"), new RegexEpsilon<String>())
		);
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
}
