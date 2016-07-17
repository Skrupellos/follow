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

package com.github.skrupellos.follow.regex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


class VisitorA implements RegexVisitor<String> {
	private final static String NEWLINE = "\n";
	public final StringBuilder builder = new StringBuilder();
	
	public void pre(RegexCatenation<String> regex)   { builder.append("pre(RegexCatenation): "+regex+NEWLINE);   }
	public void pre(RegexStar<String> regex)         { builder.append("pre(RegexStar): "+regex+NEWLINE);         }
	public void pre(RegexUnion<String> regex)        { builder.append("pre(RegexUnion): "+regex+NEWLINE);        }
	public void pre(RegexEmptySet<String> regex)     { builder.append("pre(RegexEmptySet): "+regex+NEWLINE);     }
	public void pre(RegexEpsilon<String> regex)      { builder.append("pre(RegexEpsilon): "+regex+NEWLINE);      }
	public void pre(RegexSymbol<String> regex)       { builder.append("pre(RegexSymbol): "+regex+NEWLINE);       }
	
	public void inter(RegexCatenation<String> regex) { builder.append("inter(RegexCatenation): "+regex+NEWLINE); }
	public void inter(RegexStar<String> regex)       { builder.append("inter(RegexStar): "+regex+NEWLINE);       }
	public void inter(RegexUnion<String> regex)      { builder.append("inter(RegexUnion): "+regex+NEWLINE);      }
	public void inter(RegexEmptySet<String> regex)   { builder.append("inter(RegexEmptySet): "+regex+NEWLINE);   }
	public void inter(RegexEpsilon<String> regex)    { builder.append("inter(RegexEpsilon): "+regex+NEWLINE);    }
	public void inter(RegexSymbol<String> regex)     { builder.append("inter(RegexSymbol): "+regex+NEWLINE);     }
	
	public void post(RegexCatenation<String> regex)  { builder.append("post(RegexCatenation): "+regex+NEWLINE);  }
	public void post(RegexStar<String> regex)        { builder.append("post(RegexStar): "+regex+NEWLINE);        }
	public void post(RegexUnion<String> regex)       { builder.append("post(RegexUnion): "+regex+NEWLINE);       }
	public void post(RegexEmptySet<String> regex)    { builder.append("post(RegexEmptySet): "+regex+NEWLINE);    }
	public void post(RegexEpsilon<String> regex)     { builder.append("post(RegexEpsilon): "+regex+NEWLINE);     }
	public void post(RegexSymbol<String> regex)      { builder.append("post(RegexSymbol): "+regex+NEWLINE);      }
}


class VisitorB implements RegexVisitor<String> {
	private final static String NEWLINE = "\n";
	public final StringBuilder builder = new StringBuilder();
	
	public void preInt(RegexIntNode<String> regex)   { builder.append("preInt: "+regex+NEWLINE);   }
	public void preExt(RegexExtNode<String> regex)   { builder.append("preExt: "+regex+NEWLINE);   }
	
	public void interInt(RegexIntNode<String> regex) { builder.append("interInt: "+regex+NEWLINE); }
	public void interExt(RegexExtNode<String> regex) { builder.append("interExt: "+regex+NEWLINE); }
	
	public void postInt(RegexIntNode<String> regex)  { builder.append("postInt: "+regex+NEWLINE);  }
	public void postExt(RegexExtNode<String> regex)  { builder.append("postExt: "+regex+NEWLINE);  }
}


class VisitorC implements RegexVisitor<String> {
	private final static String NEWLINE = "\n";
	public final StringBuilder builder = new StringBuilder();
	
	public void preAll(RegexNode<String> regex)   { builder.append("preAll: "+regex+NEWLINE);   }
	public void interAll(RegexNode<String> regex) { builder.append("interAll: "+regex+NEWLINE); }
	public void postAll(RegexNode<String> regex)  { builder.append("postAll: "+regex+NEWLINE);  }
}


public class RegexSpec {
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
	
	
	private RegexIntNode<String> completeRegex() {
		return new RegexCatenation<String>(
			new RegexUnion<String>(
				new RegexSymbol<String>("a"),
				new RegexEpsilon<String>()
			),
			new RegexStar<String>(
				new RegexEmptySet<String>()
			)
		);
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
		
		assertEquals("Teta", expected, getTeta().treeString());
		
		expected = "meow\n"
		         + "- +\n"
		         + "- - a\n"
		         + "- - ε\n"
		         + "- *\n"
		         + "- - Ø\n";
		
		assertEquals("complete", expected, completeRegex().treeString());
	}
	
	
	@Test
	public void acceptA() {
		VisitorA visitor = new VisitorA();
		completeRegex().accept(visitor);
		
		String expected = "pre(RegexCatenation): meow\n"
		                + "pre(RegexUnion): +\n"
		                + "pre(RegexSymbol): a\n"
		                + "post(RegexSymbol): a\n"
		                + "inter(RegexUnion): +\n"
		                + "pre(RegexEpsilon): ε\n"
		                + "post(RegexEpsilon): ε\n"
		                + "post(RegexUnion): +\n"
		                + "inter(RegexCatenation): meow\n"
		                + "pre(RegexStar): *\n"
		                + "pre(RegexEmptySet): Ø\n"
		                + "post(RegexEmptySet): Ø\n"
		                + "post(RegexStar): *\n"
		                + "post(RegexCatenation): meow\n";
		
		assertEquals("A", expected, visitor.builder.toString());
	}
	
	
	@Test
	public void acceptB() {
		VisitorB visitor = new VisitorB();
		completeRegex().accept(visitor);
		
		String expected = "preInt: meow\n"
		                + "preInt: +\n"
		                + "preExt: a\n"
		                + "postExt: a\n"
		                + "interInt: +\n"
		                + "preExt: ε\n"
		                + "postExt: ε\n"
		                + "postInt: +\n"
		                + "interInt: meow\n"
		                + "preInt: *\n"
		                + "preExt: Ø\n"
		                + "postExt: Ø\n"
		                + "postInt: *\n"
		                + "postInt: meow\n";
		
		assertEquals("A", expected, visitor.builder.toString());
	}
	
	
	@Test
	public void acceptC() {
		VisitorC visitor = new VisitorC();
		completeRegex().accept(visitor);
		
		String expected = "preAll: meow\n"
	                    + "preAll: +\n"
		                + "preAll: a\n"
		                + "postAll: a\n"
		                + "interAll: +\n"
		                + "preAll: ε\n"
		                + "postAll: ε\n"
		                + "postAll: +\n"
		                + "interAll: meow\n"
		                + "preAll: *\n"
		                + "preAll: Ø\n"
		                + "postAll: Ø\n"
		                + "postAll: *\n"
		                + "postAll: meow\n";
		
		assertEquals("A", expected, visitor.builder.toString());
	}
	
	
	@Test
	public void acceptD() {
		RegexVisitor<String> visitor = new RegexVisitor<String>(){};
		completeRegex().accept(visitor);
	}
}
