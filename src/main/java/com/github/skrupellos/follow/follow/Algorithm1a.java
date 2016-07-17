package com.github.skrupellos.follow.follow;

import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexExtNode;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexVisitor;




public class Algorithm1a<T> extends AlgorithmBase<RegexNode<T>, RegexNode<T>> implements RegexVisitor<T> {
	public static <T> RegexNode<T> apply(RegexNode<T> root) {
		return (new Algorithm1a<T>(root)).result();
	}
	
	
	public Algorithm1a(RegexNode<T> root) {
		super(root);
		root.accept(this);
	}
	
	
	public void post(RegexCatenation<T> regex) {
		RegexNode<T> left  = lookup(regex.left());
		RegexNode<T> right = lookup(regex.right());
		
		if(left.getClass().equals(RegexEmptySet.class) || right.getClass().equals(RegexEmptySet.class)) {
			define(regex, new RegexEmptySet<T>());
		}
		else {
			define(regex, new RegexCatenation<T>(left, right));
		}
	}
	
	
	public void post(RegexStar<T> regex) {
		RegexNode<T> sub = lookup(regex.sub());
		define(regex, sub.getClass().equals(RegexEmptySet.class) ? sub : new RegexStar<T>(sub));
	}
	
	
	public void post(RegexUnion<T> regex) {
		RegexNode<T> left  = lookup(regex.left());
		RegexNode<T> right = lookup(regex.right());
		
		if(left.getClass().equals(RegexEmptySet.class)) {
			define(regex, right);
		}
		else if(right.getClass().equals(RegexEmptySet.class)) {
			define(regex, left);
		}
		else {
			define(regex, new RegexUnion<T>(left, right));
		}
	}
	
	
	public void postExt(RegexExtNode<T> regex) {
		define(regex, regex.deepCopy());
	}
}
