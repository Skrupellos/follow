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




public class Algorithm1c extends AlgorithmBase<RegexNode> {
	public static RegexNode apply(RegexNode root) {
		return (new Algorithm1c(root)).result();
	}
	
	
	public Algorithm1c(RegexNode root) {
		super(root);
	}
	
	
	public void post(RegexCatenation regex) {
		define(regex, new RegexCatenation(lookup(regex.left()), lookup(regex.right())));
	}
	
	
	public void post(RegexStar regex) {
		RegexNode sub = lookup(regex.sub());
		define(regex, sub.getClass().equals(RegexStar.class) ? sub : new RegexStar(sub));
	}
	
	
	public void post(RegexUnion regex) {
		define(regex, new RegexUnion(lookup(regex.left()), lookup(regex.right())));
	}
	
	
	public void postExt(RegexExtNode regex) {
		define(regex, regex.deepCopy());
	}
}
