package com.github.skrupellos.follow.follow;

import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexExtNode;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexVisitor;


class Attributes<T> {
	public final RegexNode<T> tree;
	public final boolean containsEpsilon;
	
	Attributes(RegexNode<T> tree, boolean containsEpsilon) {
		this.tree = tree;
		this.containsEpsilon = containsEpsilon;
	}
}


public class Algorithm1b<T> extends AlgorithmBase<RegexNode<T>, Attributes<T>> implements RegexVisitor<T> {
	public static <T> Attributes<T> apply(RegexNode<T> root) {
		return (new Algorithm1b<T>(root)).result();
	}
	
	
	public Algorithm1b(RegexNode<T> root) {
		super(root);
		root.accept(this);
	}
	
	
	private void define(RegexNode<T> key, RegexNode<T> tree, boolean containsEpsilon) {
		define(key, new Attributes<T>(tree, containsEpsilon));
	}
	
	
	public void post(RegexCatenation<T> regex) {
		Attributes<T> left  = lookup(regex.left());
		Attributes<T> right = lookup(regex.right());
		
		if(left.tree.getClass().equals(RegexEpsilon.class)) {
			define(regex, right);
		}
		else if(right.tree.getClass().equals(RegexEpsilon.class)) {
			define(regex, left);
		}
		else {
			define(
				regex,
				new RegexCatenation<T>(left.tree, right.tree),
				false
			);
		}
	}
	
	
	public void post(RegexStar<T> regex) {
		Attributes<T> sub = lookup(regex.sub());
		
		if(sub.tree.getClass().equals(RegexEpsilon.class)) {
			define(regex, sub);
		}
		else {
			define(regex, new RegexStar<T>(sub.tree), sub.containsEpsilon);
		}
	}
	
	
	public void post(RegexUnion<T> regex) {
		Attributes<T> left  = lookup(regex.left());
		Attributes<T> right = lookup(regex.right());
		
		if(left.tree.getClass().equals(RegexEpsilon.class) && right.containsEpsilon) {
			define(regex, right);
		}
		else if(right.tree.getClass().equals(RegexEpsilon.class) && left.containsEpsilon) {
			define(regex, left);
		}
		else {
			define(
				regex,
				new RegexUnion<T>(left.tree, right.tree),
				left.containsEpsilon || right.containsEpsilon
			);
		}
	}
	
	
	public void post(RegexEpsilon<T> regex) {
		define(regex, new Attributes<T>(regex.deepCopy(), true));
	}
	
	
	public void postExt(RegexExtNode<T> regex) {
		define(regex, new Attributes<T>(regex.deepCopy(), false));
	}
}
