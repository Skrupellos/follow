package com.github.skrupellos.follow.follow;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import lombok.NonNull;


public abstract class MapHelper<KEY, VALUE> {
	private final Map<KEY, VALUE> map = new HashMap<KEY, VALUE>();
	
	
	protected void define(@NonNull KEY key, @NonNull VALUE value) {
		VALUE ret = map.putIfAbsent(key, value);
		if(ret != null) {
			throw new IllegalArgumentException("Not a new regex");
		}
	}
	
	
	protected VALUE lookup(@NonNull KEY key) {
		VALUE value = map.get(key);
		if(value == null) {
			throw new IllegalArgumentException("regex does not exist");
		}
		
		return value;
	}

	
	protected Collection<VALUE> getValues() {
		return new LinkedList<>(map.values());
	}
}
