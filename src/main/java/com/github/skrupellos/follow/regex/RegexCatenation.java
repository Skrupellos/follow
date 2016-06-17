package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.regex.abstraction.RegexIntNode;
import com.github.skrupellos.follow.regex.abstraction.RegexNode;

public class RegexCatenation extends RegexIntNode {
	private RegexNode subA;
	private RegexNode subB;
	
	public RegexCatenation(RegexNode subA, RegexNode subB) throws IllegalArgumentException {
		setSubA(subA);
		setSubB(subB);
	}
	
	public RegexNode getSubA() {
		return subA;
	}
	
	public RegexNode getSubB() {
		return subB;
	}
	
	public RegexCatenation setSubA(RegexNode subA) throws IllegalArgumentException {
		if(subA == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.subA = subA;
		
		return this;
	}
	
	public RegexCatenation setSubB(RegexNode subB) throws IllegalArgumentException {
		if(subB == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.subB = subB;
		
		return this;
	}
}
