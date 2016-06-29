package com.github.skrupellos.follow;

import java.util.Map;
import java.util.HashMap;
import com.github.skrupellos.follow.regex.RegexVisitor;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexExtNode;


class Attributes {
	public final RegexNode tree;
	public final boolean containsEpsilon;
	
	Attributes(RegexNode tree, boolean containsEpsilon) {
		this.tree = tree;
		this.containsEpsilon = containsEpsilon;
	}
}


public class Algorithm1b extends AlgorithmBase<RegexNode, Attributes> implements RegexVisitor {
	public static Attributes apply(RegexNode root) {
		return (new Algorithm1b(root)).result();
	}
	
	
	public Algorithm1b(RegexNode root) {
		super(root);
		root.accept(this);
	}
	
	
	private void define(RegexNode key, RegexNode tree, boolean containsEpsilon) {
		define(key, new Attributes(tree, containsEpsilon));
	}
	
	
	public void post(RegexCatenation regex) {
		Attributes left  = lookup(regex.left());
		Attributes right = lookup(regex.right());
		
		if(left.tree.getClass().equals(RegexEpsilon.class)) {
			define(regex, right);
		}
		else if(right.tree.getClass().equals(RegexEpsilon.class)) {
			define(regex, left);
		}
		else {
			define(
				regex,
				new RegexCatenation(left.tree, right.tree),
				false
			);
		}
	}
	
	
	public void post(RegexStar regex) {
		Attributes sub = lookup(regex.sub());
		
		if(sub.tree.getClass().equals(RegexEpsilon.class)) {
			define(regex, sub);
		}
		else {
			define(regex, new RegexStar(sub.tree), sub.containsEpsilon);
		}
	}
	
	
	public void post(RegexUnion regex) {
		Attributes left  = lookup(regex.left());
		Attributes right = lookup(regex.right());
		
		if(left.tree.getClass().equals(RegexEpsilon.class) && right.containsEpsilon) {
			define(regex, right);
		}
		else if(right.tree.getClass().equals(RegexEpsilon.class) && left.containsEpsilon) {
			define(regex, left);
		}
		else {
			define(
				regex,
				new RegexUnion(left.tree, right.tree),
				left.containsEpsilon || right.containsEpsilon
			);
		}
	}
	
	
	public void post(RegexEpsilon regex) {
		define(regex, new Attributes(regex.deepCopy(), true));
	}
	
	
	public void postExt(RegexExtNode regex) {
		define(regex, new Attributes(regex.deepCopy(), false));
	}
}
