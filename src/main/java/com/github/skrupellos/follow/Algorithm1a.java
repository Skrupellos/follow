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




public class Algorithm1a extends AlgorithmBase<RegexNode> {
	public static RegexNode apply(RegexNode root) {
		return (new Algorithm1a(root)).result();
	}
	
	
	public Algorithm1a(RegexNode root) {
		super(root);
	}
	
	
	public void post(RegexCatenation regex) {
		RegexNode left  = lookup(regex.left());
		RegexNode right = lookup(regex.right());
		
		if(left.getClass().equals(RegexEmptySet.class) || right.getClass().equals(RegexEmptySet.class)) {
			define(regex, new RegexEmptySet());
		}
		else {
			define(regex, new RegexCatenation(left, right));
		}
	}
	
	
	public void post(RegexStar regex) {
		RegexNode sub = lookup(regex.sub());
		define(regex, sub.getClass().equals(RegexEmptySet.class) ? sub : new RegexStar(sub));
	}
	
	
	public void post(RegexUnion regex) {
		RegexNode left  = lookup(regex.left());
		RegexNode right = lookup(regex.right());
		
		if(left.getClass().equals(RegexEmptySet.class)) {
			define(regex, right);
		}
		else if(right.getClass().equals(RegexEmptySet.class)) {
			define(regex, left);
		}
		else {
			define(regex, new RegexUnion(left, right));
		}
	}
	
	
	public void postExt(RegexExtNode regex) {
		define(regex, regex.deepCopy());
	}
}
