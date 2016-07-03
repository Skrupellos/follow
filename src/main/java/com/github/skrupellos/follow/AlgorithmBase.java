package com.github.skrupellos.follow;

import java.util.Map;
import java.util.HashMap;


public class AlgorithmBase<KEY, VALUE> extends MapHelper<KEY, VALUE> {
	private final KEY root;
	
	
	public AlgorithmBase(KEY root) {
		this.root = root;
	}
	
	
	public VALUE result() {
		return lookup(root);
	}
}
