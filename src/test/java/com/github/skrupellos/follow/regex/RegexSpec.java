package com.github.skrupellos.follow.regex;

import org.junit.Test;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;


class VisitorA implements RegexVisitor {
	private final static String NEWLINE = "\n";
	public final StringBuilder builder = new StringBuilder();
	
	public void pre(RegexCatenation regex)   { builder.append("pre(RegexCatenation): "+regex+NEWLINE);   }
	public void pre(RegexStar regex)         { builder.append("pre(RegexStar): "+regex+NEWLINE);         }
	public void pre(RegexUnion regex)        { builder.append("pre(RegexUnion): "+regex+NEWLINE);        }
	public void pre(RegexEmptySet regex)     { builder.append("pre(RegexEmptySet): "+regex+NEWLINE);     }
	public void pre(RegexEpsilon regex)      { builder.append("pre(RegexEpsilon): "+regex+NEWLINE);      }
	public void pre(RegexSymbol regex)       { builder.append("pre(RegexSymbol): "+regex+NEWLINE);       }
	
	public void inter(RegexCatenation regex) { builder.append("inter(RegexCatenation): "+regex+NEWLINE); }
	public void inter(RegexStar regex)       { builder.append("inter(RegexStar): "+regex+NEWLINE);       }
	public void inter(RegexUnion regex)      { builder.append("inter(RegexUnion): "+regex+NEWLINE);      }
	public void inter(RegexEmptySet regex)   { builder.append("inter(RegexEmptySet): "+regex+NEWLINE);   }
	public void inter(RegexEpsilon regex)    { builder.append("inter(RegexEpsilon): "+regex+NEWLINE);    }
	public void inter(RegexSymbol regex)     { builder.append("inter(RegexSymbol): "+regex+NEWLINE);     }
	
	public void post(RegexCatenation regex)  { builder.append("post(RegexCatenation): "+regex+NEWLINE);  }
	public void post(RegexStar regex)        { builder.append("post(RegexStar): "+regex+NEWLINE);        }
	public void post(RegexUnion regex)       { builder.append("post(RegexUnion): "+regex+NEWLINE);       }
	public void post(RegexEmptySet regex)    { builder.append("post(RegexEmptySet): "+regex+NEWLINE);    }
	public void post(RegexEpsilon regex)     { builder.append("post(RegexEpsilon): "+regex+NEWLINE);     }
	public void post(RegexSymbol regex)      { builder.append("post(RegexSymbol): "+regex+NEWLINE);      }
}


class VisitorB implements RegexVisitor {
	private final static String NEWLINE = "\n";
	public final StringBuilder builder = new StringBuilder();
	
	public void preInt(RegexIntNode regex)   { builder.append("preInt: "+regex+NEWLINE);   }
	public void preExt(RegexExtNode regex)   { builder.append("preExt: "+regex+NEWLINE);   }
	
	public void interInt(RegexIntNode regex) { builder.append("interInt: "+regex+NEWLINE); }
	public void interExt(RegexExtNode regex) { builder.append("interExt: "+regex+NEWLINE); }
	
	public void postInt(RegexIntNode regex)  { builder.append("postInt: "+regex+NEWLINE);  }
	public void postExt(RegexExtNode regex)  { builder.append("postExt: "+regex+NEWLINE);  }
}


class VisitorC implements RegexVisitor {
	private final static String NEWLINE = "\n";
	public final StringBuilder builder = new StringBuilder();
	
	public void preAll(RegexNode regex)   { builder.append("preAll: "+regex+NEWLINE);   }
	public void interAll(RegexNode regex) { builder.append("interAll: "+regex+NEWLINE); }
	public void postAll(RegexNode regex)  { builder.append("postAll: "+regex+NEWLINE);  }
}


public class RegexSpec {
	private RegexIntNode getTeta() {
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
	
	
	private RegexIntNode completeRegex() {
		return new RegexCatenation(
			new RegexUnion(
				new RegexSymbol<String>("a"),
				new RegexEpsilon()
			),
			new RegexStar(
				new RegexEmptySet()
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
		RegexVisitor visitor = new RegexVisitor(){};
		completeRegex().accept(visitor);
	}
	
	
	@Test(expected = AlterJungeException.class)
	public void testFailingTree() {
		new RegexCatenation(
			new RegexSymbol<Integer>(23),
			new RegexSymbol<String>("23")
		);
	}
}
