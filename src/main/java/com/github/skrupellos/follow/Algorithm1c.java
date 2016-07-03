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




public class Algorithm1c<T> extends AlgorithmBase<RegexNode<T>, RegexNode<T>> implements RegexVisitor<T> {
	public static <T> RegexNode<T> apply(RegexNode<T> root) {
		return (new Algorithm1c<T>(root)).result();
	}
	
	
	public Algorithm1c(RegexNode<T> root) {
		super(root);
		root.accept(this);
	}
	
	
	public void post(RegexCatenation<T> regex) {
		define(regex, new RegexCatenation<T>(lookup(regex.left()), lookup(regex.right())));
	}
	
	
	public void post(RegexStar<T> regex) {
		RegexNode<T> sub = lookup(regex.sub());
		define(regex, sub.getClass().equals(RegexStar.class) ? sub : new RegexStar<T>(sub));
	}
	
	
	public void post(RegexUnion<T> regex) {
		define(regex, new RegexUnion<T>(lookup(regex.left()), lookup(regex.right())));
	}
	
	
	public void postExt(RegexExtNode<T> regex) {
		define(regex, regex.deepCopy());
	}
}
