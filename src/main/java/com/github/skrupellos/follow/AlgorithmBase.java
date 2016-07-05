package com.github.skrupellos.follow;

public class AlgorithmBase<KEY, VALUE> extends MapHelper<KEY, VALUE> {
	private final KEY root;
	
	
	public AlgorithmBase(KEY root) {
		this.root = root;
	}
	
	
	public VALUE result() {
		return lookup(root);
	}
}
