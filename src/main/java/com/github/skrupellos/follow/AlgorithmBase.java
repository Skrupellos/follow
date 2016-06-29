package com.github.skrupellos.follow;

import java.util.Map;
import java.util.HashMap;
import com.github.skrupellos.follow.regex.RegexVisitor;
import com.github.skrupellos.follow.regex.RegexNode;



class AlgorithmBase<T> implements RegexVisitor {
	private final Map<RegexNode, T> map = new HashMap<RegexNode, T>();
	private final RegexNode root;
	
	
	public AlgorithmBase(RegexNode root) {
		this.root = root;
		root.accept(this);
	}
	
	
	public T result() {
		T result = map.get(root);
		assert(result != null);
		return result;
	}
	
	
	protected void define(RegexNode key, T value) {
		if(key == null) {
			throw new IllegalArgumentException("null");
		}
		if(value == null) {
			throw new IllegalArgumentException("null");
		}
		
		T ret = map.putIfAbsent(key, value);
		if(ret != null) {
			throw new IllegalArgumentException("Not a new regex");
		}
	}
	
	
	protected T lookup(RegexNode key) {
		if(key == null) {
			throw new IllegalArgumentException("null");
		}
		
		T value = map.get(key);
		if(value == null) {
			throw new IllegalArgumentException("regex does not exist");
		}
		
		return value;
	}
}
