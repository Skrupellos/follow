package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.regex.abstraction.RegexIntNode;
import com.github.skrupellos.follow.regex.abstraction.RegexNode;

public class RegexStar extends RegexIntNode {
	private RegexNode sub;
	
	public RegexStar(RegexNode sub) throws IllegalArgumentException {
		setSub(sub);
	}
	
	public RegexNode getSub() {
		return sub;
	}
	
	public RegexStar setSub(RegexNode sub) throws IllegalArgumentException {
		if(sub == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.sub = sub;
		
		return this;
	}
}
