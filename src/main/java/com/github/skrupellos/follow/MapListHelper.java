package com.github.skrupellos.follow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

public abstract class MapListHelper<KEY, VALUE> {
private final Map<KEY, List<VALUE>> map = new HashMap<>();
	
	
	protected void define(@NonNull KEY key, @NonNull VALUE value) {
		if(map.get(key) == null) {
			// size of 4 for ArrayList should be sufficient in general
			// and does not pollute memory as much as default of 10
			List<VALUE> list = new ArrayList<>(4);
			list.add(value);
			map.put(key, list);
		} else {
			map.get(key).add(value);
		}
	}
	
	
	protected List<VALUE> lookup(@NonNull KEY key) {
		return map.get(key);
	}

	
	protected Collection<List<VALUE>> getValues() {
		return new LinkedList<>(map.values());
	}
}
