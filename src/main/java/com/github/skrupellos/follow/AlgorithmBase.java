package com.github.skrupellos.follow;

import java.util.Map;
import java.util.HashMap;


public class AlgorithmBase<KEY, VALUE> {
	private final Map<KEY, VALUE> map = new HashMap<KEY, VALUE>();
	private final KEY root;
	
	
	public AlgorithmBase(KEY root) {
		this.root = root;
	}
	
	
	public VALUE result() {
		VALUE result = map.get(root);
		assert(result != null);
		return result;
	}
	
	
	protected void define(KEY key, VALUE value) {
		if(key == null) {
			throw new IllegalArgumentException("null");
		}
		if(value == null) {
			throw new IllegalArgumentException("null");
		}
		
		VALUE ret = map.putIfAbsent(key, value);
		if(ret != null) {
			throw new IllegalArgumentException("Not a new regex");
		}
	}
	
	
	protected VALUE lookup(KEY key) {
		if(key == null) {
			throw new IllegalArgumentException("null");
		}
		
		VALUE value = map.get(key);
		if(value == null) {
			throw new IllegalArgumentException("regex does not exist");
		}
		
		return value;
	}
}
