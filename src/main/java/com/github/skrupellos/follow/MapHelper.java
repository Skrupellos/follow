package com.github.skrupellos.follow;

import java.util.Map;
import java.util.HashMap;
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
}
